package com.zacsupinger.wearablerelics;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import java.util.Optional;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Makes a worn Totem Amulet save its wearer from death, exactly like a Totem of Undying,
 * then turns it back into an Empty Amulet.
 */
public final class TotemAmuletHandler {
	/** Entity event that plays the totem sound, particles and on-screen animation. */
	private static final byte TOTEM_EVENT = 35;

	private TotemAmuletHandler() {
	}

	public static void register() {
		ServerLivingEntityEvents.ALLOW_DEATH.register(TotemAmuletHandler::allowDeath);
	}

	private static boolean allowDeath(LivingEntity entity, DamageSource source, float damage) {
		// Totems never work against /kill or falling out of the world; neither does the amulet.
		if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return true;
		}

		// A real totem in either hand gets used first, so the amulet is saved for later.
		for (InteractionHand hand : InteractionHand.values()) {
			if (entity.getItemInHand(hand).has(DataComponents.DEATH_PROTECTION)) {
				return true;
			}
		}

		Optional<TrinketSlotAccess> worn = TrinketsApi.getAttachment(entity).findFirst(ModItems.TOTEM_AMULET, true);
		if (worn.isEmpty()) {
			return true;
		}

		// Spend the totem: the amulet stays in the slot as an Empty Amulet, ready to be refilled.
		worn.get().set(new ItemStack(ModItems.EMPTY_AMULET));
		revive(entity);
		return false;
	}

	/** Same result as vanilla's Totem of Undying. */
	private static void revive(LivingEntity entity) {
		if (entity instanceof ServerPlayer player) {
			player.awardStat(Stats.ITEM_USED.get(ModItems.TOTEM_AMULET));
		}

		entity.setHealth(1.0F);
		entity.removeAllEffects();
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		entity.level().broadcastEntityEvent(entity, TOTEM_EVENT);
	}
}

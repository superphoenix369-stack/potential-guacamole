package com.zacsupinger.wearablerelics.item;

import com.zacsupinger.wearablerelics.ModComponents;
import com.zacsupinger.wearablerelics.ModItems;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

/**
 * Worn in the Pendant slot. While worn (and only while worn) it gives the wearer one beacon effect,
 * at level I or II. The effect is set by the material used at the Smithing Table.
 */
public class BeaconAmuletItem extends Item implements TrinketCallback {
	/** The six beacon powers, in the order they appear in the creative menu. */
	public static final List<Identifier> CREATIVE_EFFECTS = List.of(
			Identifier.withDefaultNamespace("speed"),
			Identifier.withDefaultNamespace("haste"),
			Identifier.withDefaultNamespace("resistance"),
			Identifier.withDefaultNamespace("jump_boost"),
			Identifier.withDefaultNamespace("strength"),
			Identifier.withDefaultNamespace("regeneration"));

	/** How long each refresh of the effect lasts, in ticks (15 seconds). */
	private static final int EFFECT_DURATION = 300;
	/** Refresh once the remaining time drops below this, in ticks (10 seconds). */
	private static final int REFRESH_BELOW = 200;
	/** Beacon-style faint particles. Off by default to keep the screen clean. */
	private static final boolean SHOW_PARTICLES = false;

	public BeaconAmuletItem(Properties properties) {
		super(properties);
	}

	public static ItemStack create(Identifier effect, int level) {
		ItemStack stack = new ItemStack(ModItems.BEACON_AMULET);
		stack.set(ModComponents.BEACON_EFFECT, effect);
		stack.set(ModComponents.BEACON_LEVEL, level);
		return stack;
	}

	public static @Nullable Holder<MobEffect> getEffect(ItemStack stack) {
		Identifier id = stack.get(ModComponents.BEACON_EFFECT);
		if (id == null) {
			return null;
		}
		MobEffect effect = BuiltInRegistries.MOB_EFFECT.getValue(id);
		if (effect == null) {
			return null;
		}
		return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
	}

	public static int getLevel(ItemStack stack) {
		Integer level = stack.get(ModComponents.BEACON_LEVEL);
		if (level == null) {
			return 1;
		}
		return Math.max(1, Math.min(2, level));
	}

	@Override
	public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		if (entity.level().isClientSide() || entity.tickCount % 20 != 0 || !slot.canApplyEffects()) {
			return;
		}

		Holder<MobEffect> effect = getEffect(stack);
		if (effect == null) {
			return;
		}

		int amplifier = getLevel(stack) - 1;
		MobEffectInstance current = entity.getEffect(effect);

		// Leave stronger or longer effects (potions, real beacons) alone; only top ours up.
		boolean needsRefresh = current == null
				|| current.getAmplifier() < amplifier
				|| (current.getAmplifier() == amplifier && current.getDuration() < REFRESH_BELOW);

		if (needsRefresh) {
			entity.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, amplifier, true, SHOW_PARTICLES, true));
		}
	}

	/** Taking the amulet off removes its effect right away instead of letting it run out. */
	@Override
	public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		if (entity.level().isClientSide()) {
			return;
		}

		Holder<MobEffect> effect = getEffect(stack);
		if (effect == null) {
			return;
		}

		MobEffectInstance current = entity.getEffect(effect);
		if (current != null
				&& current.isAmbient()
				&& current.getAmplifier() == getLevel(stack) - 1
				&& current.getDuration() <= EFFECT_DURATION) {
			entity.removeEffect(effect);
		}
	}

	/** Right-clicking the amulet puts it straight into a free Pendant slot. */
	@Override
	public boolean canEquipFromUse(ItemStack stack, LivingEntity entity) {
		return true;
	}

	/** Level II amulets shimmer like enchanted gear. */
	@Override
	public boolean isFoil(ItemStack stack) {
		return getLevel(stack) >= 2 || super.isFoil(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display,
								Consumer<Component> tooltip, TooltipFlag flag) {
		Holder<MobEffect> effect = getEffect(stack);
		if (effect == null) {
			tooltip.accept(Component.translatable("tooltip.wearablerelics.beacon_amulet.unattuned")
					.withStyle(ChatFormatting.GRAY));
		} else {
			MutableComponent name = Component.translatable(effect.value().getDescriptionId());
			int level = getLevel(stack);
			if (level > 1) {
				name = Component.translatable("potion.withAmplifier", name,
						Component.translatable("potion.potency." + (level - 1)));
			}
			tooltip.accept(name.withStyle(ChatFormatting.BLUE));
		}
		tooltip.accept(Component.translatable("tooltip.wearablerelics.beacon_amulet.slot").withStyle(ChatFormatting.GRAY));
		if (getLevel(stack) < 2) {
			tooltip.accept(Component.translatable("tooltip.wearablerelics.beacon_amulet.upgrade")
					.withStyle(ChatFormatting.DARK_GRAY));
		}
	}
}

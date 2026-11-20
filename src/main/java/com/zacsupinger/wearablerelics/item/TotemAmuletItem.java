package com.zacsupinger.wearablerelics.item;

import eu.pb4.trinkets.api.callback.TrinketCallback;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

/**
 * Worn in the Necklace slot. When the wearer would die, it acts like a Totem of Undying
 * and then turns back into an Empty Amulet. The actual save happens in
 * {@link com.zacsupinger.wearablerelics.TotemAmuletHandler}.
 */
public class TotemAmuletItem extends Item implements TrinketCallback {
	public TotemAmuletItem(Properties properties) {
		super(properties);
	}

	/** Right-clicking the amulet puts it straight into a free Necklace slot. */
	@Override
	public boolean canEquipFromUse(ItemStack stack, LivingEntity entity) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display,
								Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.translatable("tooltip.wearablerelics.totem_amulet").withStyle(ChatFormatting.GOLD));
		tooltip.accept(Component.translatable("tooltip.wearablerelics.totem_amulet.slot").withStyle(ChatFormatting.GRAY));
	}
}

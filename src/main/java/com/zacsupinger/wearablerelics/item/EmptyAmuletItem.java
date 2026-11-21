package com.zacsupinger.wearablerelics.item;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

/** Crafting ingredient: the bare chain and setting, ready for a Totem or a Beacon. */
public class EmptyAmuletItem extends Item {
	public EmptyAmuletItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display,
								Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(Component.translatable("tooltip.wearablerelics.empty_amulet").withStyle(ChatFormatting.GRAY));
	}
}

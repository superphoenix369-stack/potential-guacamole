package com.zacsupinger.wearablerelics;

import com.zacsupinger.wearablerelics.item.BeaconAmuletItem;
import com.zacsupinger.wearablerelics.item.EmptyAmuletItem;
import com.zacsupinger.wearablerelics.item.TotemAmuletItem;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItems {
	public static final Item EMPTY_AMULET = register("empty_amulet", EmptyAmuletItem::new,
			new Item.Properties().stacksTo(16));

	public static final Item TOTEM_AMULET = register("totem_amulet", TotemAmuletItem::new,
			new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));

	public static final Item BEACON_AMULET = register("beacon_amulet", BeaconAmuletItem::new,
			new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
					.component(ModComponents.BEACON_LEVEL, 1));

	private ModItems() {
	}

	private static Item register(String name, Function<Item.Properties, Item> factory, Item.Properties properties) {
		Identifier id = WearableRelics.id(name);
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		return Registry.register(BuiltInRegistries.ITEM, id, factory.apply(properties.setId(key)));
	}

	/** Forces class loading so the items are registered. */
	public static void init() {
	}
}

package com.zacsupinger.wearablerelics;

import com.zacsupinger.wearablerelics.item.BeaconAmuletItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wearable Relics: turns a Totem of Undying or a Beacon into an amulet worn in a Trinkets slot.
 */
public class WearableRelics implements ModInitializer {
	public static final String MOD_ID = "wearablerelics";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		// Order matters: components must exist before the items that use them as defaults.
		ModComponents.init();
		ModItems.init();
		TotemAmuletHandler.register();

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
			output.accept(ModItems.EMPTY_AMULET);
			output.accept(ModItems.TOTEM_AMULET);
			for (Identifier effect : BeaconAmuletItem.CREATIVE_EFFECTS) {
				output.accept(BeaconAmuletItem.create(effect, 1));
			}
			for (Identifier effect : BeaconAmuletItem.CREATIVE_EFFECTS) {
				output.accept(BeaconAmuletItem.create(effect, 2));
			}
		});

		LOGGER.info("Wearable Relics loaded");
	}
}

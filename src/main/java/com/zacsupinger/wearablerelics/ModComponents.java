package com.zacsupinger.wearablerelics;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

/**
 * Item data stored on a Beacon Amulet.
 * <ul>
 *     <li>{@code wearablerelics:beacon_effect} - id of the mob effect it grants, e.g. {@code minecraft:speed}</li>
 *     <li>{@code wearablerelics:beacon_level} - 1 or 2</li>
 * </ul>
 * Both are plain values so recipes (data packs) can set them directly.
 */
public final class ModComponents {
	public static final DataComponentType<Identifier> BEACON_EFFECT = register("beacon_effect",
			DataComponentType.<Identifier>builder()
					.persistent(Identifier.CODEC)
					.networkSynchronized(Identifier.STREAM_CODEC)
					.build());

	public static final DataComponentType<Integer> BEACON_LEVEL = register("beacon_level",
			DataComponentType.<Integer>builder()
					.persistent(Codec.intRange(1, 2))
					.networkSynchronized(ByteBufCodecs.VAR_INT)
					.build());

	private ModComponents() {
	}

	private static <T> DataComponentType<T> register(String name, DataComponentType<T> type) {
		return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, WearableRelics.id(name), type);
	}

	/** Forces class loading so the components are registered. */
	public static void init() {
	}
}

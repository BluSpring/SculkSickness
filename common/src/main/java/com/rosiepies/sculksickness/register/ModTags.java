package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("SameParameterValue")
public class ModTags {
    public static final TagKey<Block> SCULK_BLOCKS = create(Registry.BLOCK_REGISTRY,"sculk_blocks");
    public static final TagKey<EntityType<?>> SCULK_ENTITIES = create(Registry.ENTITY_TYPE_REGISTRY,"sculk_entities");

    private static <T> TagKey<T> create(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, new ResourceLocation(SculkSickness.MOD_ID,id));
    }
}

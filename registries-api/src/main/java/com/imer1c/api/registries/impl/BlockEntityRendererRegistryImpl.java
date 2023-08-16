package com.imer1c.api.registries.impl;

import com.google.common.collect.Maps;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import java.util.function.BiConsumer;

import java.util.Map;

public class BlockEntityRendererRegistryImpl {

    private static final Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> FACTORIES = Maps.newHashMap();

    private static BiConsumer<BlockEntityType<?>, BlockEntityRendererFactory<?>> consumer = FACTORIES::put;

    public static void init(BiConsumer<BlockEntityType<?>, BlockEntityRendererFactory<?>> vanillaConsumer) {
        FACTORIES.forEach(vanillaConsumer);
        consumer = vanillaConsumer;
    }

    public static<T extends BlockEntity> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererFactory<T> rendererFactory) {
        consumer.accept(blockEntityType, rendererFactory);
    }

}

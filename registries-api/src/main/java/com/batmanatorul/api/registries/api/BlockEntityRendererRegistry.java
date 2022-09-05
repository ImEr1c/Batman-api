package com.batmanatorul.api.registries.api;

import com.batmanatorul.api.registries.impl.BlockEntityRendererRegistryImpl;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

public interface BlockEntityRendererRegistry {

    static<T extends BlockEntity> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererFactory<T> rendererFactory) {
        BlockEntityRendererRegistryImpl.register(blockEntityType, rendererFactory);
    }
}

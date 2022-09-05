package com.batmanatorul.api.registries.mixin;

import com.batmanatorul.api.registries.impl.BlockEntityRendererRegistryImpl;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BlockEntityRendererFactories.class)
public class BlockEntityRendererFactoriesMixin {

    @Shadow @Final private static Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> FACTORIES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        BlockEntityRendererRegistryImpl.init((blockEntityType, blockEntityRendererFactory) -> FACTORIES.put(blockEntityType, blockEntityRendererFactory));
    }
}

package com.imer1c.api.registries.mixin;

import com.imer1c.api.registries.impl.EntityRendererRegistryImpl;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EntityRenderers.class)
public class EntityRendererRegistryMixin {

    @Shadow @Final private static Map<EntityType<?>, EntityRendererFactory<?>> RENDERER_FACTORIES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        EntityRendererRegistryImpl.init((entityType, entityRendererFactory) -> RENDERER_FACTORIES.put(entityType, entityRendererFactory));
    }

}

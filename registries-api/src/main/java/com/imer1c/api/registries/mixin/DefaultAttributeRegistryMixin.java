package com.imer1c.api.registries.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin {

    @Shadow private static Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer> DEFAULT_ATTRIBUTE_REGISTRY;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        DEFAULT_ATTRIBUTE_REGISTRY = new HashMap<>(DEFAULT_ATTRIBUTE_REGISTRY);
    }
}

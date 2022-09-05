package com.batmanatorul.api.registries.api;

import com.batmanatorul.api.registries.impl.EntityRendererRegistryImpl;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public interface EntityRendererRegistry {

    static <T extends Entity> void register(EntityType<? extends T> type, EntityRendererFactory<T> factory) {
        EntityRendererRegistryImpl.register(type, factory);
    }

}

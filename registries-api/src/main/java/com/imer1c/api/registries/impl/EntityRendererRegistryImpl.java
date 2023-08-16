package com.imer1c.api.registries.impl;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Map;
import java.util.function.BiConsumer;

public class EntityRendererRegistryImpl {

    private static final Map<EntityType<?>, EntityRendererFactory<?>> FACTORIES = Maps.newHashMap();

    private static BiConsumer<EntityType<?>, EntityRendererFactory<?>> consumer = FACTORIES::put;

    public static <T extends Entity> void register(EntityType<? extends T> type, EntityRendererFactory<T> factory) {
        consumer.accept(type, factory);
    }

    public static void init(BiConsumer<EntityType<?>, EntityRendererFactory<?>> vanillaConsumer) {
        FACTORIES.forEach(vanillaConsumer);
        consumer = vanillaConsumer;
    }

}

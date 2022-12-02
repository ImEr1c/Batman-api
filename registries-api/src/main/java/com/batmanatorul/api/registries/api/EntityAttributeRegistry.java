package com.batmanatorul.api.registries.api;

import com.batmanatorul.api.registries.mixin.DefaultAttributeRegistryAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.slf4j.LoggerFactory;

public interface EntityAttributeRegistry {

    static void register(EntityType<? extends LivingEntity> entityType, DefaultAttributeContainer.Builder attributeContainer) {
        if (DefaultAttributeRegistryAccessor.getRegistry().put(entityType, attributeContainer.build()) != null) {
            LoggerFactory.getLogger(EntityAttributeRegistry.class).error("Attributes registered twice for entity type " + entityType);
        }
    }

}

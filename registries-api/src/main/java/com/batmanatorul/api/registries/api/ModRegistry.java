package com.batmanatorul.api.registries.api;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModRegistry<E> {
    private final Map<String, E> itemsMap;
    private final Registry<E> registry;
    private final String modId;
    private boolean initialized;

    private ModRegistry(Registry<E> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
        this.itemsMap = new HashMap<>();
    }

    public static<E> ModRegistry<E> create(Registry<E> registry, String modId) {
        return new ModRegistry<>(registry, modId);
    }

    public void register(String id, E object) {
        if (initialized) {
            throw new IllegalStateException("Registry already initialized, can't add more objects to it");
        }

        Objects.requireNonNull(id);
        Objects.requireNonNull(id);

        if (this.itemsMap.putIfAbsent(id, object) != null) {
            throw new IllegalArgumentException("Object already registered with id " + id);
        }
    }

    public void init() {
        initialized = true;

        itemsMap.forEach((id, obj) -> {
            Registry.register(registry, new Identifier(modId, id), obj);
        });
    }
}

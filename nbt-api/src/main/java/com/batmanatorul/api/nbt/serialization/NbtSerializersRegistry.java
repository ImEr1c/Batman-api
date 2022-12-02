package com.batmanatorul.api.nbt.serialization;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NbtSerializersRegistry {
    private static final Map<Class<?>, Supplier<?>> serializers = new HashMap<>();

    public static<A extends INbtSerializer> void register(Class<A> clazz, Supplier<A> clazzSupplier) {
        serializers.put(clazz, clazzSupplier);
    }

    public static<A extends INbtSerializer> A create(Class<A> clazz) {
        return (A) serializers.get(clazz).get();
    }
}

package com.batmanatorul.api.nbt;

import com.batmanatorul.api.nbt.serialization.INbtSerializer;
import com.batmanatorul.api.nbt.serialization.NbtSerializersRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendedNbtCompound extends NbtCompound {
    private ExtendedNbtCompound() {
    }

    public static ExtendedNbtCompound create() {
        return new ExtendedNbtCompound();
    }

    public void put(String path, INbtSerializer nbt) {
        put(path, nbt.serialize());
    }

    public<A extends INbtSerializer> A get(String path, Class<A> clazz) {
        INbtSerializer serializer = NbtSerializersRegistry.create(clazz);

        serializer.deserialize(this.getCompound(path));

        return (A) serializer;
    }

    public<A extends INbtSerializer> void putList(String path, List<A> list) {
        NbtList nbtList = new NbtList();

        nbtList.addAll(list.stream().map(INbtSerializer::serialize).toList());

        put(path, nbtList);
    }

    public<A extends INbtSerializer> List<A> getImmutableList(String path, Class<A> clazz) {
        NbtList nbtList = getList(path, COMPOUND_TYPE);

        return nbtList.stream().map(nbtElement -> {
            A obj = NbtSerializersRegistry.create(clazz);

            obj.deserialize((ExtendedNbtCompound) nbtElement);

            return obj;
        }).toList();
    }

    public<A extends INbtSerializer> List<A> getList(String path, Class<A> clazz) {
        return new ArrayList<>(this.getImmutableList(path, clazz));
    }

    public NbtList getList(String path) {
        return this.getList(path, COMPOUND_TYPE);
    }

    public<A extends INbtSerializer, B extends INbtSerializer> void put(String path, Map<A, B> map, String aPath, String bPath) {
        NbtList nbtList = new NbtList();

        map.forEach((a, b) -> {
            NbtCompound compound = new NbtCompound();

            compound.put(aPath, a.serialize());
            compound.put(bPath, b.serialize());

            nbtList.add(compound);
        });

        put(path, nbtList);
    }


    public<A extends INbtSerializer, B extends INbtSerializer> void put(String path, Map<A, B> map) {
        put(path, map, "a", "b");
    }

    public<A extends INbtSerializer, B extends INbtSerializer> Map<A, B> getMap(String path, String aPath, String bPath, Class<A> aClass, Class<B> bClass) {
        NbtList nbtList = getList(path);

        Map<A, B> map = new HashMap<>();

        nbtList.forEach(nbtElement -> {
            ExtendedNbtCompound compound = (ExtendedNbtCompound) nbtElement;

            A a = compound.get(aPath, aClass);
            B b = compound.get(bPath, bClass);

            map.put(a, b);
        });

        return map;
    }

    public<A extends INbtSerializer, B extends INbtSerializer> Map<A, B> getMap(String path, Class<A> aClass, Class<B> bClass) {
        return getMap(path, "a", "b", aClass, bClass);
    }

    @Override
    public ExtendedNbtCompound getCompound(String key) {
        return (ExtendedNbtCompound) super.getCompound(key);
    }
}

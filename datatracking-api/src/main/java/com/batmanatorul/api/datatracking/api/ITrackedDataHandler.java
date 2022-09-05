package com.batmanatorul.api.datatracking.api;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.nbt.NbtCompound;

public interface ITrackedDataHandler<T> {

    void writeBytes(ExtendedPacketByteBuf buf, T value);

    T fromBytes(ExtendedPacketByteBuf buf);

    void writeToNbt(NbtCompound compound, T value);

    T readFromNbt(NbtCompound compound);

    default TrackedData<T> create(int id) {
        return new TrackedData<>(this, id);
    }

}

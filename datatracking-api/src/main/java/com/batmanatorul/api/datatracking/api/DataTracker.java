package com.batmanatorul.api.datatracking.api;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.Collection;

public abstract class DataTracker {
    private final Int2ObjectMap<Entry<?>> entries = new Int2ObjectOpenHashMap<>();

    private int nextId = 0;

    public<T> TrackedData<T> register(ITrackedDataHandler<T> trackedDataHandler) {
        return trackedDataHandler.create(nextId++);
    }

    public<T> void define(TrackedData<T> trackedData, T defaultValue) {
        entries.put(trackedData.getId(), new Entry<>(trackedData, defaultValue));
        markForUpdate();
    }

    public<T> T get(TrackedData<T> trackedData) {
        return (T) entries.get(trackedData.getId()).get();
    }

    public<T> void set(TrackedData<T> trackedData, T value) {
        Entry<T> entry = ((Entry<T>) entries.get(trackedData.getId()));
        entry.set(value);

        markForUpdate();
    }

    public abstract void markForUpdate();

    public void pack(ExtendedPacketByteBuf buf, boolean onlyDirty) {
        Collection<Entry<?>> entries = onlyDirty ? this.entries.values().stream().filter(Entry::isDirty).toList() : this.entries.values();

        buf.writeVarInt(entries.size());

        for (Entry<?> value : entries) {
            buf.writeVarInt(TrackedDataHandlers.getId(value.trackedData.getHandler()));
            buf.writeVarInt(value.trackedData.getId());
            writeToPacket(value, buf);
        }

        this.entries.forEach((trackedData, entry) -> entry.clean());
    }

    private<T> void writeToPacket(Entry<T> entry, ExtendedPacketByteBuf buf) {
        entry.trackedData.writeBytes(buf, entry.get());
    }

    public void unPack(ExtendedPacketByteBuf buf) {
        int size = buf.readVarInt();

        for (int i = 0; i < size; i++) {
            int trackerId = buf.readVarInt();
            int dataId = buf.readVarInt();

            ITrackedDataHandler<?> trackedDataHandler = TrackedDataHandlers.getTrackedDataHandler(trackerId);

            readFromPacket(buf, trackedDataHandler, dataId);
        }
    }

    private <T> void readFromPacket(ExtendedPacketByteBuf buf, ITrackedDataHandler<T> trackedDataHandler, int id) {
        TrackedData<T> trackedData = trackedDataHandler.create(id);

        Entry<T> entry = new Entry<>(trackedData, trackedDataHandler.fromBytes(buf));

        entries.replace(trackedData.getId(), entry);
    }

    public NbtCompound serialize() {
        NbtCompound compound = new NbtCompound();

        NbtList list = new NbtList();

        for (Entry<?> value : this.entries.values()) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putInt("trackerId", value.trackedData.getId());
            nbtCompound.putInt("handlerId", TrackedDataHandlers.getId(value.trackedData.getHandler()));
            writeNbt(nbtCompound, value);

            list.add(nbtCompound);
        }

        compound.put("list", list);

        return compound;
    }

    private<T> void writeNbt(NbtCompound nbtCompound, Entry<T> entry) {
        entry.trackedData.getHandler().writeToNbt(nbtCompound, entry.value);
    }

    public void deserialize(NbtCompound compound) {
        NbtList list = compound.getList("list", NbtElement.COMPOUND_TYPE);

        for (NbtElement nbtElement : list) {
            NbtCompound nbtCompound = (NbtCompound) nbtElement;

            int trackerId = nbtCompound.getInt("trackerId");
            int handlerId = nbtCompound.getInt("handlerId");

            ITrackedDataHandler<?> trackedDataHandler = TrackedDataHandlers.getTrackedDataHandler(handlerId);

            readNbt(nbtCompound, trackedDataHandler, trackerId);
        }
    }

    private<T> void readNbt(NbtCompound compound, ITrackedDataHandler<T> trackedDataHandler, int trackerId) {
        TrackedData<T> trackedData = trackedDataHandler.create(trackerId);

        Entry<T> entry = new Entry<>(trackedData, trackedDataHandler.readFromNbt(compound));

        entries.put(trackedData.getId(), entry);
    }

    static class Entry<T> {
        private final TrackedData<T> trackedData;
        private T value;
        private boolean isDirty;

        public Entry(TrackedData<T> trackedData, T value) {
            this.trackedData = trackedData;
            this.value = value;
        }

        public void set(T value) {
            this.value = value;
            this.isDirty = true;
        }

        public T get() {
            return value;
        }

        public boolean isDirty() {
            return isDirty;
        }

        public void clean() {
            isDirty = false;
        }
    }

}

package com.batmanatorul.api.datatracking.api;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class TrackedDataHandlers {

    private static int id = 0;
    private static final Map<ITrackedDataHandler<?>, Integer> idMap = new HashMap<>();
    private static final Map<Integer, ITrackedDataHandler<?>> iTrackedDataHandlerMap = new HashMap<>();

    public static ITrackedDataHandler<Integer> INTEGER = new ITrackedDataHandler<>() {
        @Override
        public void writeBytes(ExtendedPacketByteBuf buf, Integer value) {
            buf.writeVarInt(value);
        }

        @Override
        public Integer fromBytes(ExtendedPacketByteBuf buf) {
            return buf.readVarInt();
        }

        @Override
        public void writeToNbt(NbtCompound compound, Integer value) {
            compound.putInt("value", value);
        }

        @Override
        public Integer readFromNbt(NbtCompound compound) {
            return compound.getInt("value");
        }
    };

    public static int getId(ITrackedDataHandler<?> trackedDataHandler) {
        return idMap.get(trackedDataHandler);
    }

    public static ITrackedDataHandler<?> getTrackedDataHandler(int id) {
        return iTrackedDataHandlerMap.get(id);
    }

    private static void register(ITrackedDataHandler<?> trackedDataHandler) {
        int i = id++;

        idMap.put(trackedDataHandler, i);
        iTrackedDataHandlerMap.put(i, trackedDataHandler);
    }

    static {
        register(INTEGER);
    }

}

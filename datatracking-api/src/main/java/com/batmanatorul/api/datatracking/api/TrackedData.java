package com.batmanatorul.api.datatracking.api;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;

public class TrackedData<T> {

    private final ITrackedDataHandler<T> handler;
    private final int id;

    public TrackedData(ITrackedDataHandler<T> handler, int id) {
        this.handler = handler;
        this.id = id;
    }

    public ITrackedDataHandler<T> getHandler() {
        return handler;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TrackedData<?> trackedData))
            return false;

        return trackedData.id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void writeBytes(ExtendedPacketByteBuf buf, T value) {
        handler.writeBytes(buf, value);
    }
}

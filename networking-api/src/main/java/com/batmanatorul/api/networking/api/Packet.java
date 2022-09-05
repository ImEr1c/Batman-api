package com.batmanatorul.api.networking.api;

public interface Packet {
    void toBytes(ExtendedPacketByteBuf buf);
}

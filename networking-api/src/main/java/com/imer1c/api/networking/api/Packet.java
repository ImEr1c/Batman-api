package com.imer1c.api.networking.api;

public interface Packet {
    void toBytes(ExtendedPacketByteBuf buf);
}

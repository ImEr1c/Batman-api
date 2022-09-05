package com.batmanatorul.api.networking.api;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Function;

public interface SimpleNetworkChannel {
    void registerPacket(Class<? extends Packet> packetClass, Function<ExtendedPacketByteBuf, Packet> fromBytes);

    Packet fromBytes(int id, ExtendedPacketByteBuf bytes);

    void send(ServerPacket packet);

    void send(ServerPlayerEntity serverPlayer, ClientPacket packet);
}

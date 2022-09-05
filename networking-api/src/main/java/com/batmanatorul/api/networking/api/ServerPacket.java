package com.batmanatorul.api.networking.api;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPacket extends Packet {
    void handle(ServerPlayerEntity serverPlayer, MinecraftServer server);
}

package com.batmanatorul.api.networking.interfaces;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPacketHandler {

    void handle(MinecraftServer server, ServerPlayerEntity serverPlayer, ExtendedPacketByteBuf buf);

}

package com.imer1c.api.networking.interfaces;

import com.imer1c.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPacketHandler {

    void handle(MinecraftServer server, ServerPlayerEntity serverPlayer, ExtendedPacketByteBuf buf);

}

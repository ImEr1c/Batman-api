package com.batmanatorul.api.networking.api;

import com.batmanatorul.api.networking.impl.PacketsImpl;
import com.batmanatorul.api.networking.interfaces.ClientPacketHandler;
import com.batmanatorul.api.networking.interfaces.ServerPacketHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public interface Packets {

    static void registerClientPacketReceiver(Identifier packetIdentifier, ClientPacketHandler clientConsumer) {
        PacketsImpl.registerClientPacketReceiver(packetIdentifier, clientConsumer);
    }

    static void registerServerPacketReceiver(Identifier packetIdentifier, ServerPacketHandler serverPacketHandlerConsumer) {
        PacketsImpl.registerServerPacketReceiver(packetIdentifier, serverPacketHandlerConsumer);
    }

    static void send(Identifier identifier, ExtendedPacketByteBuf buf) {
        PacketsImpl.send(identifier, buf);
    }

    static void send(Identifier identifier, ServerPlayerEntity serverPlayer, ExtendedPacketByteBuf buf) {
        PacketsImpl.send(identifier, serverPlayer, buf);
    }

}

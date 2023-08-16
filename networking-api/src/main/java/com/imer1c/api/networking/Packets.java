package com.imer1c.api.networking;

import com.imer1c.api.networking.api.ExtendedPacketByteBuf;
import com.imer1c.api.networking.interfaces.ClientPacketHandler;
import com.imer1c.api.networking.interfaces.PacketSender;
import com.imer1c.api.networking.interfaces.ServerPacketHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Packets {

    private static final Map<Identifier, ClientPacketHandler> clientPackets = new HashMap<>();
    private static final Map<Identifier, ServerPacketHandler> serverPackets = new HashMap<>();

    public static void registerClientPacketReceiver(Identifier packetIdentifier, ClientPacketHandler clientConsumer) {
        Objects.requireNonNull(packetIdentifier, "Packet Identifier can not be null when registering a receiver");
        Objects.requireNonNull(clientConsumer, "Packet Consumer can not be null when registering a receiver");

        clientPackets.put(packetIdentifier, clientConsumer);
    }

    public static void registerServerPacketReceiver(Identifier packetIdentifier, ServerPacketHandler serverPacketHandlerConsumer) {
        Objects.requireNonNull(packetIdentifier, "Packet Identifier can not be null when registering a receiver");
        Objects.requireNonNull(serverPacketHandlerConsumer, "Packet Consumer can not be null when registering a receiver");

        serverPackets.put(packetIdentifier, serverPacketHandlerConsumer);
    }

    public static void send(Identifier identifier, ExtendedPacketByteBuf buf) {
        Objects.requireNonNull(identifier, "Packet Identifier can not be null when sending a server packet");
        Objects.requireNonNull(buf, "ExtendedPacketByteBuf can not be null when sending a server packet");

        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();

        Objects.requireNonNull(networkHandler, "Minecraft Client network handler can not be null when sending a packet");

        ((PacketSender) networkHandler).send(identifier, buf);
    }

    public static void send(Identifier identifier, ServerPlayerEntity serverPlayer, ExtendedPacketByteBuf buf) {
        Objects.requireNonNull(identifier, "Packet Identifier can not be null when sending a client packet");
        Objects.requireNonNull(serverPlayer, "ServerPlayer can not be null when sending a client packet");
        Objects.requireNonNull(buf, "ExtendedPacketByteBuf can not be null when sending a client packet");

        ServerPlayNetworkHandler networkHandler = serverPlayer.networkHandler;

        ((PacketSender) networkHandler).send(identifier, buf);
    }

    public static boolean isStaticPacket(Identifier identifier) {
        return clientPackets.containsKey(identifier) || serverPackets.containsKey(identifier);
    }

    public static void handleClientPacket(Identifier identifier, ExtendedPacketByteBuf buf, MinecraftClient client) {
        clientPackets.get(identifier).handle(client, buf);
    }

    public static void handleServerPacket(Identifier identifier, ExtendedPacketByteBuf buf, ServerPlayerEntity serverPlayer, MinecraftServer server) {
        serverPackets.get(identifier).handle(server, serverPlayer, buf);
    }

}

package com.batmanatorul.api.networking.impl.channel;

import com.batmanatorul.api.networking.api.*;
import com.batmanatorul.api.networking.interfaces.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class SimpleNetworkChannelImpl implements SimpleNetworkChannel {

    private int ID = 0;
    private final Map<Integer, Function<ExtendedPacketByteBuf, Packet>> packets = new HashMap<>();
    private final Map<Class<? extends Packet>, Integer> ids = new HashMap<>();
    private final Identifier identifier;

    public SimpleNetworkChannelImpl(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void registerPacket(Class<? extends Packet> packetClass, Function<ExtendedPacketByteBuf, Packet> fromBytes) {
        Objects.requireNonNull(packetClass, "Packet class can not be null");
        Objects.requireNonNull(fromBytes, "From bytes function can not be null");

        int id = ID++;

        packets.put(id, fromBytes);
        ids.put(packetClass, id);
    }

    public Packet fromBytes(int id, ExtendedPacketByteBuf bytes) {
        return packets.get(id).apply(bytes);
    }

    public void send(ServerPacket packet) {
        Objects.requireNonNull(packet, "Server Packet can not be null");

        ExtendedPacketByteBuf buf = ExtendedPacketByteBuf.create();
        int id = ids.get(packet.getClass());

        buf.writeVarInt(id);
        packet.toBytes(buf);

        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();

        Objects.requireNonNull(networkHandler, "Minecraft Client packet listener can not be null");

        ((PacketSender) networkHandler).send(identifier, buf);
    }

    public void send(ServerPlayerEntity serverPlayer, ClientPacket packet) {
        Objects.requireNonNull(serverPlayer, "Player can not be null");
        Objects.requireNonNull(packet, "Client Packet can not be null");

        ExtendedPacketByteBuf buf = ExtendedPacketByteBuf.create();
        int id = ids.get(packet.getClass());

        buf.writeVarInt(id);
        packet.toBytes(buf);

        ((PacketSender) serverPlayer.networkHandler).send(identifier, buf);
    }
}

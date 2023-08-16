package com.imer1c.api.networking.mixin;

import com.imer1c.api.networking.api.ExtendedPacketByteBuf;
import com.imer1c.api.networking.api.ServerPacket;
import com.imer1c.api.networking.Packets;
import com.imer1c.api.networking.SimpleNetworkChannels;
import com.imer1c.api.networking.SimpleNetworkChannel;
import com.imer1c.api.networking.interfaces.PacketSender;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin implements PacketSender {

    @Shadow public ServerPlayerEntity player;

    @Shadow @Final private MinecraftServer server;

    @Shadow public abstract void sendPacket(Packet<?> packet);

    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    public void receive(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        Identifier channel = packet.getChannel();

        if (SimpleNetworkChannels.isNetworkHandler(channel)) {
            ExtendedPacketByteBuf buf = ExtendedPacketByteBuf.fromPacketByteBuf(packet.getData());

            int packetId = buf.readVarInt();

            SimpleNetworkChannel networkChannel = SimpleNetworkChannels.getNetworkChannel(channel);

            com.imer1c.api.networking.api.Packet p = networkChannel.fromBytes(packetId, buf);

            ((ServerPacket) p).handle(player, server);

            ci.cancel();
        }

        if (Packets.isStaticPacket(channel)) {
            ExtendedPacketByteBuf buf = ExtendedPacketByteBuf.fromPacketByteBuf(packet.getData());

            Packets.handleServerPacket(channel, buf, player, server);
        }
    }


    @Override
    public void send(Identifier resourceLocation, ExtendedPacketByteBuf buf) {
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(resourceLocation, buf);
        sendPacket(packet);
    }
}

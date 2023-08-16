package com.imer1c.api.networking.mixin;

import com.imer1c.api.networking.api.ClientPacket;
import com.imer1c.api.networking.api.ExtendedPacketByteBuf;
import com.imer1c.api.networking.Packets;
import com.imer1c.api.networking.SimpleNetworkChannels;
import com.imer1c.api.networking.SimpleNetworkChannel;
import com.imer1c.api.networking.interfaces.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements PacketSender {

    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void sendPacket(Packet<?> packet);

    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    public void receive(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        Identifier channel = packet.getChannel();

        if (SimpleNetworkChannels.isNetworkHandler(channel)) {
            ExtendedPacketByteBuf buf = ExtendedPacketByteBuf.fromPacketByteBuf(packet.getData());

            int packetId = buf.readVarInt();

            SimpleNetworkChannel networkChannel = SimpleNetworkChannels.getNetworkChannel(channel);

            com.imer1c.api.networking.api.Packet p = networkChannel.fromBytes(packetId, buf);

            ((ClientPacket) p).handle(client);

            ci.cancel();
        }

        if (Packets.isStaticPacket(channel)) {
            ExtendedPacketByteBuf buf = ExtendedPacketByteBuf.fromPacketByteBuf(packet.getData());

            Packets.handleClientPacket(channel, buf, client);

            ci.cancel();
        }
    }


    @Override
    public void send(Identifier channelResourceLocation, ExtendedPacketByteBuf buf) {
        CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(channelResourceLocation, buf);
        sendPacket(packet);
    }
}

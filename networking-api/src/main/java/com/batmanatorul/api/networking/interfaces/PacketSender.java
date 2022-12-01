package com.batmanatorul.api.networking.interfaces;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.util.Identifier;

public interface PacketSender {

    void send(Identifier resourceLocation, ExtendedPacketByteBuf buf);

}

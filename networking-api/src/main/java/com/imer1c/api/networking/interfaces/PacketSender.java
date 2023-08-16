package com.imer1c.api.networking.interfaces;

import com.imer1c.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.util.Identifier;

public interface PacketSender {

    void send(Identifier resourceLocation, ExtendedPacketByteBuf buf);

}

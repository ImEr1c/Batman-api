package com.imer1c.api.networking.interfaces;

import com.imer1c.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.client.MinecraftClient;

public interface ClientPacketHandler {

    void handle(MinecraftClient client, ExtendedPacketByteBuf buf);

}

package com.batmanatorul.api.networking.interfaces;

import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import net.minecraft.client.MinecraftClient;

public interface ClientPacketHandler {

    void handle(MinecraftClient client, ExtendedPacketByteBuf buf);

}

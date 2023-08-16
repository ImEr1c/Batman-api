package com.imer1c.api.networking.api;

import net.minecraft.client.MinecraftClient;

public interface ClientPacket extends Packet {
    void handle(MinecraftClient client);
}

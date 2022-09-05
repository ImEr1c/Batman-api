package com.batmanatorul.api.datatracking;

import com.batmanatorul.api.datatracking.api.DataTrackedBlockEntity;
import com.batmanatorul.api.datatracking.api.DataTracker;
import com.batmanatorul.api.networking.api.Packets;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;

public class DataTracking implements ClientModInitializer {

    public static Identifier CLIENT_PACKET = new Identifier("datatracking", "client");

    @Override
    public void onInitializeClient() {
        Packets.registerClientPacketReceiver(CLIENT_PACKET, (minecraftClient, packetByteBuf) -> {
            BlockEntity blockEntity = minecraftClient.world.getBlockEntity(packetByteBuf.readBlockPos());

            if (blockEntity instanceof DataTrackedBlockEntity dataTrackedBlockEntity) {
                DataTracker data = dataTrackedBlockEntity.getData();

                data.unPack(packetByteBuf);
            }
        });
    }
}

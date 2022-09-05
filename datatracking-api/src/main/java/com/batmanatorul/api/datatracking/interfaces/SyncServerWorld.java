package com.batmanatorul.api.datatracking.interfaces;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public interface SyncServerWorld {

    void addDataTrackedBlockEntity(BlockPos pos);

    void removeDataTrackedBlockEntity(BlockPos pos);

    void sendDataToNewPlayer(ServerPlayerEntity serverPlayer);

}

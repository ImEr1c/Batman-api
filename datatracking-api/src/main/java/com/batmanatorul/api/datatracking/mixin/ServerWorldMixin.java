package com.batmanatorul.api.datatracking.mixin;

import com.batmanatorul.api.datatracking.DataTracking;
import com.batmanatorul.api.datatracking.api.IDataTrackedBlockEntity;
import com.batmanatorul.api.datatracking.interfaces.SyncServerWorld;
import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import com.batmanatorul.api.networking.api.ExtendedPacketByteBufs;
import com.batmanatorul.api.networking.api.Packets;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements SyncServerWorld {
    @Shadow @Final private ServerChunkManager chunkManager;
    private final List<BlockPos> dataTrackedBlockEntities = new ArrayList<>();


    @Override
    public void addDataTrackedBlockEntity(BlockPos pos) {
        dataTrackedBlockEntities.add(pos.toImmutable());
    }

    @Override
    public void removeDataTrackedBlockEntity(BlockPos pos) {
        dataTrackedBlockEntities.remove(pos);
    }

    @Override
    public void sendDataToNewPlayer(ServerPlayerEntity serverPlayer) {
        dataTrackedBlockEntities.forEach(pos -> {

            int i = ChunkSectionPos.getSectionCoord(pos.getX());
            int j = ChunkSectionPos.getSectionCoord(pos.getZ());

            ChunkPos chunkPos = new ChunkPos(i, j);

            ChunkHolder chunkHolder = chunkManager.getChunkHolder(chunkPos.toLong());

            if (chunkHolder.playersWatchingChunkProvider.getPlayersWatchingChunk(chunkPos, false).contains(serverPlayer)) {

                BlockEntity blockEntity = this.chunkManager.getWorldChunk(chunkPos.x, chunkPos.z).getBlockEntity(pos);

                if (!(blockEntity instanceof IDataTrackedBlockEntity dataTrackedBlockEntity)) {
                    dataTrackedBlockEntities.remove(pos);
                    return;
                }

                ExtendedPacketByteBuf buf = ExtendedPacketByteBufs.create();
                buf.writeBlockPos(pos);
                dataTrackedBlockEntity.getData().pack(buf, false);

                Packets.send(DataTracking.CLIENT_PACKET, serverPlayer, buf);
            }
        });
    }
}

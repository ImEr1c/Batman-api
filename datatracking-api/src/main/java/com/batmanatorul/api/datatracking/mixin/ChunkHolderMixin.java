package com.batmanatorul.api.datatracking.mixin;

import com.batmanatorul.api.datatracking.DataTracking;
import com.batmanatorul.api.datatracking.api.IDataTrackedBlockEntity;
import com.batmanatorul.api.networking.api.ExtendedPacketByteBuf;
import com.batmanatorul.api.networking.api.ExtendedPacketByteBufs;
import com.batmanatorul.api.networking.api.Packets;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mixin(ChunkHolder.class)
public class ChunkHolderMixin {

    private List<UUID> lastPlayerList = new ArrayList<>();

    @Shadow @Final
    public ChunkHolder.PlayersWatchingChunkProvider playersWatchingChunkProvider;

    @Shadow @Final
    ChunkPos pos;

    @Inject(method = "sendBlockEntityUpdatePacket", at = @At("HEAD"))
    public void updateBlockEntity(World world, BlockPos pos, CallbackInfo ci) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof IDataTrackedBlockEntity dataTrackedBlockEntity) {
            ExtendedPacketByteBuf buf = ExtendedPacketByteBufs.create();

            buf.writeBlockPos(pos);
            dataTrackedBlockEntity.getData().pack(buf, true);

            this.playersWatchingChunkProvider.getPlayersWatchingChunk(this.pos, false).forEach(serverPlayer -> {
                Packets.send(DataTracking.CLIENT_PACKET, serverPlayer, buf);
            });
        }
    }

    @Inject(method = "flushUpdates", at = @At("HEAD"))
    public void update(WorldChunk chunk, CallbackInfo ci) {
        List<ServerPlayerEntity> playerEntities = this.playersWatchingChunkProvider.getPlayersWatchingChunk(this.pos, false);

        List<ServerPlayerEntity> updated = playerEntities.stream().filter(player -> !lastPlayerList.contains(player.getUuid())).toList();

        updated.forEach(player ->
                chunk.getBlockEntities().forEach((blockPos, blockEntity) -> {
                    if (blockEntity instanceof IDataTrackedBlockEntity dataTrackedBlockEntity) {
                        ExtendedPacketByteBuf buf = ExtendedPacketByteBufs.create();

                        buf.writeBlockPos(blockPos);
                        dataTrackedBlockEntity.getData().pack(buf, false);

                        Packets.send(DataTracking.CLIENT_PACKET, player, buf);
                    }
                })
        );

        lastPlayerList = new ArrayList<>(playerEntities.stream().map(ServerPlayerEntity::getUuid).toList());
    }
}

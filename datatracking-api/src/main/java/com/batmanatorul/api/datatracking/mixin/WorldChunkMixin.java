package com.batmanatorul.api.datatracking.mixin;

import com.batmanatorul.api.datatracking.api.IDataTrackedBlockEntity;
import com.batmanatorul.api.datatracking.interfaces.SyncServerWorld;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin {

    @Shadow @Final
    World world;

    @Shadow @Nullable public abstract BlockEntity getBlockEntity(BlockPos pos);

    @Inject(method = "addBlockEntity", at = @At("HEAD"))
    public void add(BlockEntity blockEntity, CallbackInfo ci) {
        if (blockEntity instanceof IDataTrackedBlockEntity && world instanceof ServerWorld)
            ((SyncServerWorld) world).addDataTrackedBlockEntity(blockEntity.getPos().toImmutable());
    }

    @Inject(method = "removeBlockEntity", at = @At("HEAD"))
    public void remove(BlockPos pos, CallbackInfo ci) {
        BlockEntity blockEntity = getBlockEntity(pos);

        if (blockEntity instanceof IDataTrackedBlockEntity && world instanceof ServerWorld)
            ((SyncServerWorld) world).removeDataTrackedBlockEntity(pos.toImmutable());
    }

    @Inject(method = "setBlockEntity", at = @At("HEAD"))
    public void load(BlockEntity blockEntity, CallbackInfo ci) {
        if (blockEntity instanceof IDataTrackedBlockEntity && world instanceof ServerWorld)
            ((SyncServerWorld) world).addDataTrackedBlockEntity(blockEntity.getPos().toImmutable());
    }
}

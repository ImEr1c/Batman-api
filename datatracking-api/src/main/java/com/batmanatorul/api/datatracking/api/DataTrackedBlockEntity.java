package com.batmanatorul.api.datatracking.api;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public abstract class DataTrackedBlockEntity extends BlockEntity implements IDataTrackedBlockEntity {

    protected final DataTracker data = new DataTracker() {
        @Override
        public void markForUpdate() {
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.getChunkManager().markForUpdate(pos);
            }
        }
    };

    public DataTrackedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("data", data.serialize());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        data.deserialize(nbt.getCompound("data"));
    }

    public DataTracker getData() {
        return data;
    }
}

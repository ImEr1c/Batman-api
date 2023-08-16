package com.imer1c.api.registries.api;

import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class BlockEntityTypeBuilder<T extends BlockEntity> {

    private final Factory<? extends T> factory;
    private final Block[] blocks;

    protected BlockEntityTypeBuilder(Factory<? extends T> factory, Block... blocks) {
        this.factory = factory;
        this.blocks = blocks;
    }

    public static<T extends BlockEntity> BlockEntityTypeBuilder<T> create(Factory<? extends T> factory, Block... blocks) {
        return new BlockEntityTypeBuilder<>(factory, blocks);
    }

    public BlockEntityType<T> build(Type<?> type) {
        return BlockEntityType.Builder.<T>create(factory::create, blocks).build(type);
    }

    public interface Factory<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }

}

package com.batmanatorul.api.networking.api;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketByteBuf;

public class ExtendedPacketByteBuf extends PacketByteBuf {

    public static ExtendedPacketByteBuf fromPacketByteBuf(PacketByteBuf data) {
        int i = data.readableBytes();
        if (i >= 0 && i <= 1048576) {
            return new ExtendedPacketByteBuf(data.readBytes(i));
        } else {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }

    public ExtendedPacketByteBuf(ByteBuf parent) {
        super(parent);
    }

    public void writeBlockState(BlockState state) {
        writeVarInt(Block.getRawIdFromState(state));
    }

    public BlockState readBlockState() {
        return Block.getStateFromRawId(readVarInt());
    }
}

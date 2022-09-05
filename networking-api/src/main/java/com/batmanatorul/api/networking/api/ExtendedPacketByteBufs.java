package com.batmanatorul.api.networking.api;

import io.netty.buffer.Unpooled;

public interface ExtendedPacketByteBufs {

    static ExtendedPacketByteBuf create() {
        return new ExtendedPacketByteBuf(Unpooled.buffer());
    }

}

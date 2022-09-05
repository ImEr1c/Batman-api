package com.batmanatorul.api.networking.api;

import com.batmanatorul.api.networking.impl.SimpleNetworkChannelsImpl;
import net.minecraft.util.Identifier;

public interface SimpleNetworkChannels {

    static SimpleNetworkChannel register(Identifier identifier) {
        return SimpleNetworkChannelsImpl.register(identifier);
    }


}

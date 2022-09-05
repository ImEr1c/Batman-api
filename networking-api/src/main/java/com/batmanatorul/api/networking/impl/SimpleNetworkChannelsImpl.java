package com.batmanatorul.api.networking.impl;

import com.batmanatorul.api.networking.api.SimpleNetworkChannel;
import com.batmanatorul.api.networking.impl.channel.SimpleNetworkChannelImpl;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SimpleNetworkChannelsImpl {

    private static final Map<Identifier, SimpleNetworkChannelImpl> simpleNetworkChannels = new HashMap<>();

    public static SimpleNetworkChannel register(Identifier identifier) {
        SimpleNetworkChannelImpl simpleNetworkChannel = new SimpleNetworkChannelImpl(identifier);
        simpleNetworkChannels.put(identifier, simpleNetworkChannel);

        return simpleNetworkChannel;
    }

    public static boolean isNetworkHandler(Identifier identifier) {
        return simpleNetworkChannels.containsKey(identifier);
    }

    public static SimpleNetworkChannelImpl getNetworkChannel(Identifier identifier) {
        return simpleNetworkChannels.get(identifier);
    }

}

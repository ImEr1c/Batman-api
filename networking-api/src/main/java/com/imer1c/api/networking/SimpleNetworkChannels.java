package com.imer1c.api.networking;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SimpleNetworkChannels {

    private static final Map<Identifier, SimpleNetworkChannel> simpleNetworkChannels = new HashMap<>();

    public static SimpleNetworkChannel register(Identifier identifier) {
        SimpleNetworkChannel simpleNetworkChannel = new SimpleNetworkChannel(identifier);
        simpleNetworkChannels.put(identifier, simpleNetworkChannel);

        return simpleNetworkChannel;
    }

    public static boolean isNetworkHandler(Identifier identifier) {
        return simpleNetworkChannels.containsKey(identifier);
    }

    public static SimpleNetworkChannel getNetworkChannel(Identifier identifier) {
        return simpleNetworkChannels.get(identifier);
    }

}

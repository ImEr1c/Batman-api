package com.batmanatorul.api.nbt;

import com.batmanatorul.api.nbt.serialization.NbtSerializersRegistry;
import net.fabricmc.api.ModInitializer;

public class NbtApi implements ModInitializer {
    @Override
    public void onInitialize() {
        NbtSerializersRegistry.init();
    }
}

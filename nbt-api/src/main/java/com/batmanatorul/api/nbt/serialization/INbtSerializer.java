package com.batmanatorul.api.nbt.serialization;

import com.batmanatorul.api.nbt.ExtendedNbtCompound;

public interface INbtSerializer {
    ExtendedNbtCompound serialize();

    void deserialize(ExtendedNbtCompound compound);
}

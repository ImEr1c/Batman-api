package com.batmanatorul.api.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.io.File;

public class ServerPaths {
    public static File getWorldFolder(MinecraftServer server) {
        return FileUtils.getOrCreateFolder(server.getSavePath(WorldSavePath.ROOT));
    }

    public static File getWorldSave(MinecraftServer server, WorldSavePath path) {
        return FileUtils.getOrCreateFolder(server.getSavePath(path));
    }

    public static File getCustomWorldFolder(MinecraftServer server, String path) {
        return new File(getWorldFolder(server), path);
    }
}

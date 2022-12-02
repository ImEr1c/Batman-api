package com.batmanatorul.api.util;

import java.io.File;
import java.nio.file.Path;

public class FileUtils {

    public static File getOrCreateFolder(String folder) {
        return getOrCreateFolder(new File(folder));
    }

    public static File getOrCreateFolder(Path path) {
        return getOrCreateFolder(path.toFile());
    }

    public static File getOrCreateFolder(File folder) {
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new IllegalStateException("Error trying to create the folder " + folder);
            }
        }

        return folder;
    }
}

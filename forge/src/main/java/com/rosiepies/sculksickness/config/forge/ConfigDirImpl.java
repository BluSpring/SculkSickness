package com.rosiepies.sculksickness.config.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class ConfigDirImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    private ConfigDirImpl() {
    }
}

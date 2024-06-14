package com.rosiepies.sculksickness.fabric;

import com.rosiepies.sculksickness.SculkSickness;
import net.fabricmc.api.ModInitializer;

public class SculkSicknessFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SculkSickness.init();
    }
}
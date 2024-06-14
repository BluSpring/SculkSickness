package com.rosiepies.sculksickness.forge;

import dev.architectury.platform.forge.EventBuses;
import com.rosiepies.sculksickness.SculkSickness;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.rmi.registry.Registry;

@Mod(SculkSickness.MOD_ID)
public class SculkSicknessForge {
    public SculkSicknessForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(SculkSickness.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SculkSickness.init();
    }
}
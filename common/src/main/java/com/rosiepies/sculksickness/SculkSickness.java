package com.rosiepies.sculksickness;

import com.rosiepies.sculksickness.config.ModConfig;
import com.rosiepies.sculksickness.events.ModEvents;
import com.rosiepies.sculksickness.register.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class SculkSickness {
    public static final String MOD_ID = "sculksickness";
    private static final Logger LOGGER = LoggerFactory.getLogger(SculkSickness.MOD_ID);
    public static Logger getLogger() {
        return LOGGER;
    }
    public static ModConfig CONFIG;

    public static DamageSource SCULK_CORROSION = new SculkCorrosionDamageSource();

    public static void init() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        ModItems.init();
        ModEffects.init();
        ModEvents.init();
    }



    public static int applyParticles(ServerLevel serverLevel, ParticleOptions particleOptions, Vec3 vec3, Vec3 vec32, float f, int i, boolean bl, Collection<ServerPlayer> collection) {
        int j = 0;
        for (ServerPlayer serverPlayer : collection) {
            if (serverLevel.sendParticles(serverPlayer, particleOptions, bl, vec3.x, vec3.y, vec3.z, i, vec32.x, vec32.y, vec32.z, f)) {
                ++j;
            }
        }
        return j;
    }

    public static void runVibration(Entity entity, Vec3 dest, Integer timeUntilDest) {
        if (entity.getServer() != null) {
            SculkSickness.applyParticles(entity.getServer().getLevel(entity.level.dimension()), new VibrationParticleOption((PositionSource) dest, timeUntilDest), entity.position(), Vec3.ZERO, 1F, 1, false, (Collection<ServerPlayer>) entity.level.players());
            entity.level.playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.WARDEN_TENDRIL_CLICKS, entity.getSoundSource(), .8F, 1.2F);
        }
    }

    public static void runFeatureFromString(LivingEntity entity, String modID, String id) {
        // Throw this wherever appropriate, as long as you have access to the Entity object

        if (entity.getLevel().isClientSide()) // This cannot be run on the client-side, only server-side.
            return;

        var registries = entity.getLevel().registryAccess();
        var featureRegistry = registries.registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);

        var feature = featureRegistry.get(new ResourceLocation(modID, id));
        if (entity.getServer() != null && feature != null) {
            SculkSickness.placeFeature(entity.getServer().getLevel(entity.getLevel().dimension()), Holder.direct(feature), entity.blockPosition());
        }
        // do whatever
    }


    public static void placeFeature(ServerLevel serverLevel, Holder<ConfiguredFeature<?,?>> holder, BlockPos blockPos) {
        ConfiguredFeature<?,?> configuredFeature = holder.value();
        configuredFeature.place(serverLevel, serverLevel.getChunkSource().getGenerator(), serverLevel.getRandom(), blockPos);
    }

    public static boolean compareAmplifier (MobEffectInstance mobEffectInstance, int integer) {
        if (mobEffectInstance != null) return mobEffectInstance.getAmplifier() >= integer;
        return false;
    }
}
package com.rosiepies.sculksickness.register;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.effects.SculkSicknessEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Random;

@SuppressWarnings("unused")
public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(SculkSickness.MOD_ID, Registry.MOB_EFFECT_REGISTRY);

    public static final RegistrySupplier<MobEffect> SCULK_SICKNESS = EFFECTS.register("sculk_sickness", SculkSicknessEffect::new);

    public static int getStageInterval(LivingEntity entity) {
        return (int) (20 * getRandomStageInterval(SculkSickness.CONFIG.common.general.minStageInterval, SculkSickness.CONFIG.common.general.maxStageInterval));
    }
    public static float getRandomStageInterval(float min, float max) {
        Random random = new Random();
        return random.nextFloat(max - min) + min;
    }

    public static Float randomTick() {
        Random random = new Random();
        return random.nextFloat();
    }

    public static void init() {
        EFFECTS.register();
    }
}

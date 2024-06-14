package com.rosiepies.sculksickness.effects;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.ModEffects;
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

public class SculkSicknessEffect extends MobEffect {
    public SculkSicknessEffect() {
        super(MobEffectCategory.HARMFUL, 213328);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (amplifier >= SculkSickness.CONFIG.common.weaknessSymptom.applyWeaknessAtStage - 1) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120, amplifier - SculkSickness.CONFIG.common.weaknessSymptom.applyWeaknessAtStage - 1, true, false, false));
        }
        if (amplifier >= SculkSickness.CONFIG.common.damageSymptom.applyDamageAtStage - 1 && ModEffects.randomTick() <= SculkSickness.CONFIG.common.damageSymptom.randomDamageChance) {
            livingEntity.hurt(SculkSickness.SCULK_CORROSION, SculkSickness.CONFIG.common.damageSymptom.applyDamageAmountPerHalfSecond);
        }
        if (amplifier >= SculkSickness.CONFIG.common.darknessSymptom.applyDarknessAtStage - 1) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 120, 255, true, false, false));
        }
        if (livingEntity.isDeadOrDying()) {
            if (livingEntity.getServer() != null) {
                SculkSickness.applyParticles(livingEntity.getServer().getLevel(livingEntity.level.dimension()), ParticleTypes.SCULK_SOUL, livingEntity.position(), new Vec3(0.5, 0, 0.5), 0.05F, 50, false, (Collection<ServerPlayer>) livingEntity.level.players());
            }
            if (!livingEntity.isSilent()) {
                livingEntity.level.playSound(null, livingEntity.xo, livingEntity.yo, livingEntity.zo, SoundEvents.SCULK_SHRIEKER_SHRIEK, livingEntity.getSoundSource(), 15, 1F);
                livingEntity.playSound(SoundEvents.SCULK_SHRIEKER_SHRIEK, 15, 1F);
            }
            SculkSickness.runFeatureFromString(livingEntity,SculkSickness.MOD_ID,"sculk_patch_death");
        }
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }
    public static void apply(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(ModEffects.SCULK_SICKNESS.get(), ModEffects.getStageInterval(entity)));
    }

}
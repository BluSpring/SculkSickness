package com.rosiepies.sculksickness.events;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.ModEffects;
import com.rosiepies.sculksickness.register.ModTags;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class ModEvents {
    public static void init() {
        EntityEvent.LIVING_HURT.register((LivingEntity entity, DamageSource source, float amount) -> {
            if (source.getDirectEntity() instanceof LivingEntity attackerEntity) {
                if (!(entity.getType().is(ModTags.SCULK_ENTITIES)) && !entity.hasEffect(ModEffects.SCULK_SICKNESS.get()) && attackerEntity.hasEffect(ModEffects.SCULK_SICKNESS.get()) && SculkSickness.CONFIG.common.general.randomInfectChance >= ModEffects.randomTick()) {
                    while (entity.addEffect(new MobEffectInstance(ModEffects.SCULK_SICKNESS.get(), ModEffects.getStageInterval(entity), 0, false, false,true))) {
                        if (entity.level.getServer() != null) {
                            SculkSickness.applyParticles((entity.level.getServer()).getLevel(entity.level.dimension()), ParticleTypes.SCULK_SOUL, entity.position(), new Vec3(0.5,0,0.5), 0.05F, 50, false, (Collection<ServerPlayer>) entity.level.players());
                        }
                        if (!entity.isSilent()) {
                            entity.level.playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.SCULK_CATALYST_BLOOM, entity.getSoundSource(), 5, 0.8F);
                            entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                        }
                        if (entity instanceof Player player) {
                            player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.entity_attack"), true);
                        }
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
    }
}

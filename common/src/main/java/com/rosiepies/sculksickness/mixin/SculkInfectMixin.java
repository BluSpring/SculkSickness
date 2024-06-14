package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.SculkSickness;
import com.rosiepies.sculksickness.register.ModEffects;
import com.rosiepies.sculksickness.register.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Random;


@Mixin(Block.class)
@SuppressWarnings("unchecked")
public abstract class SculkInfectMixin {
        @Inject(at=@At("HEAD"), method = "stepOn")
        public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity, CallbackInfo ci) {
            if (entity instanceof LivingEntity livingEntity && blockState.is(ModTags.SCULK_BLOCKS)) {
                if (!(livingEntity.getType().is(ModTags.SCULK_ENTITIES)) && !livingEntity.hasEffect(ModEffects.SCULK_SICKNESS.get())) {
                    if (SculkSickness.CONFIG.common.general.randomInfectChance / 10 >= randomTick()) {
                        SculkSickness.getLogger().info("Trying to Infect entity: " + livingEntity.getName().getString() + " of UUID: " + livingEntity.getUUID());
                        while (livingEntity.addEffect(new MobEffectInstance(ModEffects.SCULK_SICKNESS.get(), ModEffects.getStageInterval(livingEntity), 0, false, false,true))) {
                            if (level.getServer() != null) {
                                SculkSickness.applyParticles((level.getServer()).getLevel(level.dimension()), ParticleTypes.SCULK_SOUL, entity.position(), new Vec3(0.5,0,0.5), 0.05F, 50, false, (Collection<ServerPlayer>) level.players());
                            }
                            if (!livingEntity.isSilent()) {
                                livingEntity.level.playSound(null, livingEntity.xo, livingEntity.yo, livingEntity.zo, SoundEvents.SCULK_CATALYST_BLOOM, livingEntity.getSoundSource(), 5, 0.8F);
                                livingEntity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                            }
                            if (livingEntity instanceof Player player) {
                                player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.step_on_sculk"), true);
                            }
                        }
                    }
                }
            }
        }

        @Inject(at=@At("TAIL"), method = "playerDestroy")
        public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack, CallbackInfo ci) {
            if (blockState.is(ModTags.SCULK_BLOCKS)) {
                if (SculkSickness.CONFIG.common.general.randomInfectChance >= randomTick() && !player.hasEffect(ModEffects.SCULK_SICKNESS.get())) {
                    SculkSickness.getLogger().info("Trying to Infect entity: " + player.getName().getString() + " of UUID: " + player.getUUID());
                    while (player.addEffect(new MobEffectInstance(ModEffects.SCULK_SICKNESS.get(), ModEffects.getStageInterval(player), 0, false, false,true))) {
                        if (level.getServer() != null) {
                            SculkSickness.applyParticles((level.getServer()).getLevel(level.dimension()), ParticleTypes.SCULK_SOUL, player.position(), new Vec3(0.5,0,0.5), 0.05F, 50, false, (Collection<ServerPlayer>) level.players());
                        }
                        if (!player.isSilent()) {
                            player.level.playSound(null, player.xo, player.yo, player.zo, SoundEvents.SCULK_CATALYST_BLOOM, player.getSoundSource(), 5, 0.8F);
                            player.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 5, 0.8F);
                        }
                        player.displayClientMessage(Component.translatable("text.sculksickness.infected_by.break_sculk"), true);
                    }
                }
            }
        }

        private static Float randomTick() {
            Random random = new Random();
        return random.nextFloat();
    }
}
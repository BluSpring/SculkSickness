package com.rosiepies.sculksickness.mixin;


import com.mojang.logging.LogUtils;
import com.rosiepies.sculksickness.register.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements VibrationListener.VibrationListenerConfig {

    @Final
    @Shadow
    private static final Logger LOGGER = LogUtils.getLogger();
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
        new VibrationListener(new EntityPositionSource(this,0), 15, this, (VibrationListener.ReceivingEvent)null, 0.0F, 0);
    }

    @Shadow
    public abstract boolean hurt(@NotNull DamageSource source, float amount);

    @Shadow
    public abstract @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot);

    @Shadow
    public abstract @NotNull EntityDimensions getDimensions(@NotNull Pose pose);

    @Shadow
    @Nullable
    public abstract ItemEntity drop(ItemStack p_36179_, boolean p_36180_, boolean p_36181_);

    @Shadow
    public abstract Inventory getInventory();

    @Shadow protected WardenSpawnTracker wardenSpawnTracker;
    private final DynamicGameEventListener<VibrationListener> dynamicGameEventListener = new DynamicGameEventListener(new VibrationListener(new EntityPositionSource(this, this.getEyeHeight()), 16, this, (VibrationListener.ReceivingEvent)null, 0.0F, 0));


    @Inject(at=@At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        Level var2 = this.level;
        if (var2 instanceof ServerLevel serverLevel) {
            ((VibrationListener) this.dynamicGameEventListener.getListener()).tick(serverLevel);
        }
    }


    public boolean shouldListen(ServerLevel serverLevel, GameEventListener gameEventListener, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context context) {
        if (this.hasEffect(ModEffects.SCULK_SICKNESS.get()) && !this.isDeadOrDying() && !this.getBrain().hasMemoryValue(MemoryModuleType.VIBRATION_COOLDOWN) && serverLevel.getWorldBorder().isWithinBounds(blockPos) && !this.isRemoved() && this.level == serverLevel) {
            Entity var7 = context.sourceEntity();
            boolean var10002;
            if (var7 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)var7;
                if (!livingEntity.hasEffect(ModEffects.SCULK_SICKNESS.get())) {
                    var10002 = false;
                    return var10002;
                }
            }

            var10002 = true;
            return var10002;
        } else {
            return false;
        }
    }

    public void onSignalReceive(ServerLevel serverLevel, GameEventListener gameEventListener, BlockPos blockPos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f) {
        if (!this.isDeadOrDying()) {
            this.brain.setMemoryWithExpiry(MemoryModuleType.VIBRATION_COOLDOWN, Unit.INSTANCE, 40L);
            serverLevel.broadcastEntityEvent(this, (byte)61);
            this.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 5.0F, this.getVoicePitch());
            BlockPos blockPos2 = blockPos;
            if (entity2 != null) {
                if (this.closerThan(entity2, 30.0)) {
                    if (this.getBrain().hasMemoryValue(MemoryModuleType.RECENT_PROJECTILE)) {
                        if (entity2 instanceof LivingEntity livingEntity2 && !livingEntity2.hasEffect(ModEffects.SCULK_SICKNESS.get())) {
                            blockPos2 = entity2.blockPosition();
                            this.getBrain().setMemoryWithExpiry(MemoryModuleType.INTERACTION_TARGET,livingEntity2,100L);
                        }
                    }
                }

                this.getBrain().setMemoryWithExpiry(MemoryModuleType.RECENT_PROJECTILE, Unit.INSTANCE, 100L);
                returnBlockPos(blockPos2);
            }
        }
    }

    public BlockPos returnBlockPos(BlockPos blockPos) {
        return blockPos;
    }
}

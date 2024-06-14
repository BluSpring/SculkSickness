package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.register.ModEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationListener.class)
public class VibrationListenerMixin {

    @Inject(method = "handleGameEvent", at = @At("HEAD"), cancellable = true)
    public void sculksickness_handleGameEvent(ServerLevel serverLevel, GameEvent.Message message, CallbackInfoReturnable<Boolean> cir) {
        if(message.context().sourceEntity() instanceof LivingEntity entity) {
            if(entity.hasEffect(ModEffects.SCULK_SICKNESS.get())) cir.setReturnValue(false);
        }
    }
}
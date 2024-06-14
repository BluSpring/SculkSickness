package com.rosiepies.sculksickness.mixin;

import com.rosiepies.sculksickness.register.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasEffect(MobEffect mobEffect);

    @Inject(at = @At("HEAD"), method = "shouldDropExperience", cancellable = true)
    public void shouldDropExperience(CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(ModEffects.SCULK_SICKNESS.get())) cir.setReturnValue(false);
    }
}

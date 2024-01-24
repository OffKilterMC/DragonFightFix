package com.offkiltermc.dragonfightfix.mixin;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.dimension.end.DragonRespawnAnimation;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndDragonFight.class)
public abstract class DragonFightMixin {
    @Shadow @Nullable private DragonRespawnAnimation respawnStage;

    @Shadow public abstract void resetSpikeCrystals();

    @Unique
    private static final Logger LOGGER = LogUtils.getLogger();
    @Unique
    private static Boolean shouldResetSpikeCrystals = false;

    @Inject(method="saveData", at=@At("RETURN"), cancellable = true)
    public void onSave(CallbackInfoReturnable<EndDragonFight.Data> cir) {
        EndDragonFight.Data data =  cir.getReturnValue();

        boolean isRespawning = this.respawnStage != null;
        cir.setReturnValue(
                new EndDragonFight.Data(
                        data.needsStateScanning(),
                        data.dragonKilled(),
                        data.previouslyKilled(),
                        isRespawning,
                        data.dragonUUID(),
                        data.exitPortalLocation(),
                        data.gateways()
                )
        );
    }

    @Inject(method="<init>*", at=@At("RETURN"))
    private void onConstruct(CallbackInfo ci) {
        if (this.respawnStage == DragonRespawnAnimation.START) {
            LOGGER.info("NEED TO RESET SPIKE CRYSTALS");
            shouldResetSpikeCrystals = true;
        }
    }

    @Inject(method="tryRespawn", at=@At("RETURN"))
    private void onTryRespawn(CallbackInfo ci) {
        if (shouldResetSpikeCrystals && this.respawnStage == DragonRespawnAnimation.START) {
            this.resetSpikeCrystals();
        }
    }
}

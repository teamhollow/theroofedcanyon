package net.teamhollow.theroofedcanyon.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.teamhollow.theroofedcanyon.entity.vanilla.TRCBoatEntity;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

@Mixin(BoatEntityRenderer.class)
public class BoatEntityRendererMixin {
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void getModelTexture(BoatEntity boat, CallbackInfoReturnable<Identifier> info) {
        if (boat instanceof TRCBoatEntity) {
            info.setReturnValue(((TRCBoatEntity) boat).getBoatSkin());
        }
    }
}

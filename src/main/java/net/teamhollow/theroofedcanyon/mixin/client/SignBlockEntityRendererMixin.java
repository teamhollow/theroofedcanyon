package net.teamhollow.theroofedcanyon.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.teamhollow.theroofedcanyon.util.TRCSign;
import net.minecraft.block.Block;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;

@Mixin(SignBlockEntityRenderer.class)
public class SignBlockEntityRendererMixin {
    @Inject(method = "getModelTexture", at = @At("HEAD"), cancellable = true)
    private static void getModelTexture(Block block, CallbackInfoReturnable<SpriteIdentifier> info) {
        if (block instanceof TRCSign) {
            info.setReturnValue(
                    new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((TRCSign) block).getTexture()));
        }
    }
}

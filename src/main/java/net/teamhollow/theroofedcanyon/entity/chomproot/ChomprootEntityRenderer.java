package net.teamhollow.theroofedcanyon.entity.chomproot;

import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChomprootEntityRenderer extends MobEntityRenderer<ChomprootEntity, ChomprootEntityModel> {
    public ChomprootEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ChomprootEntityModel(), 0.8F);
    }

    @Override
    public Identifier getTexture(ChomprootEntity chomprootEntity) {
        return TRCEntities.texture("chomproot/chomproot");
    }

    @Override
    protected void scale(ChomprootEntity entity, MatrixStack matrixStack, float f) {
        float size = 0.67F + 0.15F * entity.getSize();
        matrixStack.scale(size, size, size);
    }

    @Override
    public void render(ChomprootEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        this.shadowRadius = 0.25F * entity.getSize();
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
    }
}

package net.teamhollow.theroofedcanyon.entity.grubworm;

import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GrubwormEntityRenderer extends MobEntityRenderer<GrubwormEntity, GrubwormEntityModel<GrubwormEntity>> {
    public GrubwormEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new GrubwormEntityModel<GrubwormEntity>(), 0.3F);
    }

    protected float getLyingAngle(GrubwormEntity GrubwormEntity) {
        return 180.0F;
    }

    @Override
    public Identifier getTexture(GrubwormEntity grubwormEntity) {
        return TRCEntities.texture(GrubwormEntity.id + "/" + GrubwormEntity.id);
    }
}

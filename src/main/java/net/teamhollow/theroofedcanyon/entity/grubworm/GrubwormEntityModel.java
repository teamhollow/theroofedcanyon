package net.teamhollow.theroofedcanyon.entity.grubworm;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class GrubwormEntityModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart body;
    private final ModelPart waist;
    private final ModelPart stump;

    public GrubwormEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        head = new ModelPart(this);
        head.setPivot(0.0F, 21.866F, -4.2679F);
        setRotationAngle(head, 0.1745F, 0.0F, 0.0F);
        head.setTextureOffset(15, 16).addCuboid(-2.0F, -2.166F, -1.7321F, 4.0F, 4.0F, 3.0F, 0.0F, false);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, 23.0F, -4.0F);

        body = new ModelPart(this);
        body.setPivot(0.0F, 1.0F, 4.0F);
        tail.addChild(body);
        body.setTextureOffset(0, 0).addCuboid(-3.0F, -5.0F, -4.0F, 6.0F, 5.0F, 5.0F, 0.0F, false);

        waist = new ModelPart(this);
        waist.setPivot(0.0F, -1.0F, 5.0F);
        tail.addChild(waist);
        setRotationAngle(waist, -0.1745F, 0.0F, 0.0F);
        waist.setTextureOffset(0, 10).addCuboid(-2.0F, -2.3F, -2.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);

        stump = new ModelPart(this);
        stump.setPivot(0.0F, 0.0F, 7.5F);
        tail.addChild(stump);
        setRotationAngle(stump, 0.1745F, 0.0F, 0.0F);
        stump.setTextureOffset(13, 10).addCuboid(-1.0F, -1.6F, -0.5F, 2.0F, 2.0F, 3.0F, 0.0F, false);
}
    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // animations go here
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        for (ModelPart part : new ModelPart[]{ head, tail }) {
            part.render(matrixStack, buffer, packedLight, packedOverlay);
        }
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of( head, tail, body, waist, stump );
    }
}

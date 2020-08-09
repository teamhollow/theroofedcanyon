package net.teamhollow.theroofedcanyon.entity.chomproot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ChomprootEntityModel extends EntityModel<ChomprootEntity> {
    private final ModelPart chomproot;
    private final ModelPart head;
    private final ModelPart crown;
    private final ModelPart leaves;
    private final ModelPart leaf1;
    private final ModelPart leaf2;
    private final ModelPart leaf3;
    private final ModelPart leaf4;
    private final ModelPart torso;
    private final ModelPart gum;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart tail;
    private final ModelPart tailRoot;
    
    public ChomprootEntityModel() {
        textureWidth = 128;
        textureHeight = 128;

        chomproot = new ModelPart(this);
        chomproot.setPivot(0.0F, 24.0F, 0.0F);

        head = new ModelPart(this);
        head.setPivot(0.0F, -15.0F, 6.0F);
        setRotationAngle(head, 0.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addCuboid(-9.0F, -9.0F, -15.0F, 18.0F, 10.0F, 18.0F, 0.0F, false);
        head.setTextureOffset(72, 23).addCuboid(-8.0F, 0.6494F, -13.8826F, 16.0F, 2.0F, 0.0F, 0.0F, false);
        chomproot.addChild(head);

        crown = new ModelPart(this);
        crown.setPivot(0.0F, -8.5F, -5.0F);
        crown.setTextureOffset(54, 0).addCuboid(-5.0F, -4.5F, -5.0F, 10.0F, 7.0F, 10.0F, 0.0F, false);
        head.addChild(crown);

        leaves = new ModelPart(this);
        leaves.setPivot(0.0F, 3.0194F, -4.1089F);
        crown.addChild(leaves);

        leaf1 = new ModelPart(this);
        leaf1.setPivot(0.0F, -7.9583F, 7.3442F);
        setRotationAngle(leaf1, 0.7854F, 0.3491F, 0.0F);
        leaf1.setTextureOffset(64, 30).addCuboid(0.0F, -1.0F, -1.0F, 0.0F, 18.0F, 16.0F, 0.0F, false);
        leaves.addChild(leaf1);

        leaf2 = new ModelPart(this);
        leaf2.setPivot(0.0F, -7.9583F, 7.3442F);
        setRotationAngle(leaf2, 0.7854F, 0.8727F, 0.0F);
        leaf2.setTextureOffset(48, 48).addCuboid(0.0F, -1.0F, -1.0F, 0.0F, 18.0F, 16.0F, 0.0F, false);
        leaves.addChild(leaf2);

        leaf3 = new ModelPart(this);
        leaf3.setPivot(0.0F, -7.9583F, 7.3442F);
        setRotationAngle(leaf3, 0.7854F, -0.3491F, 0.0F);
        leaf3.setTextureOffset(64, 12).addCuboid(0.0F, -1.0F, -1.0F, 0.0F, 18.0F, 16.0F, 0.0F, false);
        leaves.addChild(leaf3);

        leaf4 = new ModelPart(this);
        leaf4.setPivot(0.0F, -7.9583F, 7.3442F);
        setRotationAngle(leaf4, 0.7854F, -0.8727F, 0.0F);
        leaf4.setTextureOffset(0, 80).addCuboid(0.0F, -1.0F, -1.0F, 0.0F, 18.0F, 16.0F, 0.0F, false);
        leaves.addChild(leaf4);

        torso = new ModelPart(this);
        torso.setPivot(0.0F, 0.0F, 0.0F);
        torso.setTextureOffset(0, 28).addCuboid(-8.0F, -14.0F, -9.0F, 16.0F, 8.0F, 16.0F, 0.0F, false);
        torso.setTextureOffset(72, 25).addCuboid(-7.0F, -16.0F, -8.0F, 14.0F, 2.0F, 0.0F, 0.0F, false);
        chomproot.addChild(torso);

        gum = new ModelPart(this);
        gum.setPivot(0.0F, 0.0F, 0.0F);
        gum.setTextureOffset(0, 52).addCuboid(-7.0F, -23.0F, -4.0F, 14.0F, 16.0F, 10.0F, 0.0F, false);
        torso.addChild(gum);

        leftLeg = new ModelPart(this);
        leftLeg.setPivot(-4.0F, -14.0F, 0.0F);
        leftLeg.setTextureOffset(86, 9).addCuboid(-3.0F, 8.0F, -4.0F, 6.0F, 6.0F, 8.0F, 0.0F, false);
        torso.addChild(leftLeg);

        rightLeg = new ModelPart(this);
        rightLeg.setPivot(4.0F, -14.0F, 0.0F);
        rightLeg.setTextureOffset(32, 82).addCuboid(-3.0F, 8.0F, -4.0F, 6.0F, 6.0F, 8.0F, 0.0F, false);
        torso.addChild(rightLeg);

        rightArm = new ModelPart(this);
        rightArm.setPivot(8.0F, -11.0F, 0.0F);
        rightArm.setTextureOffset(90, 58).addCuboid(-2.0F, -1.0F, -3.0F, 4.0F, 10.0F, 6.0F, 0.0F, false);
        torso.addChild(rightArm);

        leftArm = new ModelPart(this);
        leftArm.setPivot(-8.0F, -11.0F, 0.0F);
        leftArm.setTextureOffset(80, 90).addCuboid(-2.0F, -1.0F, -3.0F, 4.0F, 10.0F, 6.0F, 0.0F, false);
        torso.addChild(leftArm);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, -7.0F, 6.5F);
        setRotationAngle(tail, -0.5236F, 0.0F, 0.0F);
        tail.setTextureOffset(73, 75).addCuboid(-4.0F, -4.0F, -1.5F, 8.0F, 8.0F, 7.0F, 0.0F, false);
        torso.addChild(tail);

        tailRoot = new ModelPart(this);
        tailRoot.setPivot(0.0F, -0.4019F, 5.8301F);
        setRotationAngle(tailRoot, 0.3491F, 0.0F, 0.0F);
        tailRoot.setTextureOffset(52, 88).addCuboid(-3.0F, -2.5F, -2.0F, 6.0F, 5.0F, 8.0F, 0.0F, false);
        tail.addChild(tailRoot);
    }

    @Override
    public void setAngles(ChomprootEntity chomprootEntity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbAngle *= 0.6662F;
        limbDistance *= -0.74719757;
        float leftLimbPitch = MathHelper.cos(limbAngle) * limbDistance;
        float rightLimbPitch = MathHelper.cos(limbAngle + 3.1415927F) * limbDistance;

        leftLeg.pitch = leftLimbPitch;
        rightLeg.pitch = rightLimbPitch;
        leftArm.pitch = leftLimbPitch;
        rightArm.pitch = rightLimbPitch;

        animate(head, chomprootEntity.isAttacking(), handSwingProgress, animationProgress);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        chomproot.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

    public static void animate(ModelPart part, boolean isAttacking, float handSwingProgress, float animationProgress) {
        float handSwingProgressSin1 = MathHelper.sin(handSwingProgress * 3.1415927F);
        float handSwingProgressSin2 = MathHelper.sin((1.0F - (1.0F - handSwingProgress) * (1.0F - handSwingProgress)) * 3.1415927F);
        part.roll = 0.0F;
        part.yaw = -(0.1F - handSwingProgressSin1 * 0.6F);
        float isAttackingPitch = -3.1415927F / (isAttacking ? 6.0F : 0.5F);
        part.pitch = isAttackingPitch;
        part.pitch += handSwingProgressSin1 * 1.2F - handSwingProgressSin2 * 0.4F;
        part.roll += MathHelper.cos(animationProgress * 0.09F) * 0.05F + 0.05F;
        part.pitch += MathHelper.sin(animationProgress * 0.067F) * 0.05F;
    }
}

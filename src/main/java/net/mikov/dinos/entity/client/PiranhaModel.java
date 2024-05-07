// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.PiranhaAnimations;
import net.mikov.dinos.entity.custom.PiranhaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PiranhaModel<T extends PiranhaEntity> extends SinglePartEntityModel<T> {

    private final ModelPart controller;

    public PiranhaModel(ModelPart root) {
        this.controller = root.getChild("controller");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(30, 41).cuboid(-3.0F, -6.0F, -5.5F, 6.0F, 12.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, -0.5F));

        ModelPartData pectoralR_r1 = controller.addChild("pectoralR_r1", ModelPartBuilder.create().uv(31, 41).mirrored().cuboid(-0.5F, -3.0F, -1.5F, 1.0F, 6.0F, 3.0F, new Dilation(-0.01F)).mirrored(false)
                .uv(55, 41).cuboid(5.5F, -3.0F, -1.5F, 1.0F, 6.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(-3.0F, 6.0F, 0.0F, -2.4871F, 0.0F, 0.0F));

        ModelPartData dorsal_r1 = controller.addChild("dorsal_r1", ModelPartBuilder.create().uv(21, 43).cuboid(-0.5F, -4.0F, -1.5F, 1.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        ModelPartData lower = controller.addChild("lower", ModelPartBuilder.create().uv(48, 26).cuboid(-2.0F, -5.0F, -1.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 5.5F));

        ModelPartData tail = lower.addChild("tail", ModelPartBuilder.create().uv(35, 32).cuboid(-1.0F, -1.8635F, -0.7623F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.1365F, 2.7623F));

        ModelPartData tailfinbottom_r1 = tail.addChild("tailfinbottom_r1", ModelPartBuilder.create().uv(55, 41).cuboid(-0.5F, -3.0F, -1.5F, 1.0F, 6.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 2.1365F, 3.7377F, -2.4871F, 0.0F, 0.0F));

        ModelPartData tailfintop_r1 = tail.addChild("tailfintop_r1", ModelPartBuilder.create().uv(21, 54).cuboid(-0.5F, -4.0F, -1.5F, 1.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.8635F, 3.7377F, -0.6109F, 0.0F, 0.0F));

        ModelPartData head = controller.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.8848F, -5.5793F));

        ModelPartData tooth22_r1 = head.addChild("tooth22_r1", ModelPartBuilder.create().uv(5, 6).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).mirrored().cuboid(3.6F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(-1.8F, 1.958F, -1.4995F, -0.2618F, 0.0F, 0.0F));

        ModelPartData tooth21_r1 = head.addChild("tooth21_r1", ModelPartBuilder.create().uv(5, 6).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).mirrored().cuboid(3.6F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(-1.8F, 1.858F, -2.0995F, -0.2618F, 0.0F, 0.0F));

        ModelPartData tooth20_r1 = head.addChild("tooth20_r1", ModelPartBuilder.create().uv(5, 6).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).mirrored().cuboid(3.4F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(-1.7F, 1.858F, -2.6995F, -0.2618F, 0.0F, 0.0F));

        ModelPartData tooth19_r1 = head.addChild("tooth19_r1", ModelPartBuilder.create().uv(5, 6).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).mirrored().cuboid(2.0F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(-1.0F, 1.758F, -2.6995F, -0.2618F, 0.0F, 0.0F));

        ModelPartData tooth18_r1 = head.addChild("tooth18_r1", ModelPartBuilder.create().uv(5, 6).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).mirrored().cuboid(0.6F, -1.0F, 0.0F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false), ModelTransform.of(-0.3F, 1.658F, -2.6995F, -0.2618F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(0, 51).cuboid(-3.0F, -5.0F, -2.0F, 6.0F, 9.0F, 4.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, -0.8848F, 0.0793F, -0.3927F, 0.0F, 0.0F));

        ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create(), ModelTransform.of(0.0F, 4.5152F, 1.0793F, -0.1309F, 0.0F, 0.0F));

        ModelPartData tooth12_r1 = jaw.addChild("tooth12_r1", ModelPartBuilder.create().uv(5, 6).mirrored().cuboid(-1.4F, -0.7708F, 0.1728F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false)
                .uv(5, 6).mirrored().cuboid(-0.7F, -0.7708F, 0.1728F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false)
                .uv(5, 6).mirrored().cuboid(0.0F, -0.7708F, 0.1728F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false)
                .uv(5, 6).cuboid(-2.4F, -0.7708F, 0.1728F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).cuboid(-3.1F, -0.7708F, 0.1728F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).cuboid(-3.8F, -0.7708F, 0.1728F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)), ModelTransform.of(1.9F, 1.7968F, -4.5775F, 0.5236F, 0.0F, 0.0F));

        ModelPartData tooth10_r1 = jaw.addChild("tooth10_r1", ModelPartBuilder.create().uv(5, 6).mirrored().cuboid(0.0F, 0.1233F, 2.9656F, 0.0F, 1.0F, 0.0F, new Dilation(0.1F)).mirrored(false)
                .uv(5, 6).mirrored().cuboid(0.0F, -0.4263F, 2.0398F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false)
                .uv(5, 6).cuboid(-3.8F, 0.1233F, 2.9656F, 0.0F, 1.0F, 0.0F, new Dilation(0.1F))
                .uv(5, 6).cuboid(-3.8F, -0.4263F, 2.0398F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)), ModelTransform.of(1.9F, 1.7968F, -4.5775F, 0.6981F, 0.0F, 0.0F));

        ModelPartData tooth8_r1 = jaw.addChild("tooth8_r1", ModelPartBuilder.create().uv(5, 6).mirrored().cuboid(0.0F, -0.5903F, 0.9339F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)).mirrored(false)
                .uv(5, 6).cuboid(-3.8F, -0.5903F, 0.9339F, 0.0F, 2.0F, 0.0F, new Dilation(0.1F)), ModelTransform.of(1.9F, 1.7968F, -4.5775F, 0.6545F, 0.0F, 0.0F));

        ModelPartData mouth_r1 = jaw.addChild("mouth_r1", ModelPartBuilder.create().uv(0, 44).cuboid(-2.0F, -0.5768F, -4.9482F, 4.0F, 1.0F, 5.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PiranhaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if (entity.getVelocity().length() >= 0.01) {
            this.animateMovement(PiranhaAnimations.PIRANHA_SWIM, limbSwing, limbSwingAmount, 2f, 25.5f);
        } else {
            this.updateAnimation(entity.idleAnimationState, PiranhaAnimations.PIRANHA_IDLE, ageInTicks, 1f);
            this.updateAnimation(entity.attackingAnimationState, PiranhaAnimations.PIRANHA_ATTACK, ageInTicks, 1f);
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        controller.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return controller;
    }
}

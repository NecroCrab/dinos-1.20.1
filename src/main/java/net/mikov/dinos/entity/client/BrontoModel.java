// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.AnkyAnimations;
import net.mikov.dinos.entity.animation.BrontoAnimations;
import net.mikov.dinos.entity.custom.BrontoEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BrontoModel<T extends BrontoEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;

	public BrontoModel(ModelPart root) {

		this.controller = root.getChild("controller");
	}
	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(0, 420).cuboid(-19.0F, -13.0F, -32.0F, 38.0F, 28.0F, 64.0F, new Dilation(0.0F))
		.uv(0, 357).cuboid(-16.0F, -16.0F, -29.0F, 32.0F, 3.0F, 58.0F, new Dilation(0.0F))
		.uv(0, 292).cuboid(-16.0F, 15.0F, -31.0F, 32.0F, 4.0F, 60.0F, new Dilation(0.0F))
		.uv(141, 447).cuboid(-6.0F, 19.0F, -28.0F, 12.0F, 5.0F, 30.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -38.0F, 0.0F, 3.098F, 0.0F, 3.1416F));

		ModelPartData upper = controller.addChild("upper", ModelPartBuilder.create().uv(165, 316).cuboid(-16.0F, -14.0F, -6.0F, 32.0F, 24.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 36.0F));

		ModelPartData frontlegL = upper.addChild("frontlegL", ModelPartBuilder.create().uv(182, 2).cuboid(-3.0F, -8.0F, -8.0F, 6.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(-19.0F, 2.0F, -13.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData upperarmL = frontlegL.addChild("upperarmL", ModelPartBuilder.create().uv(0, 259).cuboid(-3.5F, -3.0F, -6.5F, 7.0F, 32.0F, 13.0F, new Dilation(-0.01F)), ModelTransform.of(0.5F, 2.0F, 1.5F, 0.1745F, 0.0F, 0.0F));

		ModelPartData lowerarmL = upperarmL.addChild("lowerarmL", ModelPartBuilder.create().uv(127, 315).cuboid(-3.0F, -0.5F, -5.5F, 6.0F, 23.0F, 11.0F, new Dilation(-0.02F)), ModelTransform.of(-0.5F, 28.5F, 1.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData FfootL = lowerarmL.addChild("FfootL", ModelPartBuilder.create().uv(328, 17).cuboid(-3.5F, -0.5F, -5.0F, 7.0F, 5.0F, 12.0F, new Dilation(-0.01F)), ModelTransform.of(0.5F, 22.0F, -0.5F, -0.0436F, 0.0F, 0.0F));

		ModelPartData frontlegR = upper.addChild("frontlegR", ModelPartBuilder.create().uv(0, 225).mirrored().cuboid(-3.0F, -8.0F, -8.0F, 6.0F, 16.0F, 16.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(19.0F, 2.0F, -13.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData upperarmR = frontlegR.addChild("upperarmR", ModelPartBuilder.create().uv(0, 306).mirrored().cuboid(-3.5F, -3.0F, -6.5F, 7.0F, 32.0F, 13.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-0.5F, 2.0F, 1.5F, 0.1745F, 0.0F, 0.0F));

		ModelPartData lowerarmR = upperarmR.addChild("lowerarmR", ModelPartBuilder.create().uv(0, 189).mirrored().cuboid(-3.0F, -0.5F, -5.5F, 6.0F, 23.0F, 11.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(0.5F, 28.5F, 1.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData FfootR = lowerarmR.addChild("FfootR", ModelPartBuilder.create().uv(50, 0).mirrored().cuboid(-3.5F, -0.5F, -5.0F, 7.0F, 5.0F, 12.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-0.5F, 22.0F, -0.5F, -0.0436F, 0.0F, 0.0F));

		ModelPartData neck = upper.addChild("neck", ModelPartBuilder.create().uv(45, 246).cuboid(-14.0F, -11.0F, -2.0F, 28.0F, 20.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 2.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData neck2 = neck.addChild("neck2", ModelPartBuilder.create().uv(338, 142).cuboid(-12.0F, -10.0F, -2.0F, 24.0F, 18.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 22.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData neck3 = neck2.addChild("neck3", ModelPartBuilder.create().uv(424, 254).cuboid(-10.0F, -9.0F, -2.0F, 20.0F, 16.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 22.0F, -0.0436F, 0.0F, 0.0F));

		ModelPartData neck4 = neck3.addChild("neck4", ModelPartBuilder.create().uv(76, 0).cuboid(-8.0F, -8.0F, -2.0F, 16.0F, 14.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 22.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData neck5 = neck4.addChild("neck5", ModelPartBuilder.create().uv(260, 142).cuboid(-6.0F, -7.0F, -2.0F, 12.0F, 12.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 22.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData neck6 = neck5.addChild("neck6", ModelPartBuilder.create().uv(343, 87).cuboid(-5.0F, -6.0F, -2.0F, 10.0F, 10.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 22.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData head = neck6.addChild("head", ModelPartBuilder.create().uv(394, 73).cuboid(-7.0F, -9.0F, 0.0F, 14.0F, 15.0F, 15.0F, new Dilation(0.0F))
		.uv(386, 37).cuboid(-6.0F, -7.0F, 15.0F, 12.0F, 12.0F, 18.0F, new Dilation(0.0F))
		.uv(332, 43).cuboid(-6.0F, 5.0F, 1.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 21.0F, -0.0436F, 0.0F, 0.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(334, 61).cuboid(-6.0F, -1.0F, -2.0F, 12.0F, 2.0F, 20.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 6.0F, 14.0F, -0.0436F, 0.0F, 0.0F));

		ModelPartData lower = controller.addChild("lower", ModelPartBuilder.create().uv(176, 359).cuboid(-16.0F, -12.0F, -6.0F, 32.0F, 24.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -32.0F));

		ModelPartData rearlegL = lower.addChild("rearlegL", ModelPartBuilder.create().uv(452, 0).cuboid(-4.0F, -11.0F, -11.0F, 8.0F, 22.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(-19.0F, 1.0F, 13.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData upperlegL = rearlegL.addChild("upperlegL", ModelPartBuilder.create().uv(0, 360).cuboid(-4.5F, -6.5F, -8.5F, 9.0F, 35.0F, 17.0F, new Dilation(-0.01F)), ModelTransform.of(0.5F, 2.5F, 2.5F, 0.1745F, 0.0F, 0.0F));

		ModelPartData lowerlegL = upperlegL.addChild("lowerlegL", ModelPartBuilder.create().uv(148, 130).cuboid(-4.0F, -1.5F, -7.5F, 8.0F, 28.0F, 15.0F, new Dilation(-0.02F)), ModelTransform.of(-0.5F, 28.0F, 1.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData footL = lowerlegL.addChild("footL", ModelPartBuilder.create().uv(272, 28).cuboid(-4.5F, 0.0F, -8.0F, 9.0F, 6.0F, 16.0F, new Dilation(-0.01F)), ModelTransform.pivot(0.5F, 25.5F, 0.5F));

		ModelPartData rearlegR = lower.addChild("rearlegR", ModelPartBuilder.create().uv(47, 198).mirrored().cuboid(-4.0F, -11.0F, -11.0F, 8.0F, 22.0F, 22.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(19.0F, 1.0F, 13.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData upperlegR = rearlegR.addChild("upperlegR", ModelPartBuilder.create().uv(0, 430).mirrored().cuboid(-4.5F, -6.5F, -8.5F, 9.0F, 35.0F, 17.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-0.5F, 2.5F, 2.5F, 0.1745F, 0.0F, 0.0F));

		ModelPartData lowerlegR = upperlegR.addChild("lowerlegR", ModelPartBuilder.create().uv(126, 369).mirrored().cuboid(-4.0F, -1.5F, -7.5F, 8.0F, 28.0F, 15.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(0.5F, 28.0F, 1.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData footR = lowerlegR.addChild("footR", ModelPartBuilder.create().uv(272, 0).mirrored().cuboid(-4.5F, 0.0F, -8.0F, 9.0F, 6.0F, 16.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.pivot(-0.5F, 25.5F, 0.5F));

		ModelPartData tail1 = lower.addChild("tail1", ModelPartBuilder.create().uv(410, 208).cuboid(-15.0F, -11.0F, -18.5F, 30.0F, 22.0F, 21.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -6.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tail2 = tail1.addChild("tail2", ModelPartBuilder.create().uv(416, 165).cuboid(-13.0F, -10.0F, -20.0F, 26.0F, 20.0F, 22.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -18.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(422, 122).cuboid(-11.0F, -9.0F, -21.5F, 22.0F, 18.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -19.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(159, 397).cuboid(-9.0F, -8.0F, -21.0F, 18.0F, 16.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -21.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create().uv(430, 81).cuboid(-8.0F, -7.0F, -23.5F, 16.0F, 14.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -20.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tail6 = tail5.addChild("tail6", ModelPartBuilder.create().uv(251, 98).cuboid(-7.0F, -6.0F, -23.5F, 14.0F, 12.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -23.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tail7 = tail6.addChild("tail7", ModelPartBuilder.create().uv(254, 60).cuboid(-6.0F, -5.0F, -23.5F, 12.0F, 10.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -23.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData tail8 = tail7.addChild("tail8", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -4.0F, -23.5F, 10.0F, 8.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -23.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData tail9 = tail8.addChild("tail9", ModelPartBuilder.create().uv(444, 46).cuboid(-4.0F, -3.0F, -24.0F, 8.0F, 6.0F, 26.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -23.5F, -0.0873F, 0.0F, 0.0F));

		ModelPartData tail10 = tail9.addChild("tail10", ModelPartBuilder.create().uv(376, 0).cuboid(-3.0F, -2.0F, -25.5F, 6.0F, 4.0F, 27.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -23.5F, -0.0873F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}

	@Override
	public void setAngles(BrontoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateMovement(BrontoAnimations.BRONTO_WALK, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, BrontoAnimations.BRONTO_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, BrontoAnimations.BRONTO_ATTACK, ageInTicks, 1f);
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
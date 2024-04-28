package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.ModAnimations;
import net.mikov.dinos.entity.custom.CompyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

public class CompyModel<T extends CompyEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public CompyModel(ModelPart root) {
		this.controller = root.getChild("controller");
		this.head = controller.getChild("mainbody").getChild("frontbody")/*.getChild("neck").getChild("head")*/;
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.0F, 1.0F));

		ModelPartData mainbody = controller.addChild("mainbody", ModelPartBuilder.create().uv(26, 35).cuboid(-0.5F, -3.0F, 1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(18, 35).cuboid(-0.5F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 1.0F));

		ModelPartData body_r1 = mainbody.addChild("body_r1", ModelPartBuilder.create().uv(21, 16).cuboid(-2.0F, -5.0F, -3.0F, 4.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 1.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData tail1 = mainbody.addChild("tail1", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.0F, 4.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData tail1_r1 = tail1.addChild("tail1_r1", ModelPartBuilder.create().uv(2, 30).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 9.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.9599F, 0.0F, 0.0F));

		ModelPartData tail2 = tail1.addChild("tail2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.875F, 6.35F));

		ModelPartData tail2_r1 = tail2.addChild("tail2_r1", ModelPartBuilder.create().uv(35, 30).cuboid(-0.5F, -0.1757F, 0.0F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.3007F, -0.0463F, 1.3526F, 0.0F, 0.0F));

		ModelPartData frontbody = mainbody.addChild("frontbody", ModelPartBuilder.create().uv(0, 19).cuboid(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 6.0F, new Dilation(-0.01F))
				.uv(10, 35).cuboid(-0.5F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -3.0F));

		ModelPartData neck = frontbody.addChild("neck", ModelPartBuilder.create().uv(17, 1).cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 8.0F, 2.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, -1.0F, -3.0F, 0.7418F, 0.0F, 0.0F));

		ModelPartData spike_r1 = neck.addChild("spike_r1", ModelPartBuilder.create().uv(26, 31).cuboid(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -3.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 9).cuboid(-1.5F, -2.0F, -3.0F, 3.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 1).cuboid(-1.5F, -0.0156F, -7.0F, 3.0F, 2.0F, 5.0F, new Dilation(-0.01F))
				.uv(10, 31).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.2175F, -0.4133F, -0.6981F, 0.0F, 0.0F));

		ModelPartData spike_r2 = head.addChild("spike_r2", ModelPartBuilder.create().uv(18, 31).cuboid(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData armL = frontbody.addChild("armL", ModelPartBuilder.create(), ModelTransform.of(1.8F, 1.0F, -3.0F, 0.0F, -0.1745F, 0.0F));

		ModelPartData armL_r1 = armL.addChild("armL_r1", ModelPartBuilder.create().uv(27, 1).cuboid(-0.2F, -1.2F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.2F, 0.0F, -1.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData lowerarmL = armL.addChild("lowerarmL", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.0F, 0.0F));

		ModelPartData claw3L_r1 = lowerarmL.addChild("claw3L_r1", ModelPartBuilder.create().uv(53, 32).cuboid(-1.0F, -1.4F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 2.0F, -2.0F, 0.202F, 0.8625F, 0.1543F));

		ModelPartData claw2L_r1 = lowerarmL.addChild("claw2L_r1", ModelPartBuilder.create().uv(53, 32).cuboid(0.0F, -1.4F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 2.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

		ModelPartData claw1L_r1 = lowerarmL.addChild("claw1L_r1", ModelPartBuilder.create().uv(53, 32).cuboid(0.0F, -1.4F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, -2.0F, 0.1925F, -0.8197F, -0.1415F));

		ModelPartData lowerarmL_r1 = lowerarmL.addChild("lowerarmL_r1", ModelPartBuilder.create().uv(23, 11).cuboid(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData armR = frontbody.addChild("armR", ModelPartBuilder.create(), ModelTransform.of(-3.2F, 1.0F, -3.0F, 0.0F, 0.1745F, 0.0F));

		ModelPartData armR_r1 = armR.addChild("armR_r1", ModelPartBuilder.create().uv(59, 37).cuboid(0.3F, -1.2F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.2F, 0.0F, -1.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData lowerarmR = armR.addChild("lowerarmR", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 2.0F, 0.0F));

		ModelPartData claw3R_r1 = lowerarmR.addChild("claw3R_r1", ModelPartBuilder.create().uv(53, 32).cuboid(0.0F, -0.4086F, -1.1305F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, -2.0F, 0.1309F, -1.0036F, 0.0F));

		ModelPartData claw1R_r1 = lowerarmR.addChild("claw1R_r1", ModelPartBuilder.create().uv(53, 32).cuboid(-1.0F, -0.4086F, -1.1305F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 1.0F, -2.0F, 0.1309F, 1.0908F, 0.0F));

		ModelPartData claw2R_r1 = lowerarmR.addChild("claw2R_r1", ModelPartBuilder.create().uv(53, 32).cuboid(0.5F, -1.4F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 2.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

		ModelPartData lowerarmR_r1 = lowerarmR.addChild("lowerarmR_r1", ModelPartBuilder.create().uv(17, 13).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		ModelPartData legL = controller.addChild("legL", ModelPartBuilder.create().uv(30, 4).cuboid(0.0F, -2.0F, -2.0F, 2.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.1F, 0.0F, 2.0F, -0.2618F, 0.0F, 0.0F));

		ModelPartData thighL = legL.addChild("thighL", ModelPartBuilder.create(), ModelTransform.of(1.1F, 2.0F, -1.0F, -0.9163F, 0.0F, 0.0F));

		ModelPartData thighL_r1 = thighL.addChild("thighL_r1", ModelPartBuilder.create().uv(39, 0).cuboid(-0.8F, -1.6F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(-0.01F)), ModelTransform.of(-0.2F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData calfL = thighL.addChild("calfL", ModelPartBuilder.create(), ModelTransform.of(0.0F, 2.0359F, 3.134F, 2.138F, 0.0F, 0.0F));

		ModelPartData calfL_r1 = calfL.addChild("calfL_r1", ModelPartBuilder.create().uv(37, 14).cuboid(-1.0F, -1.0F, 0.5F, 1.0F, 4.0F, 2.0F, new Dilation(-0.02F)), ModelTransform.of(1.0F, 0.6048F, -0.9251F, 0.3491F, 0.0F, 0.0F));

		ModelPartData ankleL = calfL.addChild("ankleL", ModelPartBuilder.create().uv(41, 20).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 4.0F, new Dilation(-0.03F)), ModelTransform.pivot(0.0F, 2.6048F, 1.0749F));

		ModelPartData footL = ankleL.addChild("footL", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 0.0F, -2.0F));

		ModelPartData footL_r1 = footL.addChild("footL_r1", ModelPartBuilder.create().uv(42, 26).cuboid(-7.0F, 0.3593F, -4.0408F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 1.0F, 1.0F, -0.8727F, 0.0F, 0.0F));

		ModelPartData toesL = footL.addChild("toesL", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.5647F, -2.2284F));

		ModelPartData toe3L_r1 = toesL.addChild("toe3L_r1", ModelPartBuilder.create().uv(44, 32).cuboid(0.3F, -0.5499F, -0.902F, 1.0F, 1.0F, 2.0F, new Dilation(-0.09F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.9024F, -0.2097F, 0.2811F));

		ModelPartData toe2L_r1 = toesL.addChild("toe2L_r1", ModelPartBuilder.create().uv(44, 32).cuboid(-0.5F, -0.576F, -1.4991F, 1.0F, 1.0F, 2.0F, new Dilation(-0.09F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		ModelPartData toe1L_r1 = toesL.addChild("toe1L_r1", ModelPartBuilder.create().uv(44, 32).cuboid(-1.3289F, -0.5549F, -1.0158F, 1.0F, 1.0F, 2.0F, new Dilation(-0.09F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.9195F, 0.2602F, -0.3543F));

		ModelPartData legR = controller.addChild("legR", ModelPartBuilder.create().uv(49, 1).cuboid(-2.0F, -2.0F, -2.0F, 2.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, 0.0F, 2.0F));

		ModelPartData thighL2 = legR.addChild("thighL2", ModelPartBuilder.create(), ModelTransform.of(-1.7F, 2.0F, -1.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData thighL_r2 = thighL2.addChild("thighL_r2", ModelPartBuilder.create().uv(45, 11).cuboid(-0.3F, -1.6F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(-0.01F)), ModelTransform.of(-0.2F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData calfL2 = thighL2.addChild("calfL2", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 2.0359F, 3.134F, 1.9635F, 0.0F, 0.0F));

		ModelPartData calfL_r2 = calfL2.addChild("calfL_r2", ModelPartBuilder.create().uv(56, 12).cuboid(-0.5F, -1.0F, 0.5F, 1.0F, 4.0F, 2.0F, new Dilation(-0.02F)), ModelTransform.of(1.0F, 0.6048F, -0.9251F, 0.3491F, 0.0F, 0.0F));

		ModelPartData ankleL2 = calfL2.addChild("ankleL2", ModelPartBuilder.create().uv(53, 20).cuboid(-0.5F, 0.6892F, -3.4651F, 1.0F, 1.0F, 4.0F, new Dilation(-0.03F)), ModelTransform.pivot(1.0F, 1.9155F, 1.54F));

		ModelPartData footL2 = ankleL2.addChild("footL2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 1.1481F, -2.7716F, -0.1745F, 0.0F, 0.0F));

		ModelPartData footL_r2 = footL2.addChild("footL_r2", ModelPartBuilder.create().uv(54, 26).cuboid(-6.5F, 0.3593F, -4.0408F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 0.5412F, 1.3066F, -0.8727F, 0.0F, 0.0F));

		ModelPartData toesL2 = footL2.addChild("toesL2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0235F, -1.9218F));

		ModelPartData toe3L_r2 = toesL2.addChild("toe3L_r2", ModelPartBuilder.create().uv(44, 32).cuboid(0.7F, -0.5649F, -1.2437F, 1.0F, 1.0F, 2.0F, new Dilation(-0.09F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.9024F, -0.2097F, 0.2811F));

		ModelPartData toe2L_r2 = toesL2.addChild("toe2L_r2", ModelPartBuilder.create().uv(44, 32).cuboid(0.0F, -0.576F, -1.4991F, 1.0F, 1.0F, 2.0F, new Dilation(-0.09F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		ModelPartData toe1L_r2 = toesL2.addChild("toe1L_r2", ModelPartBuilder.create().uv(44, 32).cuboid(-0.9F, -0.5338F, -0.9805F, 1.0F, 1.0F, 2.0F, new Dilation(-0.09F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.9195F, 0.2602F, -0.3543F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(CompyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.COMPY_WALK, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.COMPY_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.sittingAnimationState, ModAnimations.COMPY_SITTING, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, ModAnimations.COMPY_ATTACK, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -20.0F, 20.0F);
		headPitch = MathHelper.clamp(headPitch, -25, 45);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
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
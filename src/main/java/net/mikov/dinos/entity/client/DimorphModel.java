package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.DimorphAnimations;
import net.mikov.dinos.entity.animation.ModAnimations;
import net.mikov.dinos.entity.custom.DimorphEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class DimorphModel<T extends DimorphEntity> extends SinglePartEntityModel<T> {
	private final ModelPart controller;
	private final ModelPart head;
	private final ModelPart wingL;
	private final ModelPart wingR;
	/*private final ModelPart mainbody;
	private final ModelPart upperbody;
	private final ModelPart upperbody_r1;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart wingL;
	private final ModelPart upperwingL;
	private final ModelPart lowerwingL;
	private final ModelPart tipwingL;
	private final ModelPart wingR;
	private final ModelPart upperwingR;
	private final ModelPart lowerwingR;
	private final ModelPart tipwingR;
	private final ModelPart tail;
	private final ModelPart end_r1;
	private final ModelPart mid_r1;
	private final ModelPart base_r1;
	private final ModelPart footL;
	private final ModelPart lowerlegL;
	private final ModelPart calfL_r1;
	private final ModelPart upperlegL;
	private final ModelPart thighL_r1;
	private final ModelPart legL;
	private final ModelPart footR;
	private final ModelPart lowerlegR;
	private final ModelPart calfR_r1;
	private final ModelPart upperlegR;
	private final ModelPart thighR_r1;
	private final ModelPart legR;*/
	public DimorphModel(ModelPart root) {
		this.controller = root.getChild("controller");
		this.head = controller.getChild( "mainbody").getChild( "upperbody").getChild( "head");
		this.wingL = controller.getChild( "mainbody").getChild( "upperbody").getChild( "wingL").getChild( "upperwingL").getChild( "lowerwingL").getChild( "tipwingL");
		this.wingR = controller.getChild( "mainbody").getChild( "upperbody").getChild( "wingR").getChild( "upperwingR").getChild( "lowerwingR").getChild( "tipwingR");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData mainbody = controller.addChild("mainbody", ModelPartBuilder.create().uv(0, 26).cuboid(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 5.0F));

		ModelPartData upperbody = mainbody.addChild("upperbody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -2.0F));

		ModelPartData upperbody_r1 = upperbody.addChild("upperbody_r1", ModelPartBuilder.create().uv(0, 17).cuboid(-2.0F, -1.15F, -3.85F, 4.0F, 2.0F, 6.0F, new Dilation(-0.15F)), ModelTransform.of(0.0F, 0.0F, -1.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData head = upperbody.addChild("head", ModelPartBuilder.create().uv(20, 56).cuboid(-2.0F, -2.0F, -5.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -5.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 8).cuboid(-2.0F, -1.2F, -4.8F, 4.0F, 2.0F, 6.0F, new Dilation(-0.4F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData wingL = upperbody.addChild("wingL", ModelPartBuilder.create().uv(44, 36).cuboid(-0.7F, -0.45F, -3.65F, 3.0F, 1.0F, 7.0F, new Dilation(-0.35F)), ModelTransform.of(1.2F, -0.6F, -1.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData upperwingL = wingL.addChild("upperwingL", ModelPartBuilder.create().uv(44, 45).cuboid(-0.56F, -0.36F, -2.84F, 5.0F, 1.0F, 5.0F, new Dilation(-0.37F)), ModelTransform.of(2.0F, -0.1F, -0.8F, 0.0F, 0.0F, 0.7854F));

		ModelPartData lowerwingL = upperwingL.addChild("lowerwingL", ModelPartBuilder.create().uv(44, 52).cuboid(-0.6F, -0.4F, -1.6F, 5.0F, 1.0F, 5.0F, new Dilation(-0.4F)), ModelTransform.of(4.0F, 0.0F, -1.2F, 0.0F, -1.6581F, 0.0F));

		ModelPartData tipwingL = lowerwingL.addChild("tipwingL", ModelPartBuilder.create().uv(44, 60).cuboid(-0.62F, -0.42F, -1.08F, 7.0F, 1.0F, 3.0F, new Dilation(-0.43F)), ModelTransform.of(4.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

		ModelPartData wingR = upperbody.addChild("wingR", ModelPartBuilder.create().uv(44, 36).cuboid(-1.7F, -0.45F, -3.65F, 3.0F, 1.0F, 7.0F, new Dilation(-0.35F)), ModelTransform.of(-1.8F, -0.6F, -1.0F, 0.0F, 0.0F, -0.0873F));

		ModelPartData upperwingR = wingR.addChild("upperwingR", ModelPartBuilder.create().uv(44, 45).cuboid(-4.26F, -0.36F, -2.84F, 5.0F, 1.0F, 5.0F, new Dilation(-0.37F)), ModelTransform.of(-1.3F, -0.1F, -0.8F, 0.0F, 0.0F, -0.8727F));

		ModelPartData lowerwingR = upperwingR.addChild("lowerwingR", ModelPartBuilder.create().uv(44, 52).cuboid(-4.6F, -0.4F, -1.6F, 5.0F, 1.0F, 5.0F, new Dilation(-0.4F)), ModelTransform.of(-3.7F, 0.0F, -1.2F, 0.0F, 1.6581F, 0.0F));

		ModelPartData tipwingR = lowerwingR.addChild("tipwingR", ModelPartBuilder.create().uv(44, 60).cuboid(-6.42F, -0.42F, -1.08F, 7.0F, 1.0F, 3.0F, new Dilation(-0.43F)), ModelTransform.of(-4.2F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		ModelPartData tail = mainbody.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -0.4F, 1.0F));

		ModelPartData end_r1 = tail.addChild("end_r1", ModelPartBuilder.create().uv(0, 43).cuboid(-1.0F, -0.5627F, -0.3535F, 2.0F, 1.0F, 6.0F, new Dilation(-0.2F)), ModelTransform.of(0.0F, 1.2F, 9.7F, -0.2182F, 0.0F, 0.0F));

		ModelPartData mid_r1 = tail.addChild("mid_r1", ModelPartBuilder.create().uv(0, 35).cuboid(-1.0F, -0.4606F, -0.7284F, 2.0F, 1.0F, 6.0F, new Dilation(-0.15F)), ModelTransform.of(0.0F, 0.3F, 5.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData base_r1 = tail.addChild("base_r1", ModelPartBuilder.create().uv(0, 35).cuboid(-1.0F, -0.4443F, -0.8704F, 2.0F, 1.0F, 6.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		ModelPartData footL = controller.addChild("footL", ModelPartBuilder.create().uv(42, 0).cuboid(-0.91F, 2.2412F, -2.9817F, 1.0F, 1.0F, 3.0F, new Dilation(-0.4F)), ModelTransform.pivot(-2.0F, -2.9156F, 4.6147F));

		ModelPartData lowerlegL = footL.addChild("lowerlegL", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.7413F, 0.9086F));

		ModelPartData calfL_r1 = lowerlegL.addChild("calfL_r1", ModelPartBuilder.create().uv(56, 15).cuboid(-0.4F, -0.2257F, -2.5512F, 1.0F, 1.0F, 3.0F, new Dilation(-0.3F)), ModelTransform.of(-0.5F, -2.4F, 0.4F, 0.5672F, 0.0F, 0.0F));

		ModelPartData upperlegL = lowerlegL.addChild("upperlegL", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.8257F, -0.1234F, 0.7418F, 0.0F, 0.0F));

		ModelPartData thighL_r1 = upperlegL.addChild("thighL_r1", ModelPartBuilder.create().uv(60, 6).cuboid(-0.5F, -0.7222F, -0.4293F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.of(-0.4F, -0.7982F, -0.2497F, 0.1745F, 0.0F, 0.0F));

		ModelPartData legL = upperlegL.addChild("legL", ModelPartBuilder.create().uv(56, 0).cuboid(-1.0F, -1.0775F, -2.3781F, 2.0F, 2.0F, 2.0F, new Dilation(-0.15F)), ModelTransform.pivot(0.0F, -1.0725F, 1.5281F));

		ModelPartData footR = controller.addChild("footR", ModelPartBuilder.create().uv(42, 0).cuboid(-0.11F, 2.2412F, -2.9817F, 1.0F, 1.0F, 3.0F, new Dilation(-0.4F)), ModelTransform.pivot(2.0F, -2.9156F, 4.6147F));

		ModelPartData lowerlegR = footR.addChild("lowerlegR", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.7413F, 0.9086F));

		ModelPartData calfR_r1 = lowerlegR.addChild("calfR_r1", ModelPartBuilder.create().uv(56, 15).cuboid(-0.4F, -0.2257F, -2.5512F, 1.0F, 1.0F, 3.0F, new Dilation(-0.3F)), ModelTransform.of(0.3F, -2.4F, 0.4F, 0.5672F, 0.0F, 0.0F));

		ModelPartData upperlegR = lowerlegR.addChild("upperlegR", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.8257F, -0.1234F, 0.7418F, 0.0F, 0.0F));

		ModelPartData thighR_r1 = upperlegR.addChild("thighR_r1", ModelPartBuilder.create().uv(60, 6).cuboid(-0.5F, -0.5808F, -0.4293F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.of(0.4F, -0.9395F, -0.2559F, 0.1745F, 0.0F, 0.0F));

		ModelPartData legR = upperlegR.addChild("legR", ModelPartBuilder.create().uv(49, 5).cuboid(-1.0F, -1.0775F, -2.3781F, 2.0F, 2.0F, 2.0F, new Dilation(-0.15F)), ModelTransform.pivot(0.0F, -1.0725F, 1.5281F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(DimorphEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		if (!entity.isOnGround() /*&& entity.getVelocity().length() >= 0.01*/) {
			//this.animateMovement(DimorphAnimations.DIMORPH_FLYING, limbSwing, limbSwingAmount, 2f, 4.5f);
			this.updateAnimation(DimorphEntity.flyingAnimationState, DimorphAnimations.DIMORPH_FLYING, ageInTicks, 1f);
		} else if (entity.isOnGround() && entity.getVelocity().length() >= 0.01) {
			//this.animateMovement(DimorphAnimations.DIMORPH_WALK, limbSwing, limbSwingAmount, 2f, 4.5f);
			this.updateAnimation(DimorphEntity.walkingAnimationState, DimorphAnimations.DIMORPH_WALK, ageInTicks, 1f);
		} else if (entity.isOnGround() && entity.getVelocity().length() == 0.00) {
			this.updateAnimation(DimorphEntity.idleAnimationState, DimorphAnimations.DIMORPH_IDLE, ageInTicks, 1f);
		} else if (entity.isAttacking() /*&& entity.getVelocity().length() >= 0.00*/) {
			this.updateAnimation(DimorphEntity.attackingAnimationState, DimorphAnimations.DIMORPH_ATTACK, ageInTicks, 1f);
		} else {
			this.updateAnimation(DimorphEntity.sittingAnimationState, DimorphAnimations.DIMORPH_SITTING, ageInTicks, 1f);
		}
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
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
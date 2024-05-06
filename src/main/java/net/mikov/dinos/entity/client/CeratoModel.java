// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.AnkyAnimations;
import net.mikov.dinos.entity.animation.CeratoAnimations;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.mikov.dinos.entity.custom.CeratoEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CeratoModel<T extends CeratoEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public CeratoModel(ModelPart root) {

		this.controller = root.getChild("controller");
		this.head = controller.getChild( "upperbody").getChild( "neck").getChild( "head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create(), ModelTransform.of(0.0F, 7.0F, 3.0F, 1.5708F, -1.4835F, -1.5708F));

		ModelPartData lowerbody = controller.addChild("lowerbody", ModelPartBuilder.create().uv(0, 110).cuboid(-11.0F, -4.0F, -5.0F, 20.0F, 8.0F, 10.0F, new Dilation(0.0F))
		.uv(86, 120).cuboid(-6.0F, 2.0F, -2.0F, 17.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 0.0F, 0.0F));

		ModelPartData legL = lowerbody.addChild("legL", ModelPartBuilder.create().uv(31, 97).cuboid(-4.0F, -3.0F, -2.0F, 8.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 0.0F, -5.0F, 0.0038F, -0.0872F, -0.0438F));

		ModelPartData upperlegL = legL.addChild("upperlegL", ModelPartBuilder.create().uv(71, 94).cuboid(-2.5F, -1.0F, -1.5F, 5.0F, 10.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(-1.5F, 1.0F, -0.5F, 0.0F, 0.0F, 0.5236F));

		ModelPartData lowerlegL = upperlegL.addChild("lowerlegL", ModelPartBuilder.create().uv(24, 80).cuboid(-1.5F, -1.0F, -1.0F, 3.0F, 8.0F, 2.0F, new Dilation(-0.04F)), ModelTransform.of(-0.7082F, 8.9354F, -0.526F, 0.0F, 0.0F, -0.6109F));

		ModelPartData footL = lowerlegL.addChild("footL", ModelPartBuilder.create().uv(87, 110).cuboid(-3.0F, 0.0F, -1.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 6.0F, 0.0F));

		ModelPartData talonsL = footL.addChild("talonsL", ModelPartBuilder.create().uv(63, 93).cuboid(-2.3296F, -1.0F, -0.507F, 3.0F, 2.0F, 1.0F, new Dilation(-0.1F))
		.uv(81, 108).cuboid(-3.3296F, -1.0F, -0.507F, 2.0F, 2.0F, 1.0F, new Dilation(-0.15F)), ModelTransform.of(-2.6704F, 1.1F, 0.007F, 0.0F, 0.0F, 0.2182F));

		ModelPartData talonail3L_r1 = talonsL.addChild("talonail3L_r1", ModelPartBuilder.create().uv(81, 108).cuboid(-3.05F, -0.95F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(-0.15F))
		.uv(63, 93).cuboid(-2.05F, -0.95F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-0.2796F, -0.05F, 0.993F, 0.0F, 0.4363F, 0.0F));

		ModelPartData talonail2L_r1 = talonsL.addChild("talonail2L_r1", ModelPartBuilder.create().uv(81, 108).cuboid(-3.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(-0.15F))
		.uv(63, 93).cuboid(-2.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-0.3296F, 0.0F, -1.007F, 0.0F, -0.4363F, 0.0F));

		ModelPartData legR = lowerbody.addChild("legR", ModelPartBuilder.create().uv(36, 78).cuboid(-4.0F, -3.0F, -1.0F, 8.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 0.0F, 5.0F, -0.0076F, 0.0869F, -0.044F));

		ModelPartData upperlegR = legR.addChild("upperlegR", ModelPartBuilder.create().uv(54, 100).cuboid(-2.5F, -1.0F, -1.5F, 5.0F, 10.0F, 3.0F, new Dilation(-0.01F)), ModelTransform.of(-1.5F, 1.0F, 0.5F, 0.0F, 0.0F, 0.5236F));

		ModelPartData lowerlegR = upperlegR.addChild("lowerlegR", ModelPartBuilder.create().uv(12, 80).cuboid(-1.5F, -1.0F, -1.0F, 3.0F, 8.0F, 2.0F, new Dilation(-0.04F)), ModelTransform.of(-0.7082F, 8.9354F, 0.5261F, 0.0F, 0.0F, -0.6109F));

		ModelPartData footR = lowerlegR.addChild("footR", ModelPartBuilder.create().uv(51, 115).cuboid(-3.0F, 0.0F, -1.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 6.0F, 0.0F));

		ModelPartData talonsR = footR.addChild("talonsR", ModelPartBuilder.create().uv(63, 93).cuboid(-2.3296F, -1.0F, -0.493F, 3.0F, 2.0F, 1.0F, new Dilation(-0.1F))
		.uv(81, 108).cuboid(-3.3296F, -1.0F, -0.493F, 2.0F, 2.0F, 1.0F, new Dilation(-0.15F)), ModelTransform.of(-2.6704F, 1.1F, -0.007F, 0.0F, 0.0F, 0.2182F));

		ModelPartData talonail3R_r1 = talonsR.addChild("talonail3R_r1", ModelPartBuilder.create().uv(81, 108).cuboid(-3.05F, -0.95F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(-0.15F))
		.uv(63, 93).cuboid(-2.05F, -0.95F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-0.2796F, -0.05F, -0.993F, 0.0F, -0.4363F, 0.0F));

		ModelPartData talonail2R_r1 = talonsR.addChild("talonail2R_r1", ModelPartBuilder.create().uv(81, 108).cuboid(-3.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(-0.15F))
		.uv(63, 93).cuboid(-2.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-0.3296F, 0.0F, 1.007F, 0.0F, 0.4363F, 0.0F));

		ModelPartData tail = lowerbody.addChild("tail", ModelPartBuilder.create().uv(89, 95).cuboid(-2.0F, -3.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -0.8F, 0.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData tail2 = tail.addChild("tail2", ModelPartBuilder.create().uv(94, 109).cuboid(0.0F, -3.0F, -3.0F, 11.0F, 5.0F, 6.0F, new Dilation(-0.01F)), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.0873F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(31, 88).cuboid(-0.5F, -2.5F, -2.0F, 11.0F, 4.0F, 4.0F, new Dilation(-0.02F)), ModelTransform.of(10.5F, -0.5F, 0.0F, 0.0F, 0.0873F, 0.0873F));

		ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(0, 91).cuboid(-0.5F, -2.5F, -2.0F, 11.0F, 3.0F, 4.0F, new Dilation(-0.03F)), ModelTransform.of(10.0F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.0873F));

		ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create().uv(58, 86).cuboid(-0.5F, -2.5F, -1.0F, 11.0F, 3.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.of(10.0F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.0873F));

		ModelPartData upperbody = controller.addChild("upperbody", ModelPartBuilder.create().uv(92, 63).cuboid(-7.0F, -3.0F, -5.0F, 8.0F, 7.0F, 10.0F, new Dilation(-0.01F)), ModelTransform.of(-10.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData armL = upperbody.addChild("armL", ModelPartBuilder.create().uv(120, 84).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.3F, 2.0F, -5.0F, -0.0873F, -0.0873F, 0.0F));

		ModelPartData upperarmL = armL.addChild("upperarmL", ModelPartBuilder.create().uv(115, 97).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 1.0F, -0.5F, 0.0F, 0.0F, 0.1309F));

		ModelPartData lowerarmL = upperarmL.addChild("lowerarmL", ModelPartBuilder.create().uv(124, 103).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(-0.02F)), ModelTransform.of(-0.5F, 3.0F, 0.0F, -0.0873F, 0.0F, 0.1745F));

		ModelPartData clawsL = lowerarmL.addChild("clawsL", ModelPartBuilder.create(), ModelTransform.of(-0.0272F, 2.748F, -0.2F, 0.1745F, 0.0F, 0.0F));

		ModelPartData claw3L_r1 = clawsL.addChild("claw3L_r1", ModelPartBuilder.create().uv(87, 115).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(-0.2728F, 0.052F, 0.0F, 0.0F, 0.0F, 0.3491F));

		ModelPartData claw2L_r1 = clawsL.addChild("claw2L_r1", ModelPartBuilder.create().uv(87, 115).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(0.3272F, 0.052F, 0.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData claw1L_r1 = clawsL.addChild("claw1L_r1", ModelPartBuilder.create().uv(87, 115).cuboid(-0.5F, -0.3F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(0.0272F, 0.052F, 0.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData armR = upperbody.addChild("armR", ModelPartBuilder.create().uv(85, 83).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.3F, 2.0F, 5.0F, 0.0873F, 0.0873F, 0.0F));

		ModelPartData upperarmR = armR.addChild("upperarmR", ModelPartBuilder.create().uv(122, 96).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 4.0F, 1.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 1.0F, 0.5F, 0.0F, 0.0F, 0.3927F));

		ModelPartData lowerarmR = upperarmR.addChild("lowerarmR", ModelPartBuilder.create().uv(124, 109).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(-0.02F)), ModelTransform.of(-0.5F, 3.0F, 0.0F, -0.1745F, 0.0F, 0.0873F));

		ModelPartData clawsR = lowerarmR.addChild("clawsR", ModelPartBuilder.create(), ModelTransform.of(-0.0272F, 2.748F, 0.2F, -0.2618F, 0.0F, 0.0F));

		ModelPartData claw3R_r1 = clawsR.addChild("claw3R_r1", ModelPartBuilder.create().uv(87, 115).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(-0.2728F, 0.052F, 0.0F, 0.0F, 0.0F, 0.3491F));

		ModelPartData claw2R_r1 = clawsR.addChild("claw2R_r1", ModelPartBuilder.create().uv(87, 115).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(0.3272F, 0.052F, 0.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData claw1R_r1 = clawsR.addChild("claw1R_r1", ModelPartBuilder.create().uv(87, 115).cuboid(-0.5F, -0.3F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(0.0272F, 0.052F, 0.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData neck = upperbody.addChild("neck", ModelPartBuilder.create().uv(65, 108).cuboid(-2.0F, -12.0F, -3.0F, 4.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(87, 81).cuboid(-10.799F, -2.8564F, -4.0F, 12.0F, 5.0F, 8.0F, new Dilation(0.01F))
		.uv(76, 75).cuboid(-10.699F, 1.3436F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-10.699F, 1.5436F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-10.699F, 1.8436F, -3.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-9.699F, 1.5436F, -3.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-8.699F, 1.3436F, -3.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-7.699F, 1.3436F, -3.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-6.699F, 1.3436F, -3.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-10.699F, 1.3436F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-10.699F, 1.5436F, 1.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-10.699F, 1.8436F, 2.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-9.699F, 1.5436F, 2.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-8.699F, 1.3436F, 2.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-7.699F, 1.3436F, 2.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-6.699F, 1.3436F, 2.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.of(-0.5724F, -10.2968F, 0.0F, 0.0F, 0.0F, 0.6109F));

		ModelPartData crestR_r1 = head.addChild("crestR_r1", ModelPartBuilder.create().uv(88, 95).cuboid(-1.2121F, -2.7879F, -1.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.399F, -1.4564F, 3.0F, 0.124F, -0.1231F, -0.7931F));

		ModelPartData crestL_r1 = head.addChild("crestL_r1", ModelPartBuilder.create().uv(88, 95).cuboid(-1.2121F, -2.7879F, 0.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.399F, -1.4564F, -3.0F, -0.124F, 0.1231F, -0.7931F));

		ModelPartData horntip_r1 = head.addChild("horntip_r1", ModelPartBuilder.create().uv(3, 110).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.09F)), ModelTransform.of(-9.199F, -5.6564F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData horn_r1 = head.addChild("horn_r1", ModelPartBuilder.create().uv(1, 114).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.199F, -2.3564F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData mouth = head.addChild("mouth", ModelPartBuilder.create(), ModelTransform.of(-1.799F, 2.2436F, 0.0F, 0.0F, 0.0F, 0.6981F));

		ModelPartData tooth28_r1 = mouth.addChild("tooth28_r1", ModelPartBuilder.create().uv(76, 75).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-1.5F, -0.4F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-2.5F, -0.1F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.5F, 0.1F, -0.4F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.8F, 0.0F, 0.4F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.8F, -0.1F, 1.2F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.8F, -0.3F, 2.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.8F, -0.3F, 3.0F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.8F, -0.1F, 3.8F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.8F, 0.0F, 4.6F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-3.5F, 0.1F, 5.4F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-2.5F, -0.1F, 5.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-1.5F, -0.4F, 5.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F))
		.uv(76, 75).cuboid(-0.5F, 0.0F, 5.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F)), ModelTransform.of(-3.6929F, 2.0497F, -3.0F, 0.0F, 0.0F, -0.7854F));

		ModelPartData jaw_r1 = mouth.addChild("jaw_r1", ModelPartBuilder.create().uv(0, 99).cuboid(-9.0F, -1.0F, -4.0F, 11.0F, 2.0F, 8.0F, new Dilation(-0.3F)), ModelTransform.of(0.9F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(CeratoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(CeratoAnimations.CERATO_WALK, limbSwing, limbSwingAmount, 2f, 4.5f);
		this.updateAnimation(entity.idleAnimationState, CeratoAnimations.CERATO_IDLE, ageInTicks, 1f);
		//this.updateAnimation(entity.sittingAnimationState, CeratoAnimations.CERATO_SIT, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, CeratoAnimations.CERATO_ATTACK, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -10.0F, 10.0F);
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
// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.MeganeuraAnimations;
import net.mikov.dinos.entity.custom.MeganeuraEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MeganeuraModel<T extends MeganeuraEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;

	public MeganeuraModel(ModelPart root) {

		this.controller = root.getChild("controller");
	}
	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(0, 56).cuboid(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 21.7F, -2.0F));

		ModelPartData body_r1 = controller.addChild("body_r1", ModelPartBuilder.create().uv(6, 52).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(6, 52).mirrored().cuboid(-0.5F, -1.0F, -2.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(6, 52).mirrored().cuboid(-0.5F, -1.0F, -4.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)).mirrored(false), ModelTransform.of(-1.5F, 1.0F, 1.8F, 0.0F, 0.0F, 1.309F));

		ModelPartData body_r2 = controller.addChild("body_r2", ModelPartBuilder.create().uv(6, 52).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.35F)).mirrored(false)
		.uv(6, 52).mirrored().cuboid(-0.5F, -1.0F, -2.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.35F)).mirrored(false)
		.uv(6, 52).mirrored().cuboid(-0.5F, -1.0F, -4.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.35F)).mirrored(false), ModelTransform.of(-2.3F, 1.7F, 1.8F, 0.0F, 0.0F, 0.3491F));

		ModelPartData body_r3 = controller.addChild("body_r3", ModelPartBuilder.create().uv(6, 52).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.35F))
		.uv(6, 52).cuboid(-0.5F, -1.0F, 1.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.35F))
		.uv(6, 52).cuboid(-0.5F, -1.0F, 3.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.35F)), ModelTransform.of(2.3F, 1.7F, -2.2F, 0.0F, 0.0F, -0.3491F));

		ModelPartData body_r4 = controller.addChild("body_r4", ModelPartBuilder.create().uv(6, 52).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F))
		.uv(6, 52).cuboid(-0.5F, -1.0F, 1.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F))
		.uv(6, 52).cuboid(-0.5F, -1.0F, 3.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(1.5F, 1.0F, -2.2F, 0.0F, 0.0F, -1.309F));

		ModelPartData lower = controller.addChild("lower", ModelPartBuilder.create().uv(0, 33).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 0.0F, 2.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tailspike2_r1 = lower.addChild("tailspike2_r1", ModelPartBuilder.create().uv(11, 52).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.of(0.5F, -0.4F, 10.0F, 0.0F, 0.0873F, 0.0F));

		ModelPartData tailspike1_r1 = lower.addChild("tailspike1_r1", ModelPartBuilder.create().uv(11, 52).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.1F)), ModelTransform.of(-0.5F, -0.4F, 10.0F, 0.0F, -0.0873F, 0.0F));

		ModelPartData head = controller.addChild("head", ModelPartBuilder.create().uv(11, 57).cuboid(-1.0F, -1.32F, -2.28F, 2.0F, 2.0F, 2.0F, new Dilation(0.2F))
		.uv(0, 46).cuboid(0.3F, -1.62F, -2.58F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(7, 46).mirrored().cuboid(-1.3F, -1.62F, -2.58F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.32F, -2.72F));

		ModelPartData mandible2_r1 = head.addChild("mandible2_r1", ModelPartBuilder.create().uv(14, 47).mirrored().cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.4F)).mirrored(false), ModelTransform.of(-0.5F, 0.78F, -2.78F, 0.2618F, 0.4363F, 0.0F));

		ModelPartData mandible1_r1 = head.addChild("mandible1_r1", ModelPartBuilder.create().uv(14, 47).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.4F)), ModelTransform.of(0.5F, 0.78F, -2.78F, 0.2618F, -0.4363F, 0.0F));

		ModelPartData wingfrontL = controller.addChild("wingfrontL", ModelPartBuilder.create().uv(20, 49).cuboid(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -1.0F, -1.5F, 0.0F, 0.0F, 1.309F));

		ModelPartData wingfrontR = controller.addChild("wingfrontR", ModelPartBuilder.create().uv(20, 49).mirrored().cuboid(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -1.0F, -1.5F, 0.0F, 0.0F, -1.309F));

		ModelPartData wingrearL = controller.addChild("wingrearL", ModelPartBuilder.create().uv(20, 49).cuboid(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -1.0F, 1.5F, 0.0F, 0.0F, 1.309F));

		ModelPartData wingrearR = controller.addChild("wingrearR", ModelPartBuilder.create().uv(20, 49).mirrored().cuboid(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -1.0F, 1.5F, 0.0F, 0.0F, -1.309F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(MeganeuraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		if (!entity.isOnGround() /*&& entity.getVelocity().length() >= 0.01*/) {
			//this.animateMovement(MeganeuraAnimations.MEGANEURA_FLYING, limbSwing, limbSwingAmount, 2f, 4.5f);
			this.updateAnimation(MeganeuraEntity.flyingAnimationState, MeganeuraAnimations.MEGANEURA_FLYING, ageInTicks, 1f);
		} else if (entity.isOnGround() && entity.getVelocity().length() >= 0.01) {
			//this.animateMovement(MeganeuraAnimations.MEGANEURA_FLYING, limbSwing, limbSwingAmount, 2f, 4.5f);
			this.updateAnimation(MeganeuraEntity.walkingAnimationState, MeganeuraAnimations.MEGANEURA_IDLE, ageInTicks, 1f);
		} else if (entity.isOnGround() && entity.getVelocity().length() == 0.00) {
			this.updateAnimation(MeganeuraEntity.idleAnimationState, MeganeuraAnimations.MEGANEURA_IDLE, ageInTicks, 1f);
		} else if (entity.isAttacking() /*&& entity.getVelocity().length() >= 0.00*/) {
			this.updateAnimation(MeganeuraEntity.attackingAnimationState, MeganeuraAnimations.MEGANEURA_ATTACK, ageInTicks, 1f);
		} else {
			this.updateAnimation(MeganeuraEntity.sittingAnimationState, MeganeuraAnimations.MEGANEURA_IDLE, ageInTicks, 1f);
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
package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.custom.DodoEntity;// Made with Blockbench 4.9.4
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.mikov.dinos.entity.animation.ModAnimations;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.MathHelper;

// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

public class DodoModel<T extends DodoEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public DodoModel(ModelPart root) {
		this.controller = root.getChild("controller");
		this.head = controller.getChild("body").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = controller.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.0F, 0.0F));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(1, 11).cuboid(-3.0F, -4.0F, -4.0F, 6.0F, 8.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(2, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -4.0F));

		ModelPartData beak = head.addChild("beak", ModelPartBuilder.create().uv(17, 0).cuboid(-1.0F, -4.0F, -5.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData tip = beak.addChild("tip", ModelPartBuilder.create().uv(24, 12).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 1.0F, new Dilation(-0.05F)), ModelTransform.pivot(0.0F, -1.0F, -3.0F));

		ModelPartData jaw = beak.addChild("jaw", ModelPartBuilder.create().uv(42, 0).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leg0 = body.addChild("leg0", ModelPartBuilder.create().uv(31, 26).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 3.0F, 1.0F, 0.0F, 0.2618F, 0.0F));

		ModelPartData leg1 = body.addChild("leg1", ModelPartBuilder.create().uv(28, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 3.0F, 1.0F, 0.0F, -0.2618F, 0.0F));

		ModelPartData wing0 = body.addChild("wing0", ModelPartBuilder.create().uv(29, 12).cuboid(-1.0F, 0.0F, -4.0F, 1.0F, 4.0F, 7.0F, new Dilation(0.1F)), ModelTransform.pivot(-3.0F, -3.0F, 0.0F));

		ModelPartData wing1 = body.addChild("wing1", ModelPartBuilder.create().uv(29, 12).cuboid(0.0F, 0.0F, -4.0F, 1.0F, 4.0F, 7.0F, new Dilation(0.1F)), ModelTransform.pivot(3.0F, -3.0F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(1, 27).cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 3.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(DodoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.DODO_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.DODO_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.sittingAnimationState, ModAnimations.DODO_SITTING, ageInTicks, 1f);
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
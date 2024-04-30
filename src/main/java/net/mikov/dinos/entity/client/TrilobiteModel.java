// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.AnkyAnimations;
import net.mikov.dinos.entity.animation.TriloAnimations;
import net.mikov.dinos.entity.custom.TrilobiteEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TrilobiteModel<T extends TrilobiteEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;

	public TrilobiteModel(ModelPart root) {
		this.controller = root.getChild("controller");
	}

	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(34, 56).cuboid(-5.0F, -3.0F, -2.0F, 10.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, -5.0F));

		ModelPartData leg2_r1 = controller.addChild("leg2_r1", ModelPartBuilder.create().uv(29, 61).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.5F, 0.0F, 1.5F, -0.1745F, 0.0F, 0.6981F));

		ModelPartData leg1_r1 = controller.addChild("leg1_r1", ModelPartBuilder.create().uv(29, 61).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, 0.0F, 1.5F, -0.1745F, 0.0F, -0.6981F));

		ModelPartData headspike3_r1 = controller.addChild("headspike3_r1", ModelPartBuilder.create().uv(36, 21).mirrored().cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, -1.0F, 1.0F, -0.609F, 0.1038F, -1.4525F));

		ModelPartData headspike2_r1 = controller.addChild("headspike2_r1", ModelPartBuilder.create().uv(36, 29).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -1.0F, 1.0F, -0.609F, -0.1038F, 1.4525F));

		ModelPartData headspike1_r1 = controller.addChild("headspike1_r1", ModelPartBuilder.create().uv(45, 27).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 1.0F, -0.6545F, 0.0F, 0.0F));

		ModelPartData midbody = controller.addChild("midbody", ModelPartBuilder.create().uv(0, 56).cuboid(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 6.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, -1.3F, 3.0F));

		ModelPartData leg6_r1 = midbody.addChild("leg6_r1", ModelPartBuilder.create().uv(29, 61).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 1.3F, 3.5F, 0.0F, 0.0F, -0.6981F));

		ModelPartData leg5_r1 = midbody.addChild("leg5_r1", ModelPartBuilder.create().uv(29, 61).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(29, 61).mirrored().cuboid(-0.5F, -1.0F, -2.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, 1.3F, 3.5F, 0.0F, 0.0F, 0.6981F));

		ModelPartData leg3_r1 = midbody.addChild("leg3_r1", ModelPartBuilder.create().uv(29, 61).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 1.3F, 1.5F, 0.0F, 0.0F, -0.6981F));

		ModelPartData midspike2_r1 = midbody.addChild("midspike2_r1", ModelPartBuilder.create().uv(37, 13).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -0.7F, 3.0F, -0.6167F, -0.2351F, -0.3175F));

		ModelPartData midspike1_r1 = midbody.addChild("midspike1_r1", ModelPartBuilder.create().uv(45, 19).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -0.7F, 3.0F, -0.6247F, 0.2097F, 0.2811F));

		ModelPartData lowerbody = midbody.addChild("lowerbody", ModelPartBuilder.create().uv(20, 47).cuboid(-3.0F, -1.0F, -1.5F, 6.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.3F, 5.5F));

		ModelPartData leg10_r1 = lowerbody.addChild("leg10_r1", ModelPartBuilder.create().uv(29, 61).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 1.0F, 2.0F, 0.1745F, 0.0F, -0.6981F));

		ModelPartData leg9_r1 = lowerbody.addChild("leg9_r1", ModelPartBuilder.create().uv(29, 61).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, 1.0F, 2.0F, 0.1745F, 0.0F, 0.6981F));

		ModelPartData leg8_r1 = lowerbody.addChild("leg8_r1", ModelPartBuilder.create().uv(29, 61).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		ModelPartData leg7_r1 = lowerbody.addChild("leg7_r1", ModelPartBuilder.create().uv(29, 61).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, 1.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		ModelPartData lowerspike_r1 = lowerbody.addChild("lowerspike_r1", ModelPartBuilder.create().uv(54, 24).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 2.5F, -0.8727F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(TrilobiteEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateMovement(TriloAnimations.TRILO_WALK, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, TriloAnimations.TRILO_IDLE, ageInTicks, 1f);
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
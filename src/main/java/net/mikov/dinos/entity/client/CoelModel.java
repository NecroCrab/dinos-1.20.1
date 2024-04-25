// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.ModAnimations;
import net.mikov.dinos.entity.custom.CoelEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CoelModel<T extends CoelEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public CoelModel(ModelPart root) {
		this.controller = root.getChild("controller");
		this.head = controller.getChild("mainbody");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 20.0F, -5.0F));

		ModelPartData mainbody = controller.addChild("mainbody", ModelPartBuilder.create().uv(10, 0).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.01F))
		.uv(0, 9).cuboid(0.5F, -0.3F, -0.3F, 1.0F, 3.0F, 3.0F, new Dilation(-0.5F))
		.uv(0, 9).cuboid(-1.5F, -0.3F, -0.3F, 1.0F, 3.0F, 3.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData midbody = mainbody.addChild("midbody", ModelPartBuilder.create().uv(10, 6).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.02F))
		.uv(12, 16).cuboid(-0.5F, -3.3F, -0.4F, 1.0F, 3.0F, 3.0F, new Dilation(-0.5F))
		.uv(0, 22).cuboid(-0.5F, -0.3F, 0.2F, 1.0F, 3.0F, 3.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		ModelPartData lowerbody = midbody.addChild("lowerbody", ModelPartBuilder.create().uv(20, 5).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		ModelPartData tailbody = lowerbody.addChild("tailbody", ModelPartBuilder.create().uv(12, 11).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 2.0F, new Dilation(-0.01F))
		.uv(0, 16).cuboid(-0.5F, -2.3F, -1.4F, 1.0F, 2.0F, 3.0F, new Dilation(-0.5F))
		.uv(6, 13).cuboid(-0.5F, 0.3F, -1.3F, 1.0F, 2.0F, 3.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		ModelPartData tail = tailbody.addChild("tail", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.5F, -0.6F, 1.0F, 3.0F, 3.0F, new Dilation(-0.5F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(CoelEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.COEL_SWIM, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.COEL_IDLE, ageInTicks, 1f);

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
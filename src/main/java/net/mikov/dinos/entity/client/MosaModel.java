// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.MosaAnimations;
import net.mikov.dinos.entity.custom.MosaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MosaModel<T extends MosaEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;

	public MosaModel(ModelPart root) {

		this.controller = root.getChild("controller");

	}
	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(388, 458).cuboid(-15.0F, -11.0F, -16.0F, 30.0F, 22.0F, 32.0F, new Dilation(0.0F))
		.uv(304, 392).cuboid(-13.0F, -12.0F, -16.0F, 26.0F, 1.0F, 32.0F, new Dilation(0.0F))
		.uv(394, 421).cuboid(-13.0F, 11.0F, -16.0F, 26.0F, 1.0F, 32.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData lower = controller.addChild("lower", ModelPartBuilder.create().uv(392, 365).cuboid(-14.0F, -10.0F, -2.3333F, 28.0F, 20.0F, 32.0F, new Dilation(0.0F))
		.uv(0, 353).cuboid(-12.0F, -11.0F, -2.3333F, 24.0F, 1.0F, 30.0F, new Dilation(0.0F))
		.uv(0, 319).cuboid(-12.0F, 10.0F, -2.3333F, 24.0F, 1.0F, 30.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 16.3333F, 0.0436F, 0.0F, 0.0F));

		ModelPartData lowerfinL = lower.addChild("lowerfinL", ModelPartBuilder.create().uv(397, 433).cuboid(-0.5F, -3.5F, -4.0F, 3.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(13.5F, 2.5F, 22.6667F, 0.0F, 0.0F, -0.0873F));

		ModelPartData lowerfinL2 = lowerfinL.addChild("lowerfinL2", ModelPartBuilder.create().uv(488, 474).cuboid(-1.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 0.0F, 0.0F, 0.0F, -0.1309F, -0.2618F));

		ModelPartData lowerfinL3 = lowerfinL2.addChild("lowerfinL3", ModelPartBuilder.create().uv(0, 500).cuboid(0.0F, -1.5F, -4.5F, 10.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.5F, 0.0F, -0.1309F, -0.1309F));

		ModelPartData lowerfinL4 = lowerfinL3.addChild("lowerfinL4", ModelPartBuilder.create().uv(358, 472).cuboid(0.0F, -0.5F, -6.0F, 16.0F, 1.0F, 12.0F, new Dilation(0.0F))
		.uv(42, 501).cuboid(16.0F, -0.5F, -5.0F, 2.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, 0.0F, 0.5F, -0.0057F, -0.1308F, -0.2614F));

		ModelPartData lowerfinR = lower.addChild("lowerfinR", ModelPartBuilder.create().uv(156, 283).mirrored().cuboid(-2.5F, -3.5F, -4.0F, 3.0F, 7.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-13.5F, 2.5F, 22.6667F, 0.0F, 0.0F, 0.0873F));

		ModelPartData lowerfinR2 = lowerfinR.addChild("lowerfinR2", ModelPartBuilder.create().uv(70, 501).mirrored().cuboid(-5.0F, -2.5F, -3.0F, 6.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.2182F));

		ModelPartData lowerfinR3 = lowerfinR2.addChild("lowerfinR3", ModelPartBuilder.create().uv(183, 386).mirrored().cuboid(-10.0F, -1.5F, -4.5F, 10.0F, 3.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.0F, 0.0F, 0.5F, 0.0F, 0.1309F, 0.2182F));

		ModelPartData lowerfinR4 = lowerfinR3.addChild("lowerfinR4", ModelPartBuilder.create().uv(183, 401).mirrored().cuboid(-16.0F, -0.5F, -6.0F, 16.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(114, 286).mirrored().cuboid(-18.0F, -0.5F, -5.0F, 2.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.0F, 0.0F, 0.5F, -0.0057F, 0.0871F, 0.1308F));

		ModelPartData tail1 = lower.addChild("tail1", ModelPartBuilder.create().uv(0, 444).cuboid(-12.0F, -9.0F, -2.0F, 24.0F, 18.0F, 32.0F, new Dilation(0.0F))
		.uv(135, 387).cuboid(-3.0F, -11.0F, -2.0F, 6.0F, 22.0F, 32.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 29.6667F, 0.0438F, -0.0872F, -0.0038F));

		ModelPartData tail2 = tail1.addChild("tail2", ModelPartBuilder.create().uv(316, 340).cuboid(-10.0F, -8.0F, -2.0F, 20.0F, 16.0F, 32.0F, new Dilation(0.0F))
		.uv(87, 358).cuboid(-3.0F, -10.0F, -2.0F, 6.0F, 20.0F, 32.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, 0.0F, 30.0F, -0.0433F, -0.1309F, -0.0019F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(287, 466).cuboid(-8.0F, -7.0F, -2.0F, 16.0F, 14.0F, 32.0F, new Dilation(0.0F))
		.uv(86, 413).cuboid(-3.0F, -10.0F, -2.0F, 6.0F, 20.0F, 32.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 30.0F, -0.0436F, -0.2182F, 0.0F));

		ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(98, 468).cuboid(-6.0F, -6.0F, -1.0F, 12.0F, 12.0F, 32.0F, new Dilation(0.0F))
		.uv(414, 308).cuboid(-3.0F, -10.0F, -1.0F, 6.0F, 20.0F, 32.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 0.0F, 30.0F, -0.044F, 0.1308F, -0.0057F));

		ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create().uv(189, 449).cuboid(-3.0F, -11.0F, -0.5F, 6.0F, 22.0F, 41.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 26.5F, -0.0464F, 0.3487F, -0.0159F));

		ModelPartData upper = controller.addChild("upper", ModelPartBuilder.create().uv(0, 394).cuboid(-14.0F, -10.0F, -25.0F, 28.0F, 20.0F, 27.0F, new Dilation(0.0F))
		.uv(315, 434).cuboid(-12.0F, -11.0F, -24.0F, 24.0F, 1.0F, 26.0F, new Dilation(0.0F))
		.uv(136, 355).cuboid(-12.0F, 10.0F, -23.0F, 24.0F, 1.0F, 25.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -16.0F, -0.0436F, 0.0F, 0.0F));

		ModelPartData upperfinL = upper.addChild("upperfinL", ModelPartBuilder.create().uv(79, 295).cuboid(-0.5F, -3.5F, -4.0F, 3.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(13.5F, 4.5F, -17.0F, 0.0F, 0.0F, 0.0436F));

		ModelPartData upperfinL2 = upperfinL.addChild("upperfinL2", ModelPartBuilder.create().uv(0, 461).cuboid(-1.0F, -2.5F, -3.0F, 8.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 0.0F, 0.0F, 0.0F, -0.0436F, 0.3054F));

		ModelPartData upperfinL3 = upperfinL2.addChild("upperfinL3", ModelPartBuilder.create().uv(462, 324).cuboid(0.0F, -1.5F, -4.5F, 16.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 0.0F, 0.5F, 0.0F, -0.0873F, 0.2182F));

		ModelPartData upperfinL4 = upperfinL3.addChild("upperfinL4", ModelPartBuilder.create().uv(158, 471).cuboid(0.0F, -0.5F, -5.0F, 22.0F, 1.0F, 12.0F, new Dilation(0.0F))
		.uv(284, 349).cuboid(22.0F, -0.5F, -4.0F, 2.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(15.0F, 0.0F, -0.5F, -0.0172F, -0.1298F, 0.132F));

		ModelPartData upperfinR = upper.addChild("upperfinR", ModelPartBuilder.create().uv(195, 282).mirrored().cuboid(-2.5F, -3.5F, -4.0F, 3.0F, 7.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-13.5F, 4.5F, -17.0F, 0.0F, 0.0F, -0.0436F));

		ModelPartData upperfinR2 = upperfinR.addChild("upperfinR2", ModelPartBuilder.create().uv(484, 459).mirrored().cuboid(-7.0F, -2.5F, -3.0F, 8.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, 0.0F, 0.0F, 0.0F, 0.0873F, -0.2618F));

		ModelPartData upperfinR3 = upperfinR2.addChild("upperfinR3", ModelPartBuilder.create().uv(264, 458).mirrored().cuboid(-16.0F, -1.5F, -4.5F, 16.0F, 3.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-6.0F, 0.0F, 0.5F, 0.0F, 0.0873F, -0.2182F));

		ModelPartData upperfinR4 = upperfinR3.addChild("upperfinR4", ModelPartBuilder.create().uv(247, 473).mirrored().cuboid(-22.0F, -0.5F, -5.0F, 22.0F, 1.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 404).mirrored().cuboid(-24.0F, -0.5F, -4.0F, 2.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-15.0F, 0.0F, -0.5F, 0.0F, 0.1309F, -0.1745F));

		ModelPartData head = upper.addChild("head", ModelPartBuilder.create().uv(166, 309).cuboid(-10.0F, -10.6172F, -19.6483F, 20.0F, 13.0F, 20.0F, new Dilation(0.0F))
		.uv(322, 337).cuboid(9.0F, -6.6F, -8.7F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(322, 337).mirrored().cuboid(-11.0F, -6.6F, -8.7F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(259, 367).cuboid(-6.0F, -11.6172F, -23.6483F, 12.0F, 1.0F, 25.0F, new Dilation(0.0F))
		.uv(85, 307).cuboid(-8.0F, -10.6172F, -42.6483F, 16.0F, 12.0F, 23.0F, new Dilation(0.0F))
		.uv(303, 437).cuboid(-6.0F, -10.6172F, -47.6483F, 12.0F, 12.0F, 5.0F, new Dilation(0.0F))
		.uv(466, 302).cuboid(-4.0F, -11.6172F, -38.6483F, 8.0F, 1.0F, 15.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.3828F, -23.3517F));

		ModelPartData tooth24_r1 = head.addChild("tooth24_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(13.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.8F, 2.8828F, -16.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth23_r1 = head.addChild("tooth23_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(13.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.8F, 2.8828F, -19.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth22_r1 = head.addChild("tooth22_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(13.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.8F, 2.8828F, -22.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth21_r1 = head.addChild("tooth21_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(13.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.8F, 2.8828F, -25.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth20_r1 = head.addChild("tooth20_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(13.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.8F, 2.8828F, -28.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth19_r1 = head.addChild("tooth19_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(13.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.8F, 2.8828F, -31.2483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth18_r1 = head.addChild("tooth18_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(12.7F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.6F, 2.8828F, -34.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth17_r1 = head.addChild("tooth17_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(12.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.3F, 2.8828F, -36.8483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth16_r1 = head.addChild("tooth16_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(10.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-5.5F, 2.5828F, -39.5483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth15_r1 = head.addChild("tooth15_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(9.1F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-4.8F, 2.3828F, -42.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth14_r1 = head.addChild("tooth14_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(6.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-3.5F, 2.3828F, -44.1483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tooth13_r1 = head.addChild("tooth13_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(2.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-1.5F, 1.8828F, -46.0483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(222, 409).cuboid(-10.0F, -2.8333F, -18.0F, 20.0F, 7.0F, 21.0F, new Dilation(-0.01F))
		.uv(365, 306).cuboid(-8.0F, -3.8333F, -41.0F, 16.0F, 7.0F, 23.0F, new Dilation(0.0F))
		.uv(36, 302).cuboid(-6.0F, -3.8333F, -45.0F, 12.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.2161F, -1.6483F, 0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth20_r1 = jaw.addChild("lowertooth20_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -3.3333F, -15.5F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth19_r1 = jaw.addChild("lowertooth19_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -4.3333F, -18.4F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth18_r1 = jaw.addChild("lowertooth18_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -5.3333F, -24.3F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth17_r1 = jaw.addChild("lowertooth17_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -5.3333F, -27.1F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth16_r1 = jaw.addChild("lowertooth16_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -5.3333F, -30.1F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth15_r1 = jaw.addChild("lowertooth15_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -5.0333F, -33.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth14_r1 = jaw.addChild("lowertooth14_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(11.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-6.0F, -4.7333F, -35.8F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth13_r1 = jaw.addChild("lowertooth13_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(10.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-5.5F, -4.4333F, -38.5F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth12_r1 = jaw.addChild("lowertooth12_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(7.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-4.0F, -4.2333F, -40.5F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth11_r1 = jaw.addChild("lowertooth11_r1", ModelPartBuilder.create().uv(270, 330).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)).mirrored(false)
		.uv(270, 330).cuboid(4.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(-2.5F, -3.9333F, -42.5F, -0.0873F, 0.0F, 0.0F));

		ModelPartData lowertooth_r1 = jaw.addChild("lowertooth_r1", ModelPartBuilder.create().uv(270, 330).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, -3.7333F, -42.5F, -0.0873F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 512, 512);
	}

	@Override
	public void setAngles(MosaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateMovement(MosaAnimations.MOSA_SWIM, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, MosaAnimations.MOSA_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, MosaAnimations.MOSA_ATTACK, ageInTicks, 1f);
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
// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.TrikeAnimations;
import net.mikov.dinos.entity.custom.TrikeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class TrikeModel<T extends TrikeEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public TrikeModel(ModelPart root) {

		this.controller = root.getChild("controller");
		this.head = controller.getChild( "upper").getChild( "neck").getChild( "head");
	}
	public static TexturedModelData getTexturedModelData() {

		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create(), ModelTransform.of(0.0F, 9.0F, 6.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData lower = controller.addChild("lower", ModelPartBuilder.create().uv(72, 103).cuboid(-7.0F, -5.5F, -6.6667F, 14.0F, 11.0F, 14.0F, new Dilation(0.0F))
		.uv(78, 0).cuboid(-6.0F, -6.5F, -6.6667F, 12.0F, 1.0F, 13.0F, new Dilation(0.0F))
		.uv(78, 44).cuboid(-6.0F, 5.5F, -6.6667F, 12.0F, 1.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, -0.3333F));

		ModelPartData rearlegL = lower.addChild("rearlegL", ModelPartBuilder.create().uv(31, 54).cuboid(-1.0F, -3.5F, -4.0F, 3.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -1.0F, 1.3333F, -0.0438F, -0.0872F, 0.0038F));

		ModelPartData upperlegL = rearlegL.addChild("upperlegL", ModelPartBuilder.create().uv(70, 63).cuboid(-1.5F, -0.5F, -3.0F, 3.0F, 9.0F, 6.0F, new Dilation(-0.01F)), ModelTransform.of(0.5F, 2.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData lowerlegL = upperlegL.addChild("lowerlegL", ModelPartBuilder.create().uv(30, 23).cuboid(-1.5F, -0.5F, -2.5F, 3.0F, 6.0F, 5.0F, new Dilation(-0.02F)), ModelTransform.of(0.0F, 8.0F, -0.5F, 0.3491F, 0.0F, 0.0F));

		ModelPartData footL = lowerlegL.addChild("footL", ModelPartBuilder.create().uv(73, 33).cuboid(-1.5F, 0.0F, -3.0F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.2F, -0.5F));

		ModelPartData rearlegR = lower.addChild("rearlegR", ModelPartBuilder.create().uv(46, 62).mirrored().cuboid(-2.0F, -3.5F, -4.0F, 3.0F, 7.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.0F, -1.0F, 1.3333F, -0.0436F, 0.0873F, 0.0F));

		ModelPartData upperlegR = rearlegR.addChild("upperlegR", ModelPartBuilder.create().uv(89, 63).mirrored().cuboid(-1.5F, -0.5F, -3.0F, 3.0F, 9.0F, 6.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-0.5F, 2.0F, -1.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData lowerlegR = upperlegR.addChild("lowerlegR", ModelPartBuilder.create().uv(14, 54).mirrored().cuboid(-1.5F, -0.5F, -2.5F, 3.0F, 6.0F, 5.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(0.0F, 8.0F, -0.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData footR = lowerlegR.addChild("footR", ModelPartBuilder.create().uv(80, 93).mirrored().cuboid(-1.5F, 0.7F, -3.0F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 3.5F, -0.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData tail = lower.addChild("tail", ModelPartBuilder.create().uv(0, 67).cuboid(-5.0F, -4.0F, -1.0F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 7.3333F, -0.1745F, 0.0F, 0.0F));

		ModelPartData tail2 = tail.addChild("tail2", ModelPartBuilder.create().uv(18, 36).cuboid(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 0.0F, 5.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(100, 71).cuboid(-3.0F, -3.5F, -1.0F, 6.0F, 7.0F, 8.0F, new Dilation(-0.02F)), ModelTransform.of(0.0F, -0.5F, 7.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(52, 40).cuboid(-2.0F, -3.0F, -1.0F, 4.0F, 6.0F, 9.0F, new Dilation(-0.03F)), ModelTransform.of(0.0F, -0.5F, 7.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 9.0F, new Dilation(-0.04F)), ModelTransform.of(0.0F, -0.5F, 8.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData upper = controller.addChild("upper", ModelPartBuilder.create().uv(0, 103).cuboid(-7.0F, -5.5F, -12.0F, 14.0F, 11.0F, 14.0F, new Dilation(-0.1F))
		.uv(80, 30).cuboid(-6.0F, -6.3F, -11.0F, 12.0F, 1.0F, 12.0F, new Dilation(-0.1F))
		.uv(80, 16).cuboid(-6.0F, 5.3F, -11.0F, 12.0F, 1.0F, 12.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 0.5F, -7.0F, -0.0436F, 0.0F, 0.0F));

		ModelPartData armL = upper.addChild("armL", ModelPartBuilder.create().uv(0, 46).cuboid(-1.5F, -3.0F, -2.5F, 3.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 1.0F, -8.5F, 0.0F, -0.0873F, 0.0F));

		ModelPartData upperarmL = armL.addChild("upperarmL", ModelPartBuilder.create().uv(61, 57).cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 1.5F, 0.5F, 0.3491F, 0.0F, 0.0F));

		ModelPartData lowerarmL = upperarmL.addChild("lowerarmL", ModelPartBuilder.create().uv(115, 107).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(-0.02F)), ModelTransform.of(0.0F, 4.5F, -0.5F, -0.6109F, 0.0F, 0.0F));

		ModelPartData handL = lowerarmL.addChild("handL", ModelPartBuilder.create().uv(47, 82).cuboid(-1.5F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.5F, 0.0F, 0.2618F, 0.0F, 0.0F));

		ModelPartData armR = upper.addChild("armR", ModelPartBuilder.create().uv(112, 59).mirrored().cuboid(-1.5F, -3.0F, -2.5F, 3.0F, 6.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.0F, 1.0F, -8.5F, 0.0F, 0.0873F, 0.0F));

		ModelPartData upperarmR = armR.addChild("upperarmR", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(0.0F, 1.5F, 0.5F, 0.3491F, 0.0F, 0.0F));

		ModelPartData lowerarmR = upperarmR.addChild("lowerarmR", ModelPartBuilder.create().uv(86, 81).mirrored().cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(0.0F, 4.5F, -0.5F, -0.6109F, 0.0F, 0.0F));

		ModelPartData handR = lowerarmR.addChild("handR", ModelPartBuilder.create().uv(0, 82).mirrored().cuboid(-1.5F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 5.5F, 0.0F, 0.2618F, 0.0F, 0.0F));

		ModelPartData neck = upper.addChild("neck", ModelPartBuilder.create().uv(56, 99).cuboid(-4.0F, -4.0F, -4.2F, 8.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -11.8F, 0.1309F, 0.0F, 0.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(18, 82).cuboid(-5.0F, -5.0475F, -8.0281F, 10.0F, 10.0F, 8.0F, new Dilation(0.0F))
		.uv(100, 87).cuboid(-3.0F, -4.0475F, -16.0281F, 6.0F, 7.0F, 8.0F, new Dilation(0.0F))
		.uv(1, 107).cuboid(-2.0F, -3.0475F, -17.0281F, 4.0F, 7.0F, 2.0F, new Dilation(0.0F))
		.uv(16, 86).cuboid(-1.5F, 3.9525F, -17.0281F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.8525F, -3.1719F, -0.0873F, 0.0F, 0.0F));

		ModelPartData HornL3_r1 = head.addChild("HornL3_r1", ModelPartBuilder.create().uv(9, 95).mirrored().cuboid(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.2F, -7.6475F, -8.8281F, -0.3531F, 0.0578F, -0.073F));

		ModelPartData HornL2_r1 = head.addChild("HornL2_r1", ModelPartBuilder.create().uv(34, 71).mirrored().cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(-3.5F, -5.8475F, -5.0281F, -0.4437F, 0.1382F, -0.1069F));

		ModelPartData HornL2_r2 = head.addChild("HornL2_r2", ModelPartBuilder.create().uv(9, 95).cuboid(-0.5F, -0.5F, -5.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.2F, -7.6475F, -8.8281F, -0.3531F, -0.0578F, 0.073F));

		ModelPartData HornL1_r1 = head.addChild("HornL1_r1", ModelPartBuilder.create().uv(34, 71).cuboid(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.2F)), ModelTransform.of(3.5F, -5.8475F, -5.0281F, -0.4437F, -0.1382F, 0.1069F));

		ModelPartData snouthorn_r1 = head.addChild("snouthorn_r1", ModelPartBuilder.create().uv(122, 72).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.8475F, -15.5281F, 0.2618F, 0.0F, 0.0F));

		ModelPartData crest3_r1 = head.addChild("crest3_r1", ModelPartBuilder.create().uv(58, 113).cuboid(-2.5F, -8.0F, -0.5F, 5.0F, 14.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-7.5F, -3.0475F, 0.4719F, -0.1314F, 0.0865F, -0.0114F));

		ModelPartData crest2_r1 = head.addChild("crest2_r1", ModelPartBuilder.create().uv(43, 101).cuboid(-2.5F, -8.0F, -0.5F, 5.0F, 14.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(7.5F, -3.0475F, 0.4719F, -0.1314F, -0.0865F, 0.0114F));

		ModelPartData crestspikeR1_r1 = head.addChild("crestspikeR1_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false)
		.uv(24, 13).cuboid(2.3F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-1.4F, -14.1432F, 1.7371F, -0.1309F, 0.0F, 0.0F));

		ModelPartData crestspikeR4_r1 = head.addChild("crestspikeR4_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-8.4F, -10.2432F, 1.5371F, -0.1314F, 0.0865F, -0.0114F));

		ModelPartData crestspikeR2_r1 = head.addChild("crestspikeR2_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false)
		.uv(24, 13).cuboid(6.3F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(-3.4F, -13.6432F, 1.6371F, -0.1309F, 0.0F, 0.0F));

		ModelPartData crestspikeR3_r1 = head.addChild("crestspikeR3_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-6.4F, -10.7432F, 1.3371F, -0.1314F, 0.0865F, -0.0114F));

		ModelPartData crestspikeR5_r1 = head.addChild("crestspikeR5_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-9.4F, -9.2432F, 1.4371F, -0.0873F, -0.1309F, -1.5708F));

		ModelPartData crestspikeR6_r1 = head.addChild("crestspikeR6_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-9.9F, -7.2432F, 1.2371F, -0.0873F, -0.1309F, -1.5708F));

		ModelPartData crestspikeR7_r1 = head.addChild("crestspikeR7_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-10.4F, -5.2432F, 0.9371F, -0.0873F, -0.1309F, -1.5708F));

		ModelPartData crestspikeR8_r1 = head.addChild("crestspikeR8_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-10.4F, -3.2432F, 0.7371F, -0.0873F, -0.1309F, -1.5708F));

		ModelPartData crestspikeR9_r1 = head.addChild("crestspikeR9_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-9.9F, -0.9432F, 0.3371F, -0.0873F, -0.1309F, -1.5708F));

		ModelPartData crestspikeR10_r1 = head.addChild("crestspikeR10_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-9.4F, 1.2568F, 0.0371F, -0.0873F, -0.1309F, -1.5708F));

		ModelPartData crestspikeR12_r1 = head.addChild("crestspikeR12_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-6.4F, 2.2568F, -0.3629F, -0.1314F, 0.0865F, -0.0114F));

		ModelPartData crestspikeR11_r1 = head.addChild("crestspikeR11_r1", ModelPartBuilder.create().uv(24, 13).mirrored().cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)).mirrored(false), ModelTransform.of(-8.4F, 2.2568F, -0.1629F, -0.1314F, 0.0865F, -0.0114F));

		ModelPartData crestspikeL12_r1 = head.addChild("crestspikeL12_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(6.4F, 2.2568F, -0.3629F, -0.1314F, -0.0865F, 0.0114F));

		ModelPartData crestspikeL11_r1 = head.addChild("crestspikeL11_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(8.4F, 2.2568F, -0.1629F, -0.1314F, -0.0865F, 0.0114F));

		ModelPartData crestspikeL10_r1 = head.addChild("crestspikeL10_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(9.4F, 1.2568F, 0.0371F, -0.0873F, 0.1309F, 1.5708F));

		ModelPartData crestspikeL9_r1 = head.addChild("crestspikeL9_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(9.9F, -0.9432F, 0.3371F, -0.0873F, 0.1309F, 1.5708F));

		ModelPartData crestspikeL8_r1 = head.addChild("crestspikeL8_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(10.4F, -3.2432F, 0.7371F, -0.0873F, 0.1309F, 1.5708F));

		ModelPartData crestspikeL7_r1 = head.addChild("crestspikeL7_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(10.4F, -5.2432F, 0.9371F, -0.0873F, 0.1309F, 1.5708F));

		ModelPartData crestspikeL6_r1 = head.addChild("crestspikeL6_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(9.9F, -7.2432F, 1.2371F, -0.0873F, 0.1309F, 1.5708F));

		ModelPartData crestspikeL5_r1 = head.addChild("crestspikeL5_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(9.4F, -9.2432F, 1.4371F, -0.0873F, 0.1309F, 1.5708F));

		ModelPartData crestspikeL4_r1 = head.addChild("crestspikeL4_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(8.4F, -10.2432F, 1.5371F, -0.1314F, -0.0865F, 0.0114F));

		ModelPartData crestspikeL3_r1 = head.addChild("crestspikeL3_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(6.4F, -10.7432F, 1.3371F, -0.1314F, -0.0865F, 0.0114F));

		ModelPartData crest1_r1 = head.addChild("crest1_r1", ModelPartBuilder.create().uv(62, 79).cuboid(-5.0F, -10.0F, -0.5F, 10.0F, 17.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0475F, 0.3719F, -0.1309F, 0.0F, 0.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(59, 19).cuboid(-2.0F, -0.1F, -8.0F, 4.0F, 2.0F, 11.0F, new Dilation(-0.1F))
		.uv(2, 60).cuboid(-1.5F, -1.2F, -8.2F, 3.0F, 3.0F, 2.0F, new Dilation(-0.1F))
		.uv(121, 92).cuboid(-1.0F, -2.0F, -8.2F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 2.9525F, -8.0281F, 0.0873F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(TrikeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(TrikeAnimations.TRIKE_WALKING, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, TrikeAnimations.TRIKE_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, TrikeAnimations.TRIKE_ATTACK, ageInTicks, 1f);
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
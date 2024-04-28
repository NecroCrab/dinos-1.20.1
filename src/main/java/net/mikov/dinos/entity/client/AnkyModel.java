// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.AnkyAnimations;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class AnkyModel<T extends AnkyEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public AnkyModel(ModelPart root) {

		this.controller = root.getChild("controller");
		this.head = controller.getChild( "mainbody").getChild( "upper").getChild( "neck").getChild( "head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create(), ModelTransform.of(0.0F, 10.0F, 10.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData mainbody = controller.addChild("mainbody", ModelPartBuilder.create().uv(0, 223).cuboid(-9.0F, -8.0F, -20.0F, 18.0F, 12.0F, 21.0F, new Dilation(0.0F))
		.uv(153, 236).cuboid(-7.0F, -10.0F, -17.0F, 14.0F, 2.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bodyspike3R_r1 = mainbody.addChild("bodyspike3R_r1", ModelPartBuilder.create().uv(212, 201).mirrored().cuboid(-5.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.0F, -6.0F, -3.0F, 0.0F, 0.0873F, 0.0F));

		ModelPartData bodyspike2R_r1 = mainbody.addChild("bodyspike2R_r1", ModelPartBuilder.create().uv(210, 201).mirrored().cuboid(-6.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.0F, -5.0F, -17.0F, 0.0F, -0.0873F, 0.0F));

		ModelPartData bodyspike1R_r1 = mainbody.addChild("bodyspike1R_r1", ModelPartBuilder.create().uv(212, 201).mirrored().cuboid(-5.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.0F, -6.0F, -13.0F, 0.0F, -0.0873F, 0.0F));

		ModelPartData bodyspike3L_r1 = mainbody.addChild("bodyspike3L_r1", ModelPartBuilder.create().uv(212, 201).cuboid(-1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -6.0F, -3.0F, 0.0F, -0.0873F, 0.0F));

		ModelPartData bodyspike2L_r1 = mainbody.addChild("bodyspike2L_r1", ModelPartBuilder.create().uv(210, 201).cuboid(-1.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -5.0F, -17.0F, 0.0F, 0.0873F, 0.0F));

		ModelPartData bodyspike1L_r1 = mainbody.addChild("bodyspike1L_r1", ModelPartBuilder.create().uv(212, 201).cuboid(-1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -6.0F, -13.0F, 0.0F, 0.0873F, 0.0F));

		ModelPartData lower = mainbody.addChild("lower", ModelPartBuilder.create().uv(100, 236).cuboid(-8.0F, -6.0F, 0.0F, 16.0F, 11.0F, 9.0F, new Dilation(0.0F))
		.uv(94, 194).cuboid(-7.0F, -8.0F, -1.0F, 14.0F, 2.0F, 8.0F, new Dilation(-0.01F)), ModelTransform.pivot(0.0F, -2.0F, 1.0F));

		ModelPartData tail = lower.addChild("tail", ModelPartBuilder.create().uv(0, 202).cuboid(-4.0F, -3.0F, -1.0F, 8.0F, 7.0F, 12.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, -2.0F, 9.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData tailspikeR_r1 = tail.addChild("tailspikeR_r1", ModelPartBuilder.create().uv(214, 201).mirrored().cuboid(-4.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-4.0F, 0.0F, 8.0F, 0.0F, 0.0873F, 0.0F));

		ModelPartData tailspikeL_r1 = tail.addChild("tailspikeL_r1", ModelPartBuilder.create().uv(214, 201).cuboid(-1.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 8.0F, 0.0F, -0.0873F, 0.0F));

		ModelPartData tail2 = tail.addChild("tail2", ModelPartBuilder.create().uv(220, 238).cuboid(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 12.0F, new Dilation(-0.015F)), ModelTransform.of(0.0F, 0.0F, 10.0F, 0.1752F, -0.0859F, -0.0152F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(91, 216).cuboid(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 12.0F, new Dilation(-0.02F)), ModelTransform.of(0.0F, -0.5F, 12.0F, 0.1752F, -0.0859F, -0.0152F));

		ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(60, 218).cuboid(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 19.0F, new Dilation(-0.025F))
		.uv(200, 235).cuboid(-4.0F, -3.0F, 10.0F, 8.0F, 6.0F, 7.0F, new Dilation(-0.025F))
		.uv(214, 201).cuboid(1.0F, -1.0F, 13.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(214, 201).mirrored().cuboid(-6.0F, -1.0F, 13.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -0.5F, 11.0F, 0.1752F, -0.0859F, -0.0152F));

		ModelPartData rearlegL = lower.addChild("rearlegL", ModelPartBuilder.create().uv(1, 182).cuboid(-1.0F, -4.0F, -4.0F, 4.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, -1.0F, 3.0F, -0.0003F, -0.0869F, 0.0076F));

		ModelPartData hipspikeL_r1 = rearlegL.addChild("hipspikeL_r1", ModelPartBuilder.create().uv(214, 201).cuboid(-1.0F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -2.0F, 0.0F, 0.0F, -0.0873F, 0.0F));

		ModelPartData rearupperL = rearlegL.addChild("rearupperL", ModelPartBuilder.create().uv(1, 226).cuboid(-3.0F, -1.0F, -2.5F, 4.0F, 10.0F, 5.0F, new Dilation(-0.01F)), ModelTransform.of(2.0F, 1.0F, -0.5F, -0.48F, 0.0F, 0.0F));

		ModelPartData rearlowerL = rearupperL.addChild("rearlowerL", ModelPartBuilder.create().uv(32, 199).cuboid(-2.0F, -1.0F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(-0.015F)), ModelTransform.of(0.0F, 9.0F, -0.5F, 0.3927F, 0.0F, 0.0F));

		ModelPartData rearfootL = rearlowerL.addChild("rearfootL", ModelPartBuilder.create().uv(238, 196).cuboid(-1.5F, 0.0F, -4.0F, 3.0F, 2.0F, 6.0F, new Dilation(-0.01F)), ModelTransform.of(-0.5F, 5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData reartoesL = rearfootL.addChild("reartoesL", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(0.0F, 1.0F, -4.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData reartoe4L_r1 = reartoesL.addChild("reartoe4L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(-2.0F, 0.0F, 1.5F, 0.0F, 0.6981F, 0.0F));

		ModelPartData reartoe3L_r1 = reartoesL.addChild("reartoe3L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(1.0F, 0.0F, -0.5F, 0.0F, -0.2618F, 0.0F));

		ModelPartData reartoe1L_r1 = reartoesL.addChild("reartoe1L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(-1.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		ModelPartData rearlegR = lower.addChild("rearlegR", ModelPartBuilder.create().uv(114, 209).mirrored().cuboid(-3.0F, -3.0F, -4.0F, 4.0F, 8.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.0F, -2.0F, 3.0F, 0.1752F, 0.0859F, 0.0152F));

		ModelPartData hipspikeR_r1 = rearlegR.addChild("hipspikeR_r1", ModelPartBuilder.create().uv(214, 201).mirrored().cuboid(-4.0F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.0F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.0F));

		ModelPartData rearupperR = rearlegR.addChild("rearupperR", ModelPartBuilder.create().uv(152, 237).mirrored().cuboid(-2.0F, -1.0F, -2.5F, 4.0F, 10.0F, 5.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-1.0F, 2.0F, -0.5F, -0.1745F, 0.0F, 0.0F));

		ModelPartData rearlowerR = rearupperR.addChild("rearlowerR", ModelPartBuilder.create().uv(140, 222).mirrored().cuboid(-1.0F, -1.0F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(-0.015F)).mirrored(false), ModelTransform.of(-1.0F, 9.0F, -0.5F, 0.4363F, 0.0F, 0.0F));

		ModelPartData rearfootR = rearlowerR.addChild("rearfootR", ModelPartBuilder.create().uv(238, 196).mirrored().cuboid(-1.5F, 0.0F, -4.0F, 3.0F, 2.0F, 6.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(0.5F, 5.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		ModelPartData reartoesR = rearfootR.addChild("reartoesR", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(0.0F, 1.0F, -4.0F, -0.2618F, 0.0F, 0.0F));

		ModelPartData reartoe4R_r1 = reartoesR.addChild("reartoe4R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(2.0F, 0.0F, 1.5F, 0.0F, -0.6981F, 0.0F));

		ModelPartData reartoe3R_r1 = reartoesR.addChild("reartoe3R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(-1.0F, 0.0F, -0.5F, 0.0F, 0.2618F, 0.0F));

		ModelPartData reartoe1R_r1 = reartoesR.addChild("reartoe1R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(1.0F, 0.0F, -0.5F, 0.0F, -0.2618F, 0.0F));

		ModelPartData upper = mainbody.addChild("upper", ModelPartBuilder.create().uv(157, 218).cuboid(-7.0F, -5.0F, -4.0F, 14.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -20.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData neck = upper.addChild("neck", ModelPartBuilder.create().uv(197, 216).cuboid(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(41, 205).cuboid(-5.0F, -4.0F, -8.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.05F))
		.uv(135, 201).cuboid(-3.0F, -3.0F, -14.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -5.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData headspike2L_r1 = head.addChild("headspike2L_r1", ModelPartBuilder.create().uv(212, 201).mirrored().cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, 3.0F, -7.0F, 0.0F, -0.1745F, 0.0F));

		ModelPartData headspike1L_r1 = head.addChild("headspike1L_r1", ModelPartBuilder.create().uv(212, 201).mirrored().cuboid(-4.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-5.0F, -3.0F, -1.0F, 0.0F, -0.1745F, 0.0F));

		ModelPartData headspike2L_r2 = head.addChild("headspike2L_r2", ModelPartBuilder.create().uv(212, 201).cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 3.0F, -7.0F, 0.0F, 0.1745F, 0.0F));

		ModelPartData headspike1L_r2 = head.addChild("headspike1L_r2", ModelPartBuilder.create().uv(212, 201).cuboid(-2.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -3.0F, -1.0F, 0.0F, 0.1745F, 0.0F));

		ModelPartData mouth = head.addChild("mouth", ModelPartBuilder.create().uv(71, 204).cuboid(-3.0F, 0.0F, -5.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -8.0F, 0.2618F, 0.0F, 0.0F));

		ModelPartData armL = upper.addChild("armL", ModelPartBuilder.create().uv(237, 221).cuboid(-1.5F, -3.0F, -2.0F, 3.0F, 6.0F, 5.0F, new Dilation(-0.01F)), ModelTransform.of(7.5F, -1.0F, 1.0F, -0.0873F, -0.0873F, 0.0F));

		ModelPartData upperarmL = armL.addChild("upperarmL", ModelPartBuilder.create().uv(88, 214).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(-0.015F)), ModelTransform.of(0.0F, 3.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData lowerarmL = upperarmL.addChild("lowerarmL", ModelPartBuilder.create().uv(0, 201).cuboid(-1.0F, -1.0F, -1.5F, 2.0F, 7.0F, 3.0F, new Dilation(-0.02F)), ModelTransform.of(0.5F, 6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData forefootL = lowerarmL.addChild("forefootL", ModelPartBuilder.create().uv(241, 208).cuboid(-1.0F, 0.0F, -2.5F, 2.0F, 2.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, 5.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData foretoesL = forefootL.addChild("foretoesL", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, -1.5F));

		ModelPartData foretoe4L_r1 = foretoesL.addChild("foretoe4L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(1.5F, 0.0F, -0.5F, 0.0F, -0.6109F, 0.0F));

		ModelPartData foretoe3L_r1 = foretoesL.addChild("foretoe3L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(0.5F, 0.0F, -1.5F, 0.0F, -0.1745F, 0.0F));

		ModelPartData foretoe2L_r1 = foretoesL.addChild("foretoe2L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(-0.5F, 0.0F, -1.5F, 0.0F, 0.1745F, 0.0F));

		ModelPartData foretoe1L_r1 = foretoesL.addChild("foretoe1L_r1", ModelPartBuilder.create().uv(194, 201).cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)), ModelTransform.of(-1.5F, 0.0F, -0.5F, 0.0F, 0.6109F, 0.0F));

		ModelPartData armR = upper.addChild("armR", ModelPartBuilder.create().uv(60, 223).mirrored().cuboid(-1.5F, -3.0F, -2.0F, 3.0F, 6.0F, 5.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-7.5F, -1.0F, 1.0F, -0.0873F, 0.0873F, 0.0F));

		ModelPartData upperarmR = armR.addChild("upperarmR", ModelPartBuilder.create().uv(83, 245).mirrored().cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(-0.015F)).mirrored(false), ModelTransform.of(0.0F, 3.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData lowerarmR = upperarmR.addChild("lowerarmR", ModelPartBuilder.create().uv(246, 238).mirrored().cuboid(-1.0F, -1.0F, -1.5F, 2.0F, 7.0F, 3.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(-0.5F, 6.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		ModelPartData forefootR = lowerarmR.addChild("forefootR", ModelPartBuilder.create().uv(241, 208).mirrored().cuboid(-1.0F, 0.0F, -2.5F, 2.0F, 2.0F, 4.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(0.0F, 5.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData foretoesR = forefootR.addChild("foretoesR", ModelPartBuilder.create(), ModelTransform.of(0.0F, 1.0F, -1.5F, -0.1309F, 0.0F, 0.0F));

		ModelPartData foretoe4R_r1 = foretoesR.addChild("foretoe4R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(-1.5F, 0.0F, -0.5F, 0.0F, 0.6981F, 0.0F));

		ModelPartData foretoe3R_r1 = foretoesR.addChild("foretoe3R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(-0.5F, 0.0F, -1.5F, 0.0F, 0.1745F, 0.0F));

		ModelPartData foretoe2R_r1 = foretoesR.addChild("foretoe2R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(0.5F, 0.0F, -1.5F, 0.0F, -0.1745F, 0.0F));

		ModelPartData foretoe1R_r1 = foretoesR.addChild("foretoe1R_r1", ModelPartBuilder.create().uv(194, 201).mirrored().cuboid(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(-0.04F)).mirrored(false), ModelTransform.of(1.5F, 0.0F, -0.5F, 0.0F, -0.7854F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void setAngles(AnkyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(AnkyAnimations.ANKY_WALK, limbSwing, limbSwingAmount, 2f, 15.5f);
		this.updateAnimation(entity.idleAnimationState, AnkyAnimations.ANKY_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.sittingAnimationState, AnkyAnimations.ANKY_SIT, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, AnkyAnimations.ANKY_ATTACK, ageInTicks, 1f);
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
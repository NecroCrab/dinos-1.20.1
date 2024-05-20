// Made with Blockbench 4.10.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.mikov.dinos.entity.client;

import net.mikov.dinos.entity.animation.AnkyAnimations;
import net.mikov.dinos.entity.animation.MegalaniaAnimations;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.mikov.dinos.entity.custom.MegalaniaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class MegalaniaModel<T extends MegalaniaEntity> extends SinglePartEntityModel<T> {

	private final ModelPart controller;
	private final ModelPart head;

	public MegalaniaModel(ModelPart root) {

		this.controller = root.getChild("controller");
		this.head = controller.getChild("upper").getChild("neck").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData controller = modelPartData.addChild("controller", ModelPartBuilder.create().uv(0, 89).cuboid(-9.0F, -6.0F, -13.5F, 18.0F, 12.0F, 27.0F, new Dilation(0.0F))
		.uv(25, 90).cuboid(-7.5F, -6.75F, -11.0F, 15.0F, 3.0F, 22.0F, new Dilation(0.0F))
		.uv(1, 43).cuboid(-7.5F, 4.5F, -9.0F, 15.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, 3.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData upper = controller.addChild("upper", ModelPartBuilder.create().uv(70, 57).cuboid(-8.0F, -5.0F, -10.5F, 17.0F, 11.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-0.45F, -0.75F, -13.5F, -0.0436F, 0.0F, 0.0F));

		ModelPartData armL = upper.addChild("armL", ModelPartBuilder.create().uv(50, 48).cuboid(-1.5F, -3.0F, -3.0F, 3.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, 2.25F, -6.0F, 0.016F, 0.0596F, -0.5222F));

		ModelPartData upperarmL = armL.addChild("upperarmL", ModelPartBuilder.create().uv(33, 20).cuboid(-0.255F, -0.005F, -2.245F, 1.0F, 9.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.of(0.75F, 0.0F, -0.75F, -0.091F, 0.0834F, -0.0436F));

		ModelPartData lowerarmL = upperarmL.addChild("lowerarmL", ModelPartBuilder.create().uv(45, 24).cuboid(-0.26F, -1.01F, -1.49F, 1.0F, 7.0F, 3.0F, new Dilation(-0.02F)), ModelTransform.of(0.0F, 9.0F, -0.75F, -0.2266F, 0.0331F, -0.22F));

		ModelPartData handL = lowerarmL.addChild("handL", ModelPartBuilder.create().uv(39, 0).cuboid(-0.25F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.5F, 0.0F, -0.0456F, 0.1685F, -0.2657F));

		ModelPartData talonsL = handL.addChild("talonsL", ModelPartBuilder.create(), ModelTransform.of(-0.375F, 1.975F, 0.0F, 0.0F, 0.0F, -0.48F));

		ModelPartData talonailL4_r1 = talonsL.addChild("talonailL4_r1", ModelPartBuilder.create().uv(0, 89).cuboid(-0.3284F, 0.6166F, -0.7477F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F))
		.uv(0, 99).cuboid(-0.2F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)), ModelTransform.of(0.0F, 0.225F, -1.5F, -0.7854F, 0.0F, 0.0F));

		ModelPartData talonailL3_r1 = talonsL.addChild("talonailL3_r1", ModelPartBuilder.create().uv(0, 89).cuboid(-0.3237F, 0.6161F, -0.7427F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F))
		.uv(0, 99).cuboid(-0.2F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)), ModelTransform.of(0.0F, 0.225F, 1.5F, 0.7854F, 0.0F, 0.0F));

		ModelPartData talonailL2_r1 = talonsL.addChild("talonailL2_r1", ModelPartBuilder.create().uv(0, 89).cuboid(-0.3237F, 0.6712F, -0.7464F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F))
		.uv(0, 99).cuboid(-0.2F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)), ModelTransform.of(0.0F, 1.275F, 0.75F, 0.2182F, 0.0F, 0.0F));

		ModelPartData talonailL1_r1 = talonsL.addChild("talonailL1_r1", ModelPartBuilder.create().uv(0, 89).cuboid(-0.3237F, 0.71F, -0.7284F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F))
		.uv(0, 99).cuboid(-0.2F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)), ModelTransform.of(0.0F, 1.275F, -0.75F, -0.2182F, 0.0F, 0.0F));

		ModelPartData armR = upper.addChild("armR", ModelPartBuilder.create().uv(0, 75).mirrored().cuboid(-1.5F, -3.0F, -3.0F, 3.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.5F, 2.25F, -6.0F, 0.0873F, 0.0F, 0.5236F));

		ModelPartData upperarmR = armR.addChild("upperarmR", ModelPartBuilder.create().uv(27, 0).mirrored().cuboid(-0.255F, -0.005F, -2.245F, 1.0F, 9.0F, 4.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-0.75F, 0.0F, -0.75F, 0.5672F, 0.0F, 0.0873F));

		ModelPartData lowerarmR = upperarmR.addChild("lowerarmR", ModelPartBuilder.create().uv(82, 6).mirrored().cuboid(-0.26F, -1.01F, -1.49F, 1.0F, 7.0F, 3.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(0.0F, 9.0F, -0.75F, -0.6981F, -0.0873F, 0.0F));

		ModelPartData handR = lowerarmR.addChild("handR", ModelPartBuilder.create().uv(41, 17).mirrored().cuboid(-0.25F, 0.0F, -1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 4.5F, 0.0F, -0.1312F, -0.0869F, 0.4041F));

		ModelPartData talonsR = handR.addChild("talonsR", ModelPartBuilder.create(), ModelTransform.of(0.775F, 1.575F, 0.0F, 0.0F, 0.0F, 0.5672F));

		ModelPartData talonailR4_r1 = talonsR.addChild("talonailR4_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.2702F, 0.5986F, -0.7356F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F)).mirrored(false)
		.uv(0, 99).mirrored().cuboid(-0.35F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(0.0F, 0.225F, -1.5F, -0.7854F, 0.0F, 0.0F));

		ModelPartData talonailR3_r1 = talonsR.addChild("talonailR3_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.2763F, 0.5985F, -0.7357F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F)).mirrored(false)
		.uv(0, 99).mirrored().cuboid(-0.35F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(0.0F, 0.225F, 1.5F, 0.7854F, 0.0F, 0.0F));

		ModelPartData talonailR2_r1 = talonsR.addChild("talonailR2_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.2763F, 0.6526F, -0.75F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F)).mirrored(false)
		.uv(0, 99).mirrored().cuboid(-0.35F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(0.0F, 1.275F, 0.75F, 0.2182F, 0.0F, 0.0F));

		ModelPartData talonailR1_r1 = talonsR.addChild("talonailR1_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.2763F, 0.6946F, -0.7395F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F)).mirrored(false)
		.uv(0, 99).mirrored().cuboid(-0.35F, -0.775F, -0.725F, 1.0F, 3.0F, 1.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(0.0F, 1.275F, -0.75F, -0.2182F, 0.0F, 0.0F));

		ModelPartData neck = upper.addChild("neck", ModelPartBuilder.create().uv(50, 29).cuboid(-5.55F, -4.05F, -7.45F, 13.0F, 10.0F, 9.0F, new Dilation(-0.1F)), ModelTransform.of(-0.5F, 0.0F, -10.5F, 0.1309F, 0.0F, 0.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(90, 112).cuboid(-5.0F, -3.5F, -6.0F, 11.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(90, 22).cuboid(-3.55F, -1.95F, -13.35F, 8.0F, 6.0F, 8.0F, new Dilation(0.3F)), ModelTransform.of(0.4F, -0.65F, -7.5F, -0.0873F, 0.0F, 0.0F));

		ModelPartData tooth24_r1 = head.addChild("tooth24_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(-3.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(2.05F, 4.5F, -13.05F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tooth23_r1 = head.addChild("tooth23_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(-6.0F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(3.25F, 4.5F, -12.45F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tooth22_r1 = head.addChild("tooth22_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(-7.2F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(3.85F, 4.65F, -11.1F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tooth21_r1 = head.addChild("tooth21_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(-7.8F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(4.15F, 4.8F, -9.6F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tooth20_r1 = head.addChild("tooth20_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(-7.8F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(4.15F, 4.5F, -8.1F, 0.1745F, 0.0F, 0.0F));

		ModelPartData tooth19_r1 = head.addChild("tooth19_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.6F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(-7.8F, -1.65F, -0.6F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(4.15F, 4.2F, -6.6F, 0.1745F, 0.0F, 0.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create(), ModelTransform.of(0.75F, 5.2395F, -1.0949F, 0.3927F, 0.0F, 0.0F));

		ModelPartData tooth12_r1 = jaw.addChild("tooth12_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-1.5F, -0.6056F, -12.3249F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).mirrored().cuboid(-4.05F, -1.7174F, -9.4747F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).mirrored().cuboid(-4.05F, -1.6824F, -7.9454F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).mirrored().cuboid(-4.05F, -1.6475F, -6.4161F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(2.85F, -1.6475F, -6.4161F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F))
		.uv(0, 89).cuboid(2.85F, -1.6824F, -7.9454F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F))
		.uv(0, 89).cuboid(2.85F, -1.7174F, -9.4747F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F))
		.uv(0, 89).cuboid(0.3F, -0.6056F, -12.3249F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(-0.2F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData tooth11_r1 = jaw.addChild("tooth11_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-2.85F, -0.901F, -12.377F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false)
		.uv(0, 89).cuboid(1.45F, -0.901F, -12.377F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(-0.1F, 0.3F, 0.3F, -0.5236F, 0.0F, 0.0F));

		ModelPartData tooth10_r1 = jaw.addChild("tooth10_r1", ModelPartBuilder.create().uv(0, 89).mirrored().cuboid(-0.7495F, -1.448F, -0.588F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)).mirrored(false), ModelTransform.of(-3.2F, -5.0087F, -9.0615F, -0.4912F, -0.3764F, 0.349F));

		ModelPartData tooth3_r1 = jaw.addChild("tooth3_r1", ModelPartBuilder.create().uv(0, 89).cuboid(-0.4001F, -1.5034F, -0.7744F, 1.0F, 3.0F, 1.0F, new Dilation(-0.3F)), ModelTransform.of(2.8F, -5.0087F, -9.0615F, -0.4912F, 0.3764F, -0.349F));

		ModelPartData mouth_r1 = jaw.addChild("mouth_r1", ModelPartBuilder.create().uv(82, 38).cuboid(-3.575F, -1.875F, -12.125F, 8.0F, 2.0F, 15.0F, new Dilation(0.25F)), ModelTransform.of(-0.75F, 0.7048F, -0.2565F, -0.3491F, 0.0F, 0.0F));

		ModelPartData tongue = jaw.addChild("tongue", ModelPartBuilder.create(), ModelTransform.of(-0.1F, -1.5241F, -0.1099F, -0.3491F, 0.0F, 0.0F));

		ModelPartData tongue_r1 = tongue.addChild("tongue_r1", ModelPartBuilder.create().uv(92, 0).cuboid(-0.625F, -7.9059F, -0.6144F, 1.0F, 9.0F, 1.0F, new Dilation(-0.35F)), ModelTransform.of(0.0F, -0.0605F, 0.0578F, 1.5708F, 0.0F, 0.0F));

		ModelPartData tonguetip = tongue.addChild("tonguetip", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0656F, -7.3092F, 0.2182F, 0.0F, 0.0F));

		ModelPartData tonguetipR_r1 = tonguetip.addChild("tonguetipR_r1", ModelPartBuilder.create().uv(21, 98).mirrored().cuboid(-0.6725F, -2.6715F, -0.6185F, 1.0F, 3.0F, 1.0F, new Dilation(-0.35F)).mirrored(false), ModelTransform.of(0.0F, -0.1186F, -0.1017F, 1.0518F, 0.7268F, -0.3295F));

		ModelPartData tonguetipL_r1 = tonguetip.addChild("tonguetipL_r1", ModelPartBuilder.create().uv(21, 98).cuboid(-0.4604F, -2.389F, -0.6062F, 1.0F, 3.0F, 1.0F, new Dilation(-0.35F)), ModelTransform.of(0.0F, -0.1186F, -0.1017F, 1.0518F, -0.7268F, 0.3295F));

		ModelPartData lower = controller.addChild("lower", ModelPartBuilder.create().uv(70, 86).cuboid(-8.0F, -5.0F, -1.5F, 17.0F, 11.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-0.55F, -0.75F, 13.5F, 0.0873F, 0.0F, 0.0F));

		ModelPartData legL = lower.addChild("legL", ModelPartBuilder.create().uv(41, 0).cuboid(-2.75F, -4.25F, -3.75F, 5.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(9.75F, 0.75F, 5.25F, -0.2631F, -0.1901F, -0.1069F));

		ModelPartData upperlegL = legL.addChild("upperlegL", ModelPartBuilder.create().uv(110, 0).cuboid(-1.505F, -0.255F, -2.995F, 3.0F, 10.0F, 6.0F, new Dilation(-0.01F)), ModelTransform.of(0.75F, 0.0F, -0.75F, 0.48F, 0.0F, 0.0F));

		ModelPartData lowerlegL = upperlegL.addChild("lowerlegL", ModelPartBuilder.create().uv(0, 0).cuboid(-1.51F, -0.26F, -2.24F, 3.0F, 7.0F, 4.0F, new Dilation(-0.02F)), ModelTransform.of(0.0F, 9.0F, -0.75F, 0.5236F, -0.0873F, 0.0F));

		ModelPartData footL = lowerlegL.addChild("footL", ModelPartBuilder.create().uv(0, 88).cuboid(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.15F, -1.45F, -0.5692F, 0.218F, -0.0444F));

		ModelPartData clawsL = footL.addChild("clawsL", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.15F, -1.125F, -0.2618F, 0.0F, 0.0F));

		ModelPartData clawnailL4_r1 = clawsL.addChild("clawnailL4_r1", ModelPartBuilder.create().uv(120, 35).cuboid(-0.325F, -1.575F, -4.05F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F))
		.uv(115, 43).cuboid(-0.275F, -1.525F, -2.6F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)), ModelTransform.of(-2.25F, 0.0F, -0.75F, 0.0F, 0.7854F, 0.0F));

		ModelPartData clawnailL3_r1 = clawsL.addChild("clawnailL3_r1", ModelPartBuilder.create().uv(120, 35).cuboid(-0.325F, -1.575F, -4.05F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F))
		.uv(115, 43).cuboid(-0.275F, -1.525F, -2.6F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)), ModelTransform.of(2.25F, 0.0F, -0.75F, 0.0F, -0.7854F, 0.0F));

		ModelPartData clawnailL2_r1 = clawsL.addChild("clawnailL2_r1", ModelPartBuilder.create().uv(120, 35).cuboid(-0.325F, -1.575F, -4.8F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F))
		.uv(115, 43).cuboid(-0.275F, -1.525F, -3.35F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)), ModelTransform.of(-0.75F, 0.0F, -1.5F, 0.0F, 0.2618F, 0.0F));

		ModelPartData clawnailL1_r1 = clawsL.addChild("clawnailL1_r1", ModelPartBuilder.create().uv(120, 35).cuboid(-0.325F, -1.575F, -4.8F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F))
		.uv(115, 43).cuboid(-0.275F, -1.525F, -3.35F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)), ModelTransform.of(0.75F, 0.0F, -1.5F, 0.0F, -0.3054F, 0.0F));

		ModelPartData legR = lower.addChild("legR", ModelPartBuilder.create().uv(0, 98).mirrored().cuboid(-2.75F, -4.25F, -3.75F, 5.0F, 8.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.25F, 0.75F, 5.25F, -0.1309F, 0.1745F, 0.0F));

		ModelPartData upperlegR = legR.addChild("upperlegR", ModelPartBuilder.create().uv(0, 44).mirrored().cuboid(-1.505F, -0.255F, -2.995F, 3.0F, 10.0F, 6.0F, new Dilation(-0.01F)).mirrored(false), ModelTransform.of(-0.75F, 0.0F, -0.75F, -0.5672F, 0.0F, 0.0F));

		ModelPartData lowerlegR = upperlegR.addChild("lowerlegR", ModelPartBuilder.create().uv(0, 23).mirrored().cuboid(-1.51F, -0.26F, -2.24F, 3.0F, 7.0F, 4.0F, new Dilation(-0.02F)).mirrored(false), ModelTransform.of(0.0F, 9.0F, -0.75F, 0.5672F, 0.0F, 0.0F));

		ModelPartData footR = lowerlegR.addChild("footR", ModelPartBuilder.create().uv(0, 65).mirrored().cuboid(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 6.75F, -1.25F));

		ModelPartData clawsR = footR.addChild("clawsR", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.15F, -1.125F));

		ModelPartData clawnailR4_r1 = clawsR.addChild("clawnailR4_r1", ModelPartBuilder.create().uv(120, 35).mirrored().cuboid(-0.325F, -1.575F, -4.05F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F)).mirrored(false)
		.uv(115, 43).mirrored().cuboid(-0.275F, -1.525F, -2.6F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(2.25F, 0.0F, -0.75F, 0.0F, -0.7854F, 0.0F));

		ModelPartData clawnailR3_r1 = clawsR.addChild("clawnailR3_r1", ModelPartBuilder.create().uv(120, 35).mirrored().cuboid(-0.325F, -1.575F, -4.05F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F)).mirrored(false)
		.uv(115, 43).mirrored().cuboid(-0.275F, -1.525F, -2.6F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(-2.25F, 0.0F, -0.75F, 0.0F, 0.7854F, 0.0F));

		ModelPartData clawnailR2_r1 = clawsR.addChild("clawnailR2_r1", ModelPartBuilder.create().uv(120, 35).mirrored().cuboid(-0.325F, -1.575F, -4.8F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F)).mirrored(false)
		.uv(115, 43).mirrored().cuboid(-0.275F, -1.525F, -3.35F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(0.75F, 0.0F, -1.5F, 0.0F, -0.2618F, 0.0F));

		ModelPartData clawnailR1_r1 = clawsR.addChild("clawnailR1_r1", ModelPartBuilder.create().uv(120, 35).mirrored().cuboid(-0.325F, -1.575F, -4.8F, 1.0F, 3.0F, 3.0F, new Dilation(-0.15F)).mirrored(false)
		.uv(115, 43).mirrored().cuboid(-0.275F, -1.525F, -3.35F, 1.0F, 3.0F, 4.0F, new Dilation(-0.05F)).mirrored(false), ModelTransform.of(-0.75F, 0.0F, -1.5F, 0.0F, 0.3054F, 0.0F));

		ModelPartData tail = lower.addChild("tail", ModelPartBuilder.create().uv(19, 66).cuboid(-6.0F, -4.5F, -1.5F, 12.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.45F, 0.75F, 10.5F, -0.2182F, 0.0F, 0.0F));

		ModelPartData tail2 = tail.addChild("tail2", ModelPartBuilder.create().uv(0, 20).cuboid(-4.25F, -3.25F, -1.5F, 8.0F, 7.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 0.0F, 10.5F, -0.1745F, 0.0F, 0.0F));

		ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(54, 7).cuboid(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(-0.3F, 0.0F, 13.5F, -0.1745F, 0.0F, 0.0F));

		ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(0, 0).cuboid(-2.75F, -1.75F, -1.5F, 5.0F, 4.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, 0.0F, 13.5F, 0.2182F, 0.0F, 0.0F));

		ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create().uv(88, 2).cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(-0.3F, 0.0F, 13.5F, 0.1745F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(MegalaniaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		if (entity.isOnGround() && entity.getVelocity().length() >= 0.01) {
			this.animateMovement(MegalaniaAnimations.MEGALANIA_WALK, limbSwing, limbSwingAmount, 2f, 15.5f);
		} else if (entity.isTouchingWater()) {
			this.animateMovement(MegalaniaAnimations.MEGALANIA_SWIM, limbSwing, limbSwingAmount, 2f, 15.5f);
			//this.updateAnimation(entity.swimmingAnimationState, MegalaniaAnimations.MEGALANIA_SWIM, ageInTicks, 1f);
		} else if (entity.isOnGround() && entity.getVelocity().length() == 0.00) {
			this.updateAnimation(entity.idleAnimationState, MegalaniaAnimations.MEGALANIA_IDLE, ageInTicks, 1f);
		} else if (entity.isAttacking()) {
			this.updateAnimation(entity.attackingAnimationState, MegalaniaAnimations.MEGALANIA_ATTACK, ageInTicks, 1f);
		} else {
			this.updateAnimation(entity.sittingAnimationState, MegalaniaAnimations.MEGALANIA_SIT, ageInTicks, 1f);
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
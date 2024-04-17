package net.mikov.dinos.entity.client;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

import net.mikov.dinos.entity.animation.ModAnimations;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.MathHelper;

public class TrexModel<T extends TrexEntity> extends SinglePartEntityModel<T> {

	private final ModelPart Trex;
	private final ModelPart head;

	public TrexModel(ModelPart root) {
		this.Trex = root.getChild("Trex");
		this.head = Trex.getChild("mainbody").getChild("upperbody").getChild("neck").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Trex = modelPartData.addChild("Trex", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, -1.0F));

		ModelPartData mainbody = Trex.addChild("mainbody", ModelPartBuilder.create().uv(96, 136).cuboid(-6.0F, -18.0F, -31.5F, 12.0F, 3.0F, 39.0F, new Dilation(0.0F))
				.uv(81, 127).cuboid(-9.0F, -15.0F, -36.0F, 18.0F, 3.0F, 48.0F, new Dilation(0.0F))
				.uv(62, 122).cuboid(-12.0F, -12.0F, -36.0F, 24.0F, 6.0F, 51.0F, new Dilation(0.0F))
				.uv(100, 193).cuboid(-12.0F, -6.0F, -36.0F, 24.0F, 9.0F, 54.0F, new Dilation(0.0F))
				.uv(7, 180).cuboid(-9.0F, 3.0F, -36.0F, 18.0F, 9.0F, 51.0F, new Dilation(0.0F))
				.uv(49, 203).cuboid(-3.0F, 12.0F, -21.0F, 6.0F, 6.0F, 30.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -51.0F, 6.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData upperbody = mainbody.addChild("upperbody", ModelPartBuilder.create().uv(171, 67).cuboid(-6.0F, -12.0F, -18.0F, 12.0F, 3.0F, 30.0F, new Dilation(0.0F))
				.uv(172, 71).cuboid(-8.998F, -8.998F, -15.002F, 18.0F, 3.0F, 24.0F, new Dilation(0.001F))
				.uv(101, 0).cuboid(-11.998F, -5.998F, -15.002F, 24.0F, 6.0F, 24.0F, new Dilation(0.001F))
				.uv(101, 0).cuboid(-11.998F, 0.002F, -15.002F, 24.0F, 9.0F, 24.0F, new Dilation(0.001F))
				.uv(111, 94).cuboid(-8.998F, 9.002F, -12.002F, 18.0F, 6.0F, 21.0F, new Dilation(0.001F)), ModelTransform.of(0.0F, -6.0F, -39.0F, -0.3065F, -0.0832F, 0.0263F));

		ModelPartData neck = upperbody.addChild("neck", ModelPartBuilder.create().uv(100, 184).cuboid(-8.998F, -10.498F, -18.002F, 18.0F, 15.0F, 6.0F, new Dilation(0.001F)), ModelTransform.pivot(0.0F, 0.0F, -3.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(213, 2).cuboid(-8.998F, -11.998F, 2.998F, 18.0F, 21.0F, 3.0F, new Dilation(0.001F))
				.uv(0, 84).cuboid(-12.0F, -15.0F, -15.0F, 24.0F, 18.0F, 18.0F, new Dilation(0.0F))
				.uv(184, 32).cuboid(-8.8F, -13.3F, -33.2F, 18.0F, 15.0F, 18.0F, new Dilation(0.1F))
				.uv(12, 21).cuboid(-8.4F, -0.4F, -32.6F, 2.0F, 9.0F, 3.0F, new Dilation(-0.2F))
				.uv(11, 29).cuboid(-2.6F, 1.4F, -32.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.01F))
				.uv(11, 29).cuboid(-11.6F, 1.4F, -2.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(12, 22).cuboid(-8.4236F, -0.4114F, -29.8157F, 2.0F, 6.0F, 3.0F, new Dilation(-0.2F))
				.uv(12, 22).cuboid(-8.4236F, -0.4114F, -26.8157F, 2.0F, 6.0F, 3.0F, new Dilation(-0.2F))
				.uv(12, 22).cuboid(-8.5236F, -0.5114F, -23.7157F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(11, 29).cuboid(-8.6F, 1.4F, -20.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(11, 29).cuboid(-8.6F, 1.4F, -17.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(12, 22).cuboid(-11.4236F, -0.4114F, -14.8157F, 3.0F, 7.0F, 3.0F, new Dilation(-0.2F))
				.uv(11, 29).cuboid(-11.6F, 1.4F, -5.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(12, 22).cuboid(-11.5236F, -0.5114F, -8.7157F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(12, 21).cuboid(6.6F, -0.4F, -32.6F, 2.0F, 9.0F, 3.0F, new Dilation(-0.2F))
				.uv(11, 29).cuboid(1.4F, 1.4F, -32.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.01F))
				.uv(12, 22).cuboid(4.6F, -0.4F, -32.6F, 2.0F, 6.0F, 3.0F, new Dilation(-0.2F))
				.uv(11, 29).cuboid(9.4F, 1.4F, -5.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(12, 23).cuboid(6.6F, -0.4F, -26.6F, 2.0F, 6.0F, 3.0F, new Dilation(-0.2F))
				.uv(12, 23).cuboid(6.5F, -0.5F, -23.5F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(11, 29).cuboid(6.4F, 1.4F, -20.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(11, 29).cuboid(6.4F, 1.4F, -17.4F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F))
				.uv(12, 23).cuboid(8.6F, -0.4F, -14.6F, 3.0F, 7.0F, 3.0F, new Dilation(-0.2F))
				.uv(12, 23).cuboid(9.5F, -0.5F, -11.5F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(12, 23).cuboid(9.5F, -0.5F, -8.5F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F)), ModelTransform.of(0.0F, -6.0F, -24.0F, 0.132F, -0.1298F, -0.0172F));

		ModelPartData lowerjaw = head.addChild("lowerjaw", ModelPartBuilder.create().uv(0, 124).cuboid(-9.0F, 3.0F, -30.0F, 18.0F, 6.0F, 33.0F, new Dilation(0.0F))
				.uv(13, 24).cuboid(5.6F, -0.4F, -8.6F, 3.0F, 6.0F, 2.0F, new Dilation(-0.2F))
				.uv(12, 23).cuboid(5.6F, -0.4F, -11.6F, 3.0F, 6.0F, 3.0F, new Dilation(-0.2F))
				.uv(12, 21).cuboid(6.5F, -3.5F, -14.5F, 2.0F, 9.0F, 3.0F, new Dilation(-0.25F))
				.uv(12, 23).cuboid(6.5F, -0.5F, -20.5F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(13, 25).cuboid(6.4F, -0.6F, -23.4F, 2.0F, 6.0F, 2.0F, new Dilation(-0.3F))
				.uv(13, 25).cuboid(6.3F, -0.7F, -26.3F, 2.0F, 6.0F, 2.0F, new Dilation(-0.35F))
				.uv(12, 30).cuboid(6.4F, 1.4F, -28.4F, 1.0F, 4.0F, 1.0F, new Dilation(0.3F))
				.uv(12, 23).cuboid(2.5F, -0.5F, -29.5F, 3.0F, 6.0F, 3.0F, new Dilation(-0.55F))
				.uv(12, 23).cuboid(-0.4F, -0.4F, -29.6F, 3.0F, 6.0F, 3.0F, new Dilation(-0.3F))
				.uv(12, 23).cuboid(-3.4F, -0.4F, -29.6F, 3.0F, 6.0F, 3.0F, new Dilation(-0.3F))
				.uv(13, 23).cuboid(-6.5F, -0.5F, -29.5F, 3.0F, 6.0F, 3.0F, new Dilation(-0.55F))
				.uv(12, 30).cuboid(-7.6F, 1.4F, -28.4F, 1.0F, 4.0F, 1.0F, new Dilation(0.3F))
				.uv(13, 25).cuboid(-8.7F, -0.7F, -26.3F, 2.0F, 6.0F, 2.0F, new Dilation(-0.35F))
				.uv(13, 23).cuboid(-8.5236F, -0.6541F, -20.6513F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(13, 23).cuboid(-8.5236F, -0.6541F, -17.6513F, 2.0F, 6.0F, 3.0F, new Dilation(-0.25F))
				.uv(12, 21).cuboid(-8.5236F, -3.6541F, -14.6513F, 2.0F, 9.0F, 3.0F, new Dilation(-0.25F))
				.uv(12, 22).cuboid(-8.4236F, -1.5541F, -11.7513F, 3.0F, 7.0F, 3.0F, new Dilation(-0.2F))
				.uv(14, 24).cuboid(-8.4236F, -0.5541F, -8.7513F, 3.0F, 6.0F, 2.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData rightarm = upperbody.addChild("rightarm", ModelPartBuilder.create(), ModelTransform.of(-12.0F, 3.0F, 0.0F, 0.7418F, 0.0F, 0.0F));

		ModelPartData shoulder = rightarm.addChild("shoulder", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-2.998F, -2.998F, -6.002F, 6.0F, 9.0F, 9.0F, new Dilation(0.001F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData arm = shoulder.addChild("arm", ModelPartBuilder.create().uv(244, 29).mirrored().cuboid(0.0F, 0.0F, -3.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.0F, 3.0F, -3.0F, -1.328F, -0.5885F, 1.3495F));

		ModelPartData forearm = arm.addChild("forearm", ModelPartBuilder.create().uv(244, 69).mirrored().cuboid(-0.01F, -0.01F, -2.99F, 3.0F, 12.0F, 3.0F, new Dilation(-0.005F)).mirrored(false), ModelTransform.of(0.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.9163F));

		ModelPartData fingerclaw01 = forearm.addChild("fingerclaw01", ModelPartBuilder.create().uv(40, 247).mirrored().cuboid(-0.06F, -2.8316F, -3.2919F, 3.0F, 6.0F, 3.0F, new Dilation(-0.03F)).mirrored(false)
				.uv(61, 246).cuboid(0.5F, -0.2716F, -2.8519F, 2.0F, 6.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.of(1.0F, 12.0F, 2.0F, 0.5888F, 0.1733F, -0.2528F));

		ModelPartData fingerclaw02 = forearm.addChild("fingerclaw02", ModelPartBuilder.create().uv(40, 247).mirrored().cuboid(-0.06F, -2.9212F, -0.8421F, 3.0F, 6.0F, 3.0F, new Dilation(-0.03F)).mirrored(false)
				.uv(62, 246).cuboid(0.5F, -0.3612F, -0.4021F, 2.0F, 6.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.of(1.0F, 12.0F, -4.0F, -0.6396F, -0.1298F, -0.228F));

		ModelPartData leftarm = upperbody.addChild("leftarm", ModelPartBuilder.create(), ModelTransform.of(12.0F, 3.0F, 0.0F, 0.3927F, -0.1745F, 0.0F));

		ModelPartData leftshoulder = leftarm.addChild("leftshoulder", ModelPartBuilder.create().uv(0, 0).cuboid(-2.998F, -2.998F, -6.002F, 6.0F, 9.0F, 9.0F, new Dilation(0.001F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData arm2 = leftshoulder.addChild("arm2", ModelPartBuilder.create().uv(195, 0).cuboid(-3.0F, 0.0F, -3.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 3.0F, -3.0F, -0.7345F, 0.5963F, -0.7105F));

		ModelPartData forearm2 = arm2.addChild("forearm2", ModelPartBuilder.create().uv(165, 39).cuboid(-0.6378F, -0.01F, -2.3697F, 3.0F, 12.0F, 3.0F, new Dilation(-0.005F)), ModelTransform.of(-2.1705F, 9.0F, -0.4675F, -0.4054F, -0.0174F, 0.3426F));

		ModelPartData fingerclaw03 = forearm2.addChild("fingerclaw03", ModelPartBuilder.create().uv(40, 247).cuboid(-3.06F, -2.6581F, -1.44F, 3.0F, 6.0F, 3.0F, new Dilation(-0.03F))
				.uv(61, 246).cuboid(-2.5F, -0.0981F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.of(3.3722F, 12.0F, 0.6203F, 0.514F, -0.1199F, -0.318F));

		ModelPartData fingerclaw04 = forearm2.addChild("fingerclaw04", ModelPartBuilder.create().uv(40, 247).cuboid(-3.06F, -2.5175F, -1.6607F, 3.0F, 6.0F, 3.0F, new Dilation(-0.03F))
				.uv(62, 246).cuboid(-2.5F, 0.0425F, -1.2207F, 2.0F, 6.0F, 2.0F, new Dilation(-0.25F)), ModelTransform.of(3.3722F, 12.0F, -2.3797F, -0.5996F, -0.1245F, -0.1796F));

		ModelPartData tail = mainbody.addChild("tail", ModelPartBuilder.create().uv(0, 244).cuboid(-6.0F, -9.0F, -3.0F, 12.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(71, 0).cuboid(-9.0F, -3.0F, 0.0F, 18.0F, 9.0F, 6.0F, new Dilation(0.0F))
				.uv(61, 215).cuboid(-3.0F, 6.0F, -3.0F, 6.0F, 6.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 18.0F, 0.0004F, 0.0001F, 0.0057F));

		ModelPartData start = tail.addChild("start", ModelPartBuilder.create().uv(45, 17).cuboid(-3.002F, -9.002F, -5.998F, 6.0F, 6.0F, 42.0F, new Dilation(-0.001F))
				.uv(26, 26).cuboid(-6.002F, -3.002F, -1.498F, 12.0F, 9.0F, 15.0F, new Dilation(-0.001F))
				.uv(0, 196).cuboid(-3.002F, -3.002F, 9.002F, 6.0F, 9.0F, 21.0F, new Dilation(-0.001F)), ModelTransform.of(0.0F, 0.0F, 6.0F, -0.0876F, 0.0869F, -0.0076F));

		ModelPartData mid = start.addChild("mid", ModelPartBuilder.create().uv(57, 31).cuboid(-3.004F, -6.004F, 0.004F, 6.0F, 3.0F, 30.0F, new Dilation(-0.002F))
				.uv(190, 157).cuboid(-3.004F, -3.004F, -4.496F, 6.0F, 6.0F, 27.0F, new Dilation(-0.002F))
				.uv(193, 160).cuboid(-3.004F, -3.004F, 18.004F, 6.0F, 6.0F, 24.0F, new Dilation(-0.002F)), ModelTransform.of(0.0F, 0.0F, 30.0F, -0.132F, 0.1298F, -0.0172F));

		ModelPartData edge = mid.addChild("edge", ModelPartBuilder.create().uv(193, 159).cuboid(-3.006F, -3.006F, -2.994F, 6.0F, 6.0F, 24.0F, new Dilation(-0.003F)), ModelTransform.of(0.0F, 0.0F, 42.0F, -0.1772F, 0.1719F, -0.0306F));

		ModelPartData tip = edge.addChild("tip", ModelPartBuilder.create().uv(190, 159).cuboid(-3.008F, -0.008F, -1.492F, 6.0F, 3.0F, 27.0F, new Dilation(-0.004F)), ModelTransform.of(0.0F, 0.0F, 21.0F, -0.2618F, 0.0F, 0.0F));

		ModelPartData rightleg = Trex.addChild("rightleg", ModelPartBuilder.create().uv(184, 104).mirrored().cuboid(-2.98F, -11.98F, -13.52F, 9.0F, 21.0F, 27.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(-18.0F, -54.0F, 3.0F, 0.2976F, 0.1368F, -0.0396F));

		ModelPartData upperleg = rightleg.addChild("upperleg", ModelPartBuilder.create().uv(208, 199).mirrored().cuboid(-3.002F, 2.998F, -17.998F, 6.0F, 24.0F, 18.0F, new Dilation(-0.001F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData lowerleg = upperleg.addChild("lowerleg", ModelPartBuilder.create().uv(68, 67).cuboid(-3.004F, -3.004F, -5.996F, 6.0F, 9.0F, 24.0F, new Dilation(-0.002F))
				.uv(43, 3).cuboid(3.0F, 3.0F, 3.0F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 27.0F, -9.0F, -0.6981F, 0.0873F, 0.0F));

		ModelPartData ankle = lowerleg.addChild("ankle", ModelPartBuilder.create().uv(0, 123).mirrored().cuboid(-3.002F, -3.002F, -2.998F, 6.0F, 18.0F, 6.0F, new Dilation(-0.001F)).mirrored(false)
				.uv(176, 0).cuboid(-0.002F, -0.002F, 3.002F, 3.0F, 18.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.of(3.0F, 6.0F, 15.0F, 0.1301F, 0.0695F, -0.0334F));

		ModelPartData foot = ankle.addChild("foot", ModelPartBuilder.create(), ModelTransform.of(0.0F, 12.0F, 0.0F, 0.0F, 0.2182F, 0.0F));

		ModelPartData thumbmain = foot.addChild("thumbmain", ModelPartBuilder.create().uv(34, 0).mirrored().cuboid(0.04F, 0.04F, -6.04F, 3.0F, 9.0F, 9.0F, new Dilation(0.02F)).mirrored(false), ModelTransform.of(3.0F, 0.0F, 0.0F, 0.1806F, -0.2577F, -0.0465F));

		ModelPartData thumbtoe = thumbmain.addChild("thumbtoe", ModelPartBuilder.create().uv(0, 62).mirrored().cuboid(0.4179F, -1.5675F, -9.2483F, 3.0F, 6.0F, 12.0F, new Dilation(-0.003F)).mirrored(false)
				.uv(57, 244).mirrored().cuboid(0.4239F, -0.8615F, -11.9543F, 3.0F, 5.0F, 6.0F, new Dilation(-0.25F)).mirrored(false)
				.uv(57, 246).mirrored().cuboid(0.4239F, 1.0385F, -14.8543F, 3.0F, 3.0F, 6.0F, new Dilation(-0.25F)).mirrored(false), ModelTransform.of(-0.4239F, 4.5615F, -5.7457F, -0.3016F, -0.0257F, 0.0311F));

		ModelPartData footmain = foot.addChild("footmain", ModelPartBuilder.create().uv(34, 0).mirrored().cuboid(0.04F, 0.04F, -6.04F, 3.0F, 9.0F, 9.0F, new Dilation(0.02F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0873F, -0.0436F, 0.0F));

		ModelPartData foottoe = footmain.addChild("foottoe", ModelPartBuilder.create().uv(1, 37).mirrored().cuboid(-2.0297F, -2.5813F, -13.3602F, 3.0F, 6.0F, 15.0F, new Dilation(-0.003F)).mirrored(false)
				.uv(57, 244).mirrored().cuboid(-2.0237F, -1.8753F, -17.5662F, 3.0F, 5.0F, 6.0F, new Dilation(-0.15F)).mirrored(false)
				.uv(57, 246).mirrored().cuboid(-2.0237F, 0.0247F, -20.4662F, 3.0F, 3.0F, 6.0F, new Dilation(-0.2F)).mirrored(false), ModelTransform.of(2.0237F, 5.5753F, -3.1338F, -0.1745F, 0.0F, 0.0F));

		ModelPartData heelmain = foot.addChild("heelmain", ModelPartBuilder.create().uv(34, 0).mirrored().cuboid(-2.96F, 0.04F, -6.04F, 3.0F, 9.0F, 9.0F, new Dilation(0.02F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1772F, 0.1719F, 0.0306F));

		ModelPartData heeltoe = heelmain.addChild("heeltoe", ModelPartBuilder.create().uv(0, 62).mirrored().cuboid(-3.006F, 2.994F, -14.994F, 3.0F, 6.0F, 12.0F, new Dilation(-0.003F)).mirrored(false)
				.uv(57, 244).mirrored().cuboid(-3.0F, 3.7F, -17.7F, 3.0F, 5.0F, 6.0F, new Dilation(-0.15F)).mirrored(false)
				.uv(57, 245).mirrored().cuboid(-3.0F, 5.6F, -20.6F, 3.0F, 3.0F, 6.0F, new Dilation(-0.2F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		ModelPartData leftleg = Trex.addChild("leftleg", ModelPartBuilder.create().uv(117, 41).cuboid(-5.98F, -11.98F, -13.52F, 9.0F, 21.0F, 27.0F, new Dilation(0.01F)), ModelTransform.of(18.0F, -54.0F, 3.0F, 0.0879F, -0.1744F, -0.0039F));

		ModelPartData upperleg2 = leftleg.addChild("upperleg2", ModelPartBuilder.create().uv(208, 199).cuboid(-2.6104F, 0.2686F, -14.7735F, 6.0F, 24.0F, 18.0F, new Dilation(-0.001F)), ModelTransform.of(-0.3916F, 2.7294F, -3.2245F, -0.3927F, 0.0F, 0.0F));

		ModelPartData lowerleg2 = upperleg2.addChild("lowerleg2", ModelPartBuilder.create().uv(43, 3).mirrored().cuboid(-6.6249F, 4.4878F, 5.0787F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F)).mirrored(false)
				.uv(68, 67).mirrored().cuboid(-3.6289F, -1.5162F, -3.9173F, 6.0F, 9.0F, 24.0F, new Dilation(-0.002F)).mirrored(false), ModelTransform.of(1.0164F, 22.7829F, -7.8542F, -0.5668F, -0.0234F, -0.0368F));

		ModelPartData ankle2 = lowerleg2.addChild("ankle2", ModelPartBuilder.create().uv(176, 0).mirrored().cuboid(-3.2609F, -0.3066F, 1.7019F, 3.0F, 18.0F, 3.0F, new Dilation(-0.001F)).mirrored(false)
				.uv(0, 123).cuboid(-3.2609F, -3.3066F, -4.2981F, 6.0F, 18.0F, 6.0F, new Dilation(-0.001F)), ModelTransform.of(-3.3659F, 7.7924F, 18.3788F, -0.1745F, 0.0F, 0.0F));

		ModelPartData foot2 = ankle2.addChild("foot2", ModelPartBuilder.create(), ModelTransform.of(-2.3495F, 13.6815F, -3.2506F, 1.0472F, 0.0F, 0.0F));

		ModelPartData thumbmain2 = foot2.addChild("thumbmain2", ModelPartBuilder.create().uv(34, 0).cuboid(-2.96F, -2.96F, -6.04F, 3.0F, 9.0F, 9.0F, new Dilation(0.02F)), ModelTransform.of(-0.9095F, 1.014F, 1.9505F, 0.1791F, 0.2595F, 0.035F));

		ModelPartData thumbtoe2 = thumbmain2.addChild("thumbtoe2", ModelPartBuilder.create().uv(0, 62).cuboid(-3.006F, -3.006F, -8.994F, 3.0F, 6.0F, 12.0F, new Dilation(-0.003F))
				.uv(57, 244).cuboid(-3.0F, -2.3F, -11.7F, 3.0F, 5.0F, 6.0F, new Dilation(-0.15F))
				.uv(57, 245).cuboid(-3.0F, -0.4F, -14.6F, 3.0F, 3.0F, 6.0F, new Dilation(-0.2F)), ModelTransform.of(0.0F, 3.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData footmain2 = foot2.addChild("footmain2", ModelPartBuilder.create().uv(34, 0).cuboid(-2.96F, -2.96F, -6.04F, 3.0F, 9.0F, 9.0F, new Dilation(0.02F)), ModelTransform.of(2.0905F, 1.014F, 1.9505F, 0.0873F, 0.0F, 0.0F));

		ModelPartData foottoe2 = footmain2.addChild("foottoe2", ModelPartBuilder.create().uv(1, 37).cuboid(-0.006F, -3.006F, -10.494F, 3.0F, 6.0F, 15.0F, new Dilation(-0.003F))
				.uv(57, 244).cuboid(0.0F, -2.3F, -14.7F, 3.0F, 5.0F, 6.0F, new Dilation(-0.15F))
				.uv(57, 245).cuboid(0.0F, -0.4F, -17.6F, 3.0F, 3.0F, 6.0F, new Dilation(-0.2F)), ModelTransform.pivot(-3.0F, 3.0F, -6.0F));

		ModelPartData heelmain2 = foot2.addChild("heelmain2", ModelPartBuilder.create().uv(34, 0).cuboid(0.04F, -2.96F, -6.04F, 3.0F, 9.0F, 9.0F, new Dilation(0.02F)), ModelTransform.of(2.0905F, 1.014F, 1.9505F, 0.1745F, -0.2618F, 0.0F));

		ModelPartData heeltoe2 = heelmain2.addChild("heeltoe2", ModelPartBuilder.create().uv(0, 62).cuboid(-0.006F, -3.006F, -8.994F, 3.0F, 6.0F, 12.0F, new Dilation(-0.003F))
				.uv(57, 244).cuboid(0.0F, -2.3F, -11.7F, 3.0F, 5.0F, 6.0F, new Dilation(-0.15F))
				.uv(57, 245).cuboid(0.0F, -0.4F, -14.6F, 3.0F, 3.0F, 6.0F, new Dilation(-0.2F)), ModelTransform.of(0.0F, 3.0F, -6.0F, -0.0873F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void setAngles(TrexEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.WALK, limbSwing, limbSwingAmount, 2f, 25.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackingAnimationState, ModAnimations.TREX_ATTACK, ageInTicks, 1f);

	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25, 45);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Trex.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return Trex;
	}
}
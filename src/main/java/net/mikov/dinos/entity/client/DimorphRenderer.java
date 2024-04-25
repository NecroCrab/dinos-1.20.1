package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.DimorphEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DimorphRenderer extends MobEntityRenderer<DimorphEntity, DimorphModel<DimorphEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/dimorph_texture.png");

    public DimorphRenderer(EntityRendererFactory.Context context) {
        super(context, new DimorphModel<>(context.getPart(ModModelLayers.DIMORPH)), 0.25f);
    }

    @Override
    public Identifier getTexture(DimorphEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DimorphEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    /*@Override
    public float getAnimationProgress(DimorphEntity dimorphEntity, float f) {
        float g = MathHelper.lerp(f, dimorphEntity.prevFlapProgress, dimorphEntity.flapProgress);
        float h = MathHelper.lerp(f, dimorphEntity.prevMaxWingDeviation, dimorphEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0f) * h;
    }*/

}

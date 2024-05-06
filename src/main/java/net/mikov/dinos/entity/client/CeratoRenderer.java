package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.CeratoEntity;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CeratoRenderer extends MobEntityRenderer<CeratoEntity, CeratoModel<CeratoEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/cerato_texture.png");

    public CeratoRenderer(EntityRendererFactory.Context context) {
        super(context, new CeratoModel<>(context.getPart(ModModelLayers.CERATO)), 1f);
    }

    @Override
    public Identifier getTexture(CeratoEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(CeratoEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.75f, 0.75f, 0.75f);
        } else {
            matrixStack.scale( 1.5f, 1.5f, 1.5f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

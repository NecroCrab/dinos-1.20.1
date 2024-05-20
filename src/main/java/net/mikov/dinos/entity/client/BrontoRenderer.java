package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.BrontoEntity;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BrontoRenderer extends MobEntityRenderer<BrontoEntity, BrontoModel<BrontoEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/bronto_texture.png");

    public BrontoRenderer(EntityRendererFactory.Context context) {
        super(context, new BrontoModel<>(context.getPart(ModModelLayers.BRONTO)), 2f);
    }

    @Override
    public Identifier getTexture(BrontoEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BrontoEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.35f, 0.35f, 0.35f);
        } else {
            matrixStack.scale( 1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

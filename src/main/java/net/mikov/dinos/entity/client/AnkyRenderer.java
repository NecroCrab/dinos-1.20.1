package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AnkyRenderer extends MobEntityRenderer<AnkyEntity, AnkyModel<AnkyEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/anky_texture.png");

    public AnkyRenderer(EntityRendererFactory.Context context) {
        super(context, new AnkyModel<>(context.getPart(ModModelLayers.ANKY)), 1f);
    }

    @Override
    public Identifier getTexture(AnkyEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(AnkyEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

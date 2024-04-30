package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.DodoEntity;
import net.mikov.dinos.entity.custom.TrilobiteEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TrilobiteRenderer extends MobEntityRenderer<TrilobiteEntity, TrilobiteModel<TrilobiteEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/trilobite_texture.png");

    public TrilobiteRenderer(EntityRendererFactory.Context context) {
        super(context, new TrilobiteModel<>(context.getPart(ModModelLayers.TRILOBITE)), 0.25f);
    }

    @Override
    public Identifier getTexture(TrilobiteEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TrilobiteEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

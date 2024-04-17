package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.CompyEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CompyRenderer extends MobEntityRenderer<CompyEntity, CompyModel<CompyEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/compy_texture.png");

    public CompyRenderer(EntityRendererFactory.Context context) {
        super(context, new CompyModel<>(context.getPart(ModModelLayers.COMPY)), 0.25f);
    }

    @Override
    public Identifier getTexture(CompyEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(CompyEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.35f, 0.35f, 0.35f);
        } else {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.mikov.dinos.entity.custom.TrikeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TrikeRenderer extends MobEntityRenderer<TrikeEntity, TrikeModel<TrikeEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/trike_texture.png");

    public TrikeRenderer(EntityRendererFactory.Context context) {
        super(context, new TrikeModel<>(context.getPart(ModModelLayers.TRIKE)), 1f);
    }

    @Override
    public Identifier getTexture(TrikeEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TrikeEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1.75f, 1.75f, 1.75f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

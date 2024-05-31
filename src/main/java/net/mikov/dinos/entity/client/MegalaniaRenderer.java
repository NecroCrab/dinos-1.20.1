package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.AnkyEntity;
import net.mikov.dinos.entity.custom.MegalaniaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MegalaniaRenderer extends MobEntityRenderer<MegalaniaEntity, MegalaniaModel<MegalaniaEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/megalania_texture.png");

    public MegalaniaRenderer(EntityRendererFactory.Context context) {
        super(context, new MegalaniaModel<>(context.getPart(ModModelLayers.MEGALANIA)), 1f);
    }

    @Override
    public Identifier getTexture(MegalaniaEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(MegalaniaEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

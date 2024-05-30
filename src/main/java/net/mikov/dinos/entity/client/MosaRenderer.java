package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.MosaEntity;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MosaRenderer extends MobEntityRenderer<MosaEntity, MosaModel<MosaEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/mosa_texture.png");

    public MosaRenderer(EntityRendererFactory.Context context) {
        super(context, new MosaModel<>(context.getPart(ModModelLayers.MOSA)), 2f);
    }

    @Override
    public Identifier getTexture(MosaEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(MosaEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

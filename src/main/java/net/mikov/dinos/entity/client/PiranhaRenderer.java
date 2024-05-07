package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.CeratoEntity;
import net.mikov.dinos.entity.custom.PiranhaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PiranhaRenderer extends MobEntityRenderer<PiranhaEntity, PiranhaModel<PiranhaEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dinos.MOD_ID, "textures/entity/piranha_texture.png");

    public PiranhaRenderer(EntityRendererFactory.Context context) {
        super(context, new PiranhaModel<>(context.getPart(ModModelLayers.PIRANHA)), 1f);
    }

    @Override
    public Identifier getTexture(PiranhaEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(PiranhaEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale( 0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale( 1.0f, 1.0f, 1.0f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}

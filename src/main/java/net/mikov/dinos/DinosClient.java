package net.mikov.dinos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.client.*;
import net.mikov.dinos.gui.ModScreenHandlers;
import net.mikov.dinos.gui.MountScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class DinosClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.TREX, TrexRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TREX, TrexModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DODO, DodoRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DODO, DodoModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.COMPY, CompyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.COMPY, CompyModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DIMORPH, DimorphRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DIMORPH, DimorphModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.COEL, CoelRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.COEL, CoelModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.ANKY, AnkyRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ANKY, AnkyModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.TRILOBITE, TrilobiteRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TRILOBITE, TrilobiteModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.CERATO, CeratoRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CERATO, CeratoModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PIRANHA, PiranhaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PIRANHA, PiranhaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MEGALANIA, MegalaniaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MEGALANIA, MegalaniaModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.BRONTO, BrontoRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BRONTO, BrontoModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MEGANEURA, MeganeuraRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MEGANEURA, MeganeuraModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.TRIKE, TrikeRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TRIKE, TrikeModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.MOSA, MosaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MOSA, MosaModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMBER_FOSSIL_BLOCK, RenderLayer.getTranslucent());

        //HandledScreens.register(ModScreenHandlers.MOUNT_SCREEN_HANDLER, MountScreen::new);

    }
}

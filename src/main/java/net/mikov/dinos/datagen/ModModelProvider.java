package net.mikov.dinos.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.mikov.dinos.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerParentedItemModel(ModItems.TREX_SPAWN_EGG,
                ModelIds.getMinecraftNamespacedItem("item/template_spawn_egg"));
        blockStateModelGenerator.registerParentedItemModel(ModItems.DODO_SPAWN_EGG,
                ModelIds.getMinecraftNamespacedItem("item/template_spawn_egg"));

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}

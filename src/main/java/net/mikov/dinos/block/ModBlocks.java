package net.mikov.dinos.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.block.custom.CompyEggBlock;
import net.mikov.dinos.block.custom.DodoEggBlock;
import net.mikov.dinos.block.custom.TrilobiteEggBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block TRILOBITE_EGG_BLOCK = registerBlock( "trilobite_egg_block",
            new TrilobiteEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));
    public static final Block DODO_EGG_BLOCK = registerBlock( "dodo_egg_block",
            new DodoEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));
    public static final Block COMPY_EGG_BLOCK = registerBlock( "compy_egg_block",
            new CompyEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Dinos.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Dinos.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Dinos.LOGGER.info("Registering DinoBlocks");
    }
}

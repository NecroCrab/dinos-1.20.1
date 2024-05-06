package net.mikov.dinos.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.block.custom.*;
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
    public static final Block DIMORPH_EGG_BLOCK = registerBlock( "dimorph_egg_block",
            new DimorphEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));
    public static final Block ANKY_EGG_BLOCK = registerBlock( "anky_egg_block",
            new AnkyEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));
    public static final Block TREX_EGG_BLOCK = registerBlock( "trex_egg_block",
            new TrexEggBlock(FabricBlockSettings.copyOf(Blocks.SNIFFER_EGG)));
    public static final Block COEL_EGG_BLOCK = registerBlock( "coel_egg_block",
            new CoelEggBlock(FabricBlockSettings.copyOf(Blocks.FROGSPAWN)));
    public static final Block CERATO_EGG_BLOCK = registerBlock( "cerato_egg_block",
            new CeratoEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));

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

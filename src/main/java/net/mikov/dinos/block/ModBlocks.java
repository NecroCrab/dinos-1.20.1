package net.mikov.dinos.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.block.custom.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

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
    public static final Block PIRANHA_EGG_BLOCK = registerBlock( "piranha_egg_block",
            new PiranhaEggBlock(FabricBlockSettings.copyOf(Blocks.FROGSPAWN)));
    public static final Block MEGALANIA_EGG_BLOCK = registerBlock( "megalania_egg_block",
            new MegalaniaEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));
    public static final Block BRONTO_EGG_BLOCK = registerBlock( "bronto_egg_block",
            new BrontoEggBlock(FabricBlockSettings.copyOf(Blocks.SNIFFER_EGG)));
    public static final Block MEGANEURA_EGG_BLOCK = registerBlock( "meganeura_egg_block",
            new MeganeuraEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));

    public static final Block MEGANEURA_HIVE_BLOCK = registerBlock( "meganeura_hive_block",
            new MeganeuraHiveBlock(FabricBlockSettings.copyOf(Blocks.BEEHIVE)));

    public static final Block EXTRACTOR_BLOCK = registerBlock( "extractor_block",
            new ExtractorBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE).nonOpaque()));

    public static final Block SILT_BLOCK = registerBlock( "silt_block",
            new GravelBlock(FabricBlockSettings.copyOf(Blocks.GRAVEL)));
    public static final Block FOSSIL_BLOCK = registerBlock( "fossil_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).requiresTool().strength(1.0f, 1.0f), UniformIntProvider.create(3, 7)));
    public static final Block DEEPSLATE_FOSSIL_BLOCK = registerBlock( "deepslate_fossil_block",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).requiresTool().strength(1.0f, 1.0f), UniformIntProvider.create(3, 7)));

    public static final Block TRILOBITE_FOSSIL_BLOCK = registerBlock( "trilobite_fossil_block",
            new Block(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).nonOpaque()));

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

package net.mikov.dinos;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.custom.*;
import net.mikov.dinos.item.ModItems;
import net.mikov.dinos.sounds.ModSounds;
import net.mikov.dinos.util.ModLootTableModifiers;
import net.mikov.dinos.world.gen.ModWorldGen;
import net.mikov.dinos.world.gen.ModWorldOreGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dinos implements ModInitializer {

	public static final String MOD_ID = "dinos";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("dinos");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Dino roars with bestial vigor!");
		FabricDefaultAttributeRegistry.register(ModEntities.TREX, TrexEntity.createTrexAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.DODO, DodoEntity.createDodoAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.COMPY, CompyEntity.createCompyAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.DIMORPH, DimorphEntity.createDimorphAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.COEL, CoelEntity.createCoelAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ANKY, AnkyEntity.createAnkyAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.TRILOBITE, TrilobiteEntity.createTriloAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CERATO, CeratoEntity.createCeratoAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.PIRANHA, PiranhaEntity.createPiranhaAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MEGALANIA, MegalaniaEntity.createMegalaniaAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BRONTO, BrontoEntity.createBrontoAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MEGANEURA, MeganeuraEntity.createMeganeuraAttributes());

		ModWorldGen.generateWorldGen();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModSounds.registerSounds();
		ModWorldOreGen.generateModWorldOres();
		ModLootTableModifiers.modifyLootTables();
	}
}
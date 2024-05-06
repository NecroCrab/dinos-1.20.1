package net.mikov.dinos;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.custom.*;
import net.mikov.dinos.gui.MountScreen;
import net.mikov.dinos.gui.MountScreenHandler;
import net.mikov.dinos.item.ModItems;
import net.mikov.dinos.registry.FishingLoot;
import net.mikov.dinos.sounds.ModSounds;
import net.mikov.dinos.world.gen.ModEntitySpawn;
import net.mikov.dinos.world.gen.ModWorldGen;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
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

		ModWorldGen.generateWorldGen();
		FishingLoot.registerFishingLoot();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModSounds.registerSounds();
	}
}
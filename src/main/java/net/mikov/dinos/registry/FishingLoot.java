package net.mikov.dinos.registry;

import net.mikov.dinos.Dinos;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.mikov.dinos.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class FishingLoot {

    private static final Identifier FISH_LOOT = new Identifier("gameplay/fishing/fish");

    public static void registerFishingLoot() {

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && FISH_LOOT.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder();
                poolBuilder.with(ItemEntry.builder(ModItems.RAW_COEL).weight(25));
                poolBuilder.with(ItemEntry.builder(Items.COD).weight(60));
                poolBuilder.with(ItemEntry.builder(Items.SALMON).weight(25));
                poolBuilder.with(ItemEntry.builder(Items.PUFFERFISH).weight(13));
                poolBuilder.with(ItemEntry.builder(Items.TROPICAL_FISH).weight(2));

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}

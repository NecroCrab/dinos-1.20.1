package net.mikov.dinos.util;

import com.mojang.datafixers.kinds.Const;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.mikov.dinos.block.ModBlocks;
import net.mikov.dinos.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModLootTableModifiers {

    private static final Identifier FISHING = new Identifier( "minecraft", "gameplay/fishing/fish");
    private static final Identifier SUSPICIOUS_SAND = new Identifier( "minecraft", "archaeology/desert_pyramid");
    private static final Identifier SUSPICIOUS_GRAVEL = new Identifier( "minecraft", "archaeology/ocean_ruin_cold");

    public static void modifyLootTables() {
        /*LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) ->  {
            if (FISHING.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder( 0.25f))
                        .with(ItemEntry.builder(ModItems.RAW_COEL))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if (FISHING.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder( 0.15f))
                        .with(ItemEntry.builder(ModItems.RAW_PIRANHA))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });*/
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if (FISHING.equals(id)) {
                List<LootPoolEntry> entries = new ArrayList<>(Arrays.asList(original.pools[0].entries));
                entries.add(ItemEntry.builder(ModItems.RAW_COEL).weight(25).build());
                entries.add(ItemEntry.builder(ModItems.RAW_PIRANHA).weight(15).build());
                LootPool.Builder pool = LootPool.builder().with(entries);
                return LootTable.builder().pool(pool).build();
            }
                return null;
        });
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if (SUSPICIOUS_SAND.equals(id)) {
                List<LootPoolEntry> entries = new ArrayList<>(Arrays.asList(original.pools[0].entries));
                entries.add(ItemEntry.builder(ModBlocks.SILT_BLOCK).build());
                entries.add(ItemEntry.builder(ModBlocks.FOSSIL_BLOCK).build());
                LootPool.Builder pool = LootPool.builder().with(entries);
                return LootTable.builder().pool(pool).build();
            }
            return null;
        });
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if (SUSPICIOUS_GRAVEL.equals(id)) {
                List<LootPoolEntry> entries = new ArrayList<>(Arrays.asList(original.pools[0].entries));
                entries.add(ItemEntry.builder(ModBlocks.SILT_BLOCK).build());
                entries.add(ItemEntry.builder(ModBlocks.FOSSIL_BLOCK).build());
                LootPool.Builder pool = LootPool.builder().with(entries);
                return LootTable.builder().pool(pool).build();
            }
            return null;
        });
    }
}

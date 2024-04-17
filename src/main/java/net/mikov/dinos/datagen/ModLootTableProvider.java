package net.mikov.dinos.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider implements FabricLootTableProvider {
    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {

    }
}

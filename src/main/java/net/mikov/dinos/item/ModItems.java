package net.mikov.dinos.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;


public class ModItems {

    public static final Item TREX_SPAWN_EGG = registerItem( "trex_spawn_egg",
            new SpawnEggItem(ModEntities.TREX, 0xD07444, 0x734831, new FabricItemSettings()));
    public static final Item DODO_SPAWN_EGG = registerItem( "dodo_spawn_egg",
            new SpawnEggItem(ModEntities.DODO, 0xA1A1A1, 0xFCC369, new FabricItemSettings()));
    public static final Item COMPY_SPAWN_EGG = registerItem( "compy_spawn_egg",
            new SpawnEggItem(ModEntities.COMPY, 0x315234, 0x0DA70B, new FabricItemSettings()));
    public static final Item DIMORPH_SPAWN_EGG = registerItem( "dimorph_spawn_egg",
            new SpawnEggItem(ModEntities.DIMORPH, 0x957256, 0x0F0F0F, new FabricItemSettings()));
    public static final Item COEL_SPAWN_EGG = registerItem( "coel_spawn_egg",
            new SpawnEggItem(ModEntities.COEL, 0xA1A1A1, 0xF9F9F9, new FabricItemSettings()));

    public static final Item RAW_PRIMAL_MEAT = registerItem("raw_primal_meat", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_PRIMAL_MEAT)));
    public static final Item COOKED_PRIMAL_MEAT = registerItem("cooked_primal_meat", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_PRIMAL_MEAT)));

    public static final Item RAW_COEL = registerItem("raw_coel", new Item(new FabricItemSettings().food(ModFoodComponents.RAW_COEL)));
    public static final Item COOKED_COEL = registerItem("cooked_coel", new Item(new FabricItemSettings().food(ModFoodComponents.COOKED_COEL)));
    public static final Item COEL_BUCKET = registerItem("coel_bucket",
            new EntityBucketItem(ModEntities.COEL, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1)));

    public static void addItemsToFoodItemGroup(FabricItemGroupEntries entries) {
        entries.add(RAW_PRIMAL_MEAT);
        entries.add(COOKED_PRIMAL_MEAT);
        entries.add(RAW_COEL);
        entries.add(COOKED_COEL);
        entries.add(COEL_BUCKET);
    }

    public static void addItemsToSpawnEggItemGroup(FabricItemGroupEntries entries) {
        entries.add(TREX_SPAWN_EGG);
        entries.add(DODO_SPAWN_EGG);
        entries.add(COMPY_SPAWN_EGG);
        entries.add(DIMORPH_SPAWN_EGG);
        entries.add(COEL_SPAWN_EGG);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Dinos.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Dinos.LOGGER.info("Registering prehistoric items...");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::addItemsToFoodItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ModItems::addItemsToSpawnEggItemGroup);
    }
}

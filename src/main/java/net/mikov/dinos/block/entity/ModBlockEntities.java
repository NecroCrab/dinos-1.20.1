package net.mikov.dinos.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<MeganeuraHiveBlockEntity> BUGHIVE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Dinos.MOD_ID, "bughive"),
                    FabricBlockEntityTypeBuilder.create(MeganeuraHiveBlockEntity::new,
                            ModBlocks.MEGANEURA_HIVE_BLOCK).build());

    public static void registerBlockEntities() {
        Dinos.LOGGER.info("Registering block entities");
    }
}

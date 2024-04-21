package net.mikov.dinos.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.CompyEntity;
import net.mikov.dinos.entity.custom.DimorphEntity;
import net.mikov.dinos.entity.custom.DodoEntity;
import net.mikov.dinos.entity.custom.TrexEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TrexEntity> TREX = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "trex"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TrexEntity::new)
                    .dimensions(EntityDimensions.changing( 4f, 5f)).build());

    public static final EntityType<DodoEntity> DODO = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "dodo"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DodoEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

    public static final EntityType<CompyEntity> COMPY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "compy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CompyEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

    public static final EntityType<DimorphEntity> DIMORPH = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "dimorph"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DimorphEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

}

package net.mikov.dinos.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.mikov.dinos.Dinos;
import net.mikov.dinos.entity.custom.*;
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
                    .dimensions(EntityDimensions.changing( 4f, 5.5f)).build());

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

    public static final EntityType<CoelEntity> COEL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "coel"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT, CoelEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

    public static final EntityType<AnkyEntity> ANKY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "anky"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AnkyEntity::new)
                    .dimensions(EntityDimensions.changing( 2.5f, 1.75f)).build());

    public static final EntityType<TrilobiteEntity> TRILOBITE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "trilobite"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TrilobiteEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

    public static final EntityType<CeratoEntity> CERATO = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "cerato"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CeratoEntity::new)
                    .dimensions(EntityDimensions.changing( 2.2f, 2.5f)).build());

    public static final EntityType<PiranhaEntity> PIRANHA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "piranha"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, PiranhaEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

    public static final EntityType<MegalaniaEntity> MEGALANIA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "megalania"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MegalaniaEntity::new)
                    .dimensions(EntityDimensions.changing( 2.5f, 1.75f)).build());

    public static final EntityType<BrontoEntity> BRONTO = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "bronto"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BrontoEntity::new)
                    .dimensions(EntityDimensions.changing( 4f, 7f)).build());

    public static final EntityType<MeganeuraEntity> MEGANEURA = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Dinos.MOD_ID, "meganeura"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MeganeuraEntity::new)
                    .dimensions(EntityDimensions.changing( 1f, 1f)).build());

}

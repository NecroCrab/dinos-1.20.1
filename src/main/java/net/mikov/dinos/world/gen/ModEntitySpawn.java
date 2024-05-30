package net.mikov.dinos.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.custom.*;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawn {
    public static void addEntitySpawn() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.BADLANDS,
                        BiomeKeys.BEACH,
                        BiomeKeys.DESERT,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.DODO, 120, 1, 4);
                SpawnRestriction.register(ModEntities.DODO, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BADLANDS,
                        BiomeKeys.BEACH,
                        BiomeKeys.DESERT,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.TREX, 40, 1, 2);
                SpawnRestriction.register(ModEntities.TREX, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BADLANDS,
                        BiomeKeys.BEACH,
                        BiomeKeys.DESERT,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.FOREST,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.DARK_FOREST,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.COMPY, 90, 3, 6);
        SpawnRestriction.register(ModEntities.COMPY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BADLANDS,
                        BiomeKeys.BEACH,
                        BiomeKeys.DESERT,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.FOREST,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.DARK_FOREST,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.DIMORPH, 70, 1, 3);
        SpawnRestriction.register(ModEntities.DIMORPH, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BEACH,
                        BiomeKeys.SNOWY_BEACH,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.RIVER,
                        BiomeKeys.COLD_OCEAN,
                        BiomeKeys.DEEP_COLD_OCEAN,
                        BiomeKeys.DEEP_FROZEN_OCEAN,
                        BiomeKeys.FROZEN_OCEAN,
                        BiomeKeys.DEEP_OCEAN,
                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                        BiomeKeys.LUKEWARM_OCEAN,
                        BiomeKeys.OCEAN,
                        BiomeKeys.FROZEN_RIVER),
                SpawnGroup.WATER_AMBIENT,
                ModEntities.COEL, 180, 1, 8);
        SpawnRestriction.register(ModEntities.COEL, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                CoelEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BADLANDS,
                        BiomeKeys.DESERT,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.FOREST,
                        BiomeKeys.SWAMP,
                        BiomeKeys.MANGROVE_SWAMP,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.DARK_FOREST,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.RIVER ),
                SpawnGroup.WATER_CREATURE,
                ModEntities.PIRANHA, 150, 2, 6);
        SpawnRestriction.register(ModEntities.PIRANHA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PiranhaEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.BADLANDS,
                        BiomeKeys.SWAMP,
                        BiomeKeys.FOREST,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.BIRCH_FOREST,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.GROVE,
                        BiomeKeys.MEADOW,
                        BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.SUNFLOWER_PLAINS,
                        BiomeKeys.TAIGA,
                        BiomeKeys.CHERRY_GROVE,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.ANKY, 40, 1, 4);
        SpawnRestriction.register(ModEntities.ANKY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BEACH,
                        BiomeKeys.SNOWY_BEACH,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.RIVER,
                        BiomeKeys.COLD_OCEAN,
                        BiomeKeys.DEEP_COLD_OCEAN,
                        BiomeKeys.DEEP_FROZEN_OCEAN,
                        BiomeKeys.FROZEN_OCEAN,
                        BiomeKeys.DEEP_OCEAN,
                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                        BiomeKeys.LUKEWARM_OCEAN,
                        BiomeKeys.OCEAN,
                        BiomeKeys.FROZEN_RIVER),
                SpawnGroup.CREATURE,
                ModEntities.TRILOBITE, 90, 2, 4);
        SpawnRestriction.register(ModEntities.TRILOBITE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BADLANDS,
                        BiomeKeys.BEACH,
                        BiomeKeys.DESERT,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.CERATO, 80, 1, 4);
        SpawnRestriction.register(ModEntities.CERATO, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.SWAMP,
                        BiomeKeys.MANGROVE_SWAMP,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.DARK_FOREST,
                        BiomeKeys.LUSH_CAVES,
                        BiomeKeys.DRIPSTONE_CAVES,
                        BiomeKeys.DEEP_DARK,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.MEGALANIA, 60, 1, 2);
        SpawnRestriction.register(ModEntities.MEGALANIA, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MobEntity::canMobSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.SWAMP,
                        BiomeKeys.MANGROVE_SWAMP,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.DARK_FOREST,
                        BiomeKeys.FOREST,
                        BiomeKeys.CHERRY_GROVE,
                        BiomeKeys.BIRCH_FOREST,
                        BiomeKeys.TAIGA,
                        BiomeKeys.LUSH_CAVES,
                        BiomeKeys.DRIPSTONE_CAVES,
                        BiomeKeys.DEEP_DARK ),
                SpawnGroup.CREATURE,
                ModEntities.MEGANEURA, 80, 2, 5);
        SpawnRestriction.register(ModEntities.MEGANEURA, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.BADLANDS,
                        BiomeKeys.SWAMP,
                        BiomeKeys.FOREST,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.BIRCH_FOREST,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.GROVE,
                        BiomeKeys.MEADOW,
                        BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.SUNFLOWER_PLAINS,
                        BiomeKeys.TAIGA,
                        BiomeKeys.CHERRY_GROVE ),
                SpawnGroup.CREATURE,
                ModEntities.BRONTO, 15, 1, 4);
        SpawnRestriction.register(ModEntities.BRONTO, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.PLAINS,
                        BiomeKeys.BADLANDS,
                        BiomeKeys.SWAMP,
                        BiomeKeys.FOREST,
                        BiomeKeys.ERODED_BADLANDS,
                        BiomeKeys.SAVANNA,
                        BiomeKeys.FLOWER_FOREST,
                        BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.BIRCH_FOREST,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.GROVE,
                        BiomeKeys.MEADOW,
                        BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                        BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.SUNFLOWER_PLAINS,
                        BiomeKeys.TAIGA,
                        BiomeKeys.CHERRY_GROVE,
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.RIVER ),
                SpawnGroup.CREATURE,
                ModEntities.TRIKE, 40, 1, 4);
        SpawnRestriction.register(ModEntities.TRIKE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        /*BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
                        BiomeKeys.BEACH,
                        BiomeKeys.SNOWY_BEACH,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.COLD_OCEAN,
                        BiomeKeys.DEEP_COLD_OCEAN,
                        BiomeKeys.DEEP_FROZEN_OCEAN,
                        BiomeKeys.FROZEN_OCEAN,
                        BiomeKeys.DEEP_OCEAN,
                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                        BiomeKeys.LUKEWARM_OCEAN,
                        BiomeKeys.OCEAN ),
                SpawnGroup.WATER_CREATURE,
                ModEntities.MOSA, 90, 1, 2);
        SpawnRestriction.register(ModEntities.MOSA, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                MosaEntity::canSpawn);*/
    }
}

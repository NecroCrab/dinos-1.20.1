package net.mikov.dinos.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.mikov.dinos.entity.ModEntities;
import net.mikov.dinos.entity.custom.CoelEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
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

        /*BiomeModifications.addSpawn(BiomeSelectors.includeByKey(
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
                AnimalEntity::isValidNaturalSpawn);*/

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
                        BiomeKeys.DEEP_OCEAN,
                        BiomeKeys.DEEP_LUKEWARM_OCEAN,
                        BiomeKeys.LUKEWARM_OCEAN,
                        BiomeKeys.OCEAN,
                        BiomeKeys.FROZEN_RIVER),
                SpawnGroup.WATER_AMBIENT,
                ModEntities.COEL, 190, 1, 8);
        SpawnRestriction.register(ModEntities.COEL, SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                CoelEntity::canSpawn);
    }
}

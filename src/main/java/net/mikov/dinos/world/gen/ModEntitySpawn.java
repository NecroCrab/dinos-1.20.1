package net.mikov.dinos.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.mikov.dinos.entity.ModEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
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
    }
}

package net.mikov.dinos.world.gen;

import net.mikov.dinos.Dinos;

public class ModWorldGen {
    public static void generateWorldGen() {
        Dinos.LOGGER.info("Adding Dinosaurs to world generation");
        ModEntitySpawn.addEntitySpawn();
    }
}

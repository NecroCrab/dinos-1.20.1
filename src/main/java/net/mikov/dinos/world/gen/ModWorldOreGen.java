package net.mikov.dinos.world.gen;

import net.mikov.dinos.Dinos;

public class ModWorldOreGen {
    public static void generateModWorldOres() {
        Dinos.LOGGER.info("Adding Fossils to world generation");
        ModOreGeneration.generateOres();
    }
}

package net.mikov.dinos.entity.client;

import net.mikov.dinos.Dinos;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer TREX =
            new EntityModelLayer(new Identifier(Dinos.MOD_ID, "trex"), "main");

    public static final EntityModelLayer DODO =
            new EntityModelLayer(new Identifier(Dinos.MOD_ID, "dodo"), "main");

    public static final EntityModelLayer COMPY =
            new EntityModelLayer(new Identifier(Dinos.MOD_ID, "compy"), "main");
}

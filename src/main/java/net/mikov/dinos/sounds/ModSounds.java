package net.mikov.dinos.sounds;

import net.mikov.dinos.Dinos;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static SoundEvent TREX_STEP = registerSound("trex_step");
    public static SoundEvent TREX_ATTACK = registerSound("trex_attack");
    public static SoundEvent DODO_AMBIENT = registerSound("dodo_ambient");
    public static SoundEvent ANKY_AMBIENT = registerSound("anky_ambient");
    public static SoundEvent ANKY_DEATH = registerSound("anky_death");
    public static SoundEvent CERATO_ATTACK = registerSound("cerato_attack");
    public static SoundEvent CERATO_HURT = registerSound("cerato_hurt");
    public static SoundEvent CERATO_AMBIENT = registerSound("cerato_ambient");

    static SoundEvent registerSound(String id) {
        SoundEvent sound = SoundEvent.of(new Identifier(Dinos.MOD_ID, id));
        return Registry.register(Registries.SOUND_EVENT, new Identifier(Dinos.MOD_ID, id), sound);
    }

    public static void  registerSounds() {
        Dinos.LOGGER.info("Custom sounds loading...");
    }
}

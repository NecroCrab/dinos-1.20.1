package net.mikov.dinos.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.mikov.dinos.Dinos;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    /*public static final ScreenHandlerType<MountScreenHandler> MOUNT_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Dinos.MOD_ID, "mount"),
                    new ExtendedScreenHandlerType<>(MountScreenHandler::new));*/

    public static void registerScreenHandlers() {
        Dinos.LOGGER.info("Registering Screen Handlers... ");
    }

}

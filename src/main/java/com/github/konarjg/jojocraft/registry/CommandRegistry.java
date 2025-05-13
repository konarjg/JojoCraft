package com.github.konarjg.jojocraft.registry;

import com.github.konarjg.jojocraft.command.CommandSetPower;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandRegistry {
    public static void registerCommands(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetPower());
    }
}

package com.github.konarjg.jojocraft.objectholder;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class JojoKeybinds {
    public static final KeyBinding OPEN_STAND_STATS_GUI = new KeyBinding("key.open_stand_stats_gui", Keyboard.KEY_G, "key.categories.jojocraft");
    public static final KeyBinding SUMMON_STAND = new KeyBinding("key.summon_stand", Keyboard.KEY_V, "key.categories.jojocraft");
    public static final KeyBinding MANUAL_MODE = new KeyBinding("key.stand_manual_mode", Keyboard.KEY_Z, "key.categories.jojocraft");
    public static final KeyBinding FOLLOW_MODE = new KeyBinding("key.stand_follow_mode", Keyboard.KEY_L, "key.categories.jojocraft");
    public static final KeyBinding PUNCH = new KeyBinding("key.stand_punch", Keyboard.KEY_P, "key.categories.jojocraft");
    public static final KeyBinding POWER = new KeyBinding("key.stand_power", Keyboard.KEY_R, "key.categories.jojocraft");

    public static void register() {
        ClientRegistry.registerKeyBinding(OPEN_STAND_STATS_GUI);
        ClientRegistry.registerKeyBinding(SUMMON_STAND);
        ClientRegistry.registerKeyBinding(MANUAL_MODE);
        ClientRegistry.registerKeyBinding(FOLLOW_MODE);
        ClientRegistry.registerKeyBinding(PUNCH);
        ClientRegistry.registerKeyBinding(POWER);
    }
}

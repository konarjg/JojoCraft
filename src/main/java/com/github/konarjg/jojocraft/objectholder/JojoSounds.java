package com.github.konarjg.jojocraft.objectholder;

import com.github.konarjg.jojocraft.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class JojoSounds {
    public static SoundEvent STAR_PLATINUM_TIME_STOP_ACTIVATE = null;
    public static SoundEvent TIME_STOP_DEACTIVATE = null;
    public static SoundEvent STAR_PLATINUM_PUNCH = null;


    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        JojoSounds.STAR_PLATINUM_TIME_STOP_ACTIVATE = JojoSounds.registerSound("star_platinum_time_stop_activate", event);
        JojoSounds.TIME_STOP_DEACTIVATE = JojoSounds.registerSound("time_stop_deactivate", event);
        JojoSounds.STAR_PLATINUM_PUNCH = JojoSounds.registerSound("star_platinum_punch", event);
    }

    public static SoundEvent registerSound(String soundName, RegistryEvent.Register<SoundEvent> event) {
        ResourceLocation location = new ResourceLocation(Tags.MOD_ID, soundName);
        SoundEvent sound = new SoundEvent(location).setRegistryName(location);
        event.getRegistry().register(sound);
        return sound;
    }
}

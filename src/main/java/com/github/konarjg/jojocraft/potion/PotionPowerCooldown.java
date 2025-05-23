package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionPowerCooldown extends Potion {

    public PotionPowerCooldown() {
        super(false, 0xffffff);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "power_cooldown"));
        setPotionName("effect.power_cooldown");
    }
}

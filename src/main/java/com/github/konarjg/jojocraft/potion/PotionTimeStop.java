package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionTimeStop extends Potion {

    public PotionTimeStop() {
        super(false, 0xffffff);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "time_stop"));
        setPotionName("effect.time_stop");
    }
}

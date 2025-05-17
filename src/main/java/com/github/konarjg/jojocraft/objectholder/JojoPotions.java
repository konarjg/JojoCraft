package com.github.konarjg.jojocraft.objectholder;


import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.potion.*;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoPotions {
    public static final Potion VAMPIRE_EFFECT = new PotionVampire();
    public static final Potion PILLARMAN_EFFECT = new PotionPillarman();
    public static final Potion ULTIMATE_LIFE_FORM_EFFECT = new PotionUltimateLifeForm();
    public static final Potion HAMON_EFFECT = new PotionHamon();
    public static final Potion SPIN_EFFECT = new PotionSpin();
    public static final Potion GOLDEN_SPIN_EFFECT = new PotionGoldenSpin();
}

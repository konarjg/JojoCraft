package com.github.konarjg.jojocraft.objectholder;


import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.potion.PotionHamon;
import com.github.konarjg.jojocraft.potion.PotionVampire;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Tags.MOD_ID)
public class JojoPotions {
    public static final Potion VAMPIRE_EFFECT = new PotionVampire();
    public static final Potion HAMON_EFFECT = new PotionHamon();
}

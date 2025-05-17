package com.github.konarjg.jojocraft.potion;

import com.github.konarjg.jojocraft.Tags;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PotionUltimateLifeForm extends Potion {
    public PotionUltimateLifeForm() {
        super(false, 0);
        setRegistryName(new ResourceLocation(Tags.MOD_ID, "ultimate_life_form"));
        setPotionName("effect.ultimate_life_form");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        player.setGameType(GameType.CREATIVE);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);

        if (!(entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) entity;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        GameType defaultGameType = server.getGameType();
        player.setGameType(defaultGameType);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}

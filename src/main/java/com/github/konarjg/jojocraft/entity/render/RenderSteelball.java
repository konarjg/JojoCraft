package com.github.konarjg.jojocraft.entity.render;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntitySteelball;
import com.github.konarjg.jojocraft.entity.model.ModelSteelball;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSteelball extends Render<EntitySteelball> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MOD_ID, "textures/entity/steelball.png");
    private final ModelSteelball model = new ModelSteelball();

    public RenderSteelball(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntitySteelball entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y - 1.1875, z);
        bindTexture(getEntityTexture(entity));
        model.render(entity, 0, 0, 0, 0, 0, 0.0625F);
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySteelball entity) {
        return TEXTURE;
    }

}

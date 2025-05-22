package com.github.konarjg.jojocraft.entity.render;

import com.github.konarjg.jojocraft.Tags;
import com.github.konarjg.jojocraft.entity.EntitySteelball;
import com.github.konarjg.jojocraft.entity.model.ModelBaronZeppeli;
import com.github.konarjg.jojocraft.entity.model.ModelSteelball;
import com.github.konarjg.jojocraft.entity.npc.EntityBaronZeppeli;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderBaronZeppeli extends Render<EntityBaronZeppeli> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Tags.MOD_ID, "textures/entity/baron_zeppeli.png");
    private final ModelBaronZeppeli model = new ModelBaronZeppeli();

    public RenderBaronZeppeli(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityBaronZeppeli entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 1.5, z);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(entityYaw + 90.0f, 0.0f, 1.0f, 0.0f);

        bindTexture(getEntityTexture(entity));
        model.render(entity, 0, 0, 0, 0, 0, 0.0625F);
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBaronZeppeli entity) {
        return TEXTURE;
    }
}

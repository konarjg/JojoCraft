package com.github.konarjg.jojocraft.entity.model;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSteelball extends ModelBase {
    private final ModelRenderer Steelball;

    public ModelSteelball() {
        textureWidth = 32;
        textureHeight = 32;

        Steelball = new ModelRenderer(this);
        Steelball.setRotationPoint(0.0F, 24.0F, 0.0F);
        Steelball.cubeList.add(new ModelBox(Steelball, 0, 15, -2.0F, -1.0F, -2.0F, 4, 1, 4, 0.0F, false));
        Steelball.cubeList.add(new ModelBox(Steelball, 0, 10, -2.0F, -6.0F, -2.0F, 4, 1, 4, 0.0F, false));
        Steelball.cubeList.add(new ModelBox(Steelball, 0, 0, -3.0F, -5.0F, -3.0F, 6, 4, 6, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        Steelball.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
package com.github.konarjg.jojocraft.entity.model;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBaronZeppeli extends ModelBase {
	private final ModelRenderer BaronZeppeli;
	private final ModelRenderer Hat;
	private final ModelRenderer Head;
	private final ModelRenderer Torso;
	private final ModelRenderer Legs;
	private final ModelRenderer Arms;
	private final ModelRenderer LeftArm_r1;
	private final ModelRenderer RightArm_r1;

	public ModelBaronZeppeli() {
		textureWidth = 64;
		textureHeight = 64;

		BaronZeppeli = new ModelRenderer(this);
		BaronZeppeli.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(BaronZeppeli, 0.0F, -1.5708F, 0.0F);
		

		Hat = new ModelRenderer(this);
		Hat.setRotationPoint(0.0F, 0.0F, 0.0F);
		BaronZeppeli.addChild(Hat);
		Hat.cubeList.add(new ModelBox(Hat, 0, 25, -4.5F, -30.0F, -4.5F, 9, 2, 9, 0.0F, false));
		Hat.cubeList.add(new ModelBox(Hat, 25, 13, -2.5F, -35.0F, -2.5F, 5, 5, 5, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		BaronZeppeli.addChild(Head);
		Head.cubeList.add(new ModelBox(Head, 25, 0, -3.0F, -28.0F, -3.0F, 6, 6, 6, 0.0F, false));

		Torso = new ModelRenderer(this);
		Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		BaronZeppeli.addChild(Torso);
		Torso.cubeList.add(new ModelBox(Torso, 0, 0, -2.0F, -22.0F, -4.0F, 4, 16, 8, 0.0F, false));

		Legs = new ModelRenderer(this);
		Legs.setRotationPoint(0.0F, 0.0F, 0.0F);
		BaronZeppeli.addChild(Legs);
		Legs.cubeList.add(new ModelBox(Legs, 22, 37, -1.0F, -6.0F, -4.0F, 2, 6, 3, 0.0F, false));
		Legs.cubeList.add(new ModelBox(Legs, 37, 24, -1.0F, -6.0F, 1.0F, 2, 6, 3, 0.0F, false));

		Arms = new ModelRenderer(this);
		Arms.setRotationPoint(0.0F, 0.0F, 0.0F);
		BaronZeppeli.addChild(Arms);
		

		LeftArm_r1 = new ModelRenderer(this);
		LeftArm_r1.setRotationPoint(0.0F, 25.0F, -9.0F);
		Arms.addChild(LeftArm_r1);
		setRotationAngle(LeftArm_r1, 0.7854F, 0.0F, 0.0F);
		LeftArm_r1.cubeList.add(new ModelBox(LeftArm_r1, 11, 37, -1.0F, -24.0F, 39.0F, 2, 8, 3, 0.0F, false));

		RightArm_r1 = new ModelRenderer(this);
		RightArm_r1.setRotationPoint(0.0F, 0.0F, -16.0F);
		Arms.addChild(RightArm_r1);
		setRotationAngle(RightArm_r1, -0.7854F, 0.0F, 0.0F);
		RightArm_r1.cubeList.add(new ModelBox(RightArm_r1, 0, 37, -1.0F, -24.0F, -7.0F, 2, 8, 3, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		BaronZeppeli.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
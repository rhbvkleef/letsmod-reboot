package tk.yteditors.requiredstuffz.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class OvenModel extends ModelBase {

	ModelRenderer base;
	ModelRenderer sideLeft;
	ModelRenderer sideRight;
	ModelRenderer backBottom;
	ModelRenderer topCornerLeft;
	ModelRenderer topCornerRight;
	ModelRenderer top;
	ModelRenderer plateau;
	ModelRenderer pizza;
	ModelRenderer chimney0;
	ModelRenderer chimney1;
	ModelRenderer chimney2;
	ModelRenderer chimney3;
	private boolean renderPizza = false;

	public OvenModel() {

		textureWidth = 128;
		textureHeight = 128;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(0F, 0F, 0F, 14, 1, 16);
		base.setRotationPoint(-7F, 23F, -8F);
		base.setTextureSize(128, 128);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		sideLeft = new ModelRenderer(this, 0, 46);
		sideLeft.addBox(0F, 0F, 0F, 1, 5, 16);
		sideLeft.setRotationPoint(6F, 18F, -8F);
		sideLeft.setTextureSize(128, 128);
		sideLeft.mirror = true;
		setRotation(sideLeft, 0F, 0F, 0F);
		sideRight = new ModelRenderer(this, 35, 46);
		sideRight.addBox(0F, 0F, 0F, 1, 5, 16);
		sideRight.setRotationPoint(-7F, 18F, -8F);
		sideRight.setTextureSize(128, 128);
		sideRight.mirror = true;
		setRotation(sideRight, 0F, 0F, 0F);
		backBottom = new ModelRenderer(this, 32, 120);
		backBottom.addBox(0F, 0F, 0F, 12, 7, 1);
		backBottom.setRotationPoint(-6F, 16F, -8F);
		backBottom.setTextureSize(128, 128);
		backBottom.mirror = true;
		setRotation(backBottom, 0F, 0F, 0F);
		topCornerLeft = new ModelRenderer(this, 0, 68);
		topCornerLeft.addBox(0F, 0F, 0F, 1, 2, 15);
		topCornerLeft.setRotationPoint(5F, 16F, -7F);
		topCornerLeft.setTextureSize(128, 128);
		topCornerLeft.mirror = true;
		setRotation(topCornerLeft, 0F, 0F, 0F);
		topCornerRight = new ModelRenderer(this, 33, 68);
		topCornerRight.addBox(0F, 0F, 0F, 1, 2, 15);
		topCornerRight.setRotationPoint(-6F, 16F, -7F);
		topCornerRight.setTextureSize(128, 128);
		topCornerRight.mirror = true;
		setRotation(topCornerRight, 0F, 0F, 0F);
		top = new ModelRenderer(this, 7, 86);
		top.addBox(0F, 0F, 0F, 10, 1, 16);
		top.setRotationPoint(-5F, 15F, -8F);
		top.setTextureSize(128, 128);
		top.mirror = true;
		setRotation(top, 0F, 0F, 0F);
		plateau = new ModelRenderer(this, 7, 104);
		plateau.addBox(0F, 0F, 0F, 12, 1, 14);
		plateau.setRotationPoint(-6F, 19F, -7F);
		plateau.setTextureSize(128, 128);
		plateau.mirror = true;
		setRotation(plateau, 0F, 0F, 0F);
		pizza = new ModelRenderer(this, 7, 121);
		pizza.addBox(0F, 0F, 0F, 6, 1, 6);
		pizza.setRotationPoint(-3F, 18F, -2F);
		pizza.setTextureSize(128, 128);
		pizza.mirror = true;
		setRotation(pizza, 0F, 0F, 0F);
		chimney0 = new ModelRenderer(this, 0, 119);
		chimney0.addBox(0F, 0F, 0F, 2, 8, 1);
		chimney0.setRotationPoint(-1F, 7F, 1F);
		chimney0.setTextureSize(128, 128);
		chimney0.mirror = true;
		setRotation(chimney0, 0F, 0F, 0F);
		chimney1 = new ModelRenderer(this, 0, 108);
		chimney1.addBox(0F, 0F, 0F, 1, 8, 2);
		chimney1.setRotationPoint(-1F, 7F, -1F);
		chimney1.setTextureSize(128, 128);
		chimney1.mirror = true;
		setRotation(chimney1, 0F, 0F, 0F);
		chimney2 = new ModelRenderer(this, 0, 97);
		chimney2.addBox(0F, 0F, 0F, 1, 8, 2);
		chimney2.setRotationPoint(1F, 7F, 0F);
		chimney2.setTextureSize(128, 128);
		chimney2.mirror = true;
		setRotation(chimney2, 0F, 0F, 0F);
		chimney3 = new ModelRenderer(this, 0, 87);
		chimney3.addBox(0F, 0F, 0F, 2, 8, 1);
		chimney3.setRotationPoint(0F, 7F, -1F);
		chimney3.setTextureSize(128, 128);
		chimney3.mirror = true;
		setRotation(chimney3, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		base.render(f5);
		sideLeft.render(f5);
		sideRight.render(f5);
		backBottom.render(f5);
		topCornerLeft.render(f5);
		topCornerRight.render(f5);
		top.render(f5);
		plateau.render(f5);
		if (renderPizza) pizza.render(f5);
		chimney0.render(f5);
		chimney1.render(f5);
		chimney2.render(f5);
		chimney3.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {

		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {

		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void setRenderPizza(boolean renderPizza) {

		this.renderPizza = renderPizza;
	}

}

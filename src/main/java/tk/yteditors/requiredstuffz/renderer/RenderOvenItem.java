package tk.yteditors.requiredstuffz.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tk.yteditors.requiredstuffz.models.OvenModel;
import tk.yteditors.requiredstuffz.util.LogHelper;

public class RenderOvenItem implements IItemRenderer {
	
	private OvenModel	model;
	
	public RenderOvenItem(OvenModel model) {
		this.model = model;
		LogHelper.debug(model.getClass().getName());
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();
		GL11.glScalef(1f, 1f, 1f);
		GL11.glTranslatef(0, 0, 0);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TileEntityOvenRenderer.resourceloc);
		model.render(null, 0, 0, 0, 0, 0, 0.0625f);

		GL11.glPopMatrix();
	}
	
}

package tk.yteditors.requiredstuffz.renderer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import tk.yteditors.requiredstuffz.models.OvenModel;

public class RenderOvenItem implements IItemRenderer {
	
	private OvenModel	model;
	
	public RenderOvenItem(OvenModel model) {
		this.model = model;
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
		
	}
	
}

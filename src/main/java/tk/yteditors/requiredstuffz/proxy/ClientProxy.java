package tk.yteditors.requiredstuffz.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import tk.yteditors.requiredstuffz.RequiredStuffz;
import tk.yteditors.requiredstuffz.models.OvenModel;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.renderer.RenderOvenItem;
import tk.yteditors.requiredstuffz.renderer.TileEntityOvenRenderer;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		OvenModel ovenModel = new OvenModel();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer(ovenModel));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RequiredStuffz.blockOvenOff), new RenderOvenItem(ovenModel));
	}
	
	@Override
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityOven.class, ModInfo.MOD_ID + ":TileEntityOven");
	}
	
}

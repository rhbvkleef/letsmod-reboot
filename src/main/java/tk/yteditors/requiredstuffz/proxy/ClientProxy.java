package tk.yteditors.requiredstuffz.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import tk.yteditors.requiredstuffz.block.Block;
import tk.yteditors.requiredstuffz.models.OvenModel;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.renderer.RenderOvenItem;
import tk.yteditors.requiredstuffz.renderer.TileEntityOvenRenderer;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers() {
		OvenModel ovenModel = new OvenModel();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Block.blockOvenOff), new RenderOvenItem(ovenModel));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Block.blockOvenOn), new RenderOvenItem(ovenModel));
	}
	
	@Override
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityOven.class, ModInfo.MOD_ID + ":TileEntityOven");
	}
	
}

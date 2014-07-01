package tk.yteditors.requiredstuffz.proxy;

import net.minecraft.tileentity.TileEntity;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.renderer.TileEntityOvenRenderer;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer());
	}
	
	@Override
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityOven.class, ModInfo.MOD_ID + ":TileEntityOven");
	}
	
}

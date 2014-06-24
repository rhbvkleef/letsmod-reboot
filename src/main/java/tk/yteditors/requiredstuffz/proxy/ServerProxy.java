package tk.yteditors.requiredstuffz.proxy;

import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileentity.TileEntityOven;
import cpw.mods.fml.common.registry.GameRegistry;

public class ServerProxy {

	public void registerRenderers(){
		
	}
	
	public void registerTileEntities(){
		GameRegistry.registerTileEntity(TileEntityOven.class, ModInfo.modId + "TileEntityOven");
	}
}

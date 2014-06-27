package tk.yteditors.requiredstuffz.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileentity.TileEntityOven;
import cpw.mods.fml.common.registry.GameRegistry;

public class ServerProxy extends CommonProxy {
	
	public void registerRenderers() {
		
	}
	
	public void registerTileEntities() {
		Logger.getLogger("requiredstuffz").log(Level.INFO,
				"Registering tile entities");
		System.out.println("Registering tile entities");
		GameRegistry.registerTileEntity(TileEntityOven.class, ModInfo.modId
				+ "TileEntityOven");
	}
}

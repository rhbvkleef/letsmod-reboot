package tk.yteditors.requiredstuffz.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.tileentity.TileEntity;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;
import cpw.mods.fml.common.registry.GameRegistry;

public class ServerProxy extends CommonProxy {
	
	public void registerRenderers() {
		
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityOven.class, ModInfo.MOD_ID + ":TileEntityOven");
	}
}

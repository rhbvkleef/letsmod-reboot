package tk.yteditors.requiredstuffz;

import net.minecraft.block.Block;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileentity.TileEntityOven;
import tk.yteditors.requiredstuffz.util.RegisterHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=ModInfo.modId, name="Required Stuffz", version="1.7.2-A-1.0")
public class RequiredStuffz {
	
	public static Block oven;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
		RegisterHelper.registerBlock(new BlockOven());
		GameRegistry.registerTileEntity(TileEntityOven.class, "TileEntityOven");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e){
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e){
		
	}
	
}

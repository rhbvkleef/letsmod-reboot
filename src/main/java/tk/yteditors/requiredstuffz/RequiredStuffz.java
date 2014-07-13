package tk.yteditors.requiredstuffz;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import tk.yteditors.requiredstuffz.block.Block;
import tk.yteditors.requiredstuffz.config.ConfigHandler;
import tk.yteditors.requiredstuffz.creativeTabs.MainTab;
import tk.yteditors.requiredstuffz.item.Item;
import tk.yteditors.requiredstuffz.proxy.IProxy;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.util.LogHelper;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, guiFactory = ModInfo.GUI_FACTORY_CLASS)
public class RequiredStuffz {
	
	@Instance(value = ModInfo.MOD_ID)
	public static RequiredStuffz	instance;
	
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static IProxy			proxy;
	
	public static CreativeTabs		mainTab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
		ConfigHandler.init(e.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());

		Block.registerBlocks();
		Item.registerItems();

		LogHelper.info("Pre-initialization complete!");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.registerRenderers();
		proxy.registerTileEntities();
		
		mainTab = new MainTab();
		Block.blockOvenOff.setCreativeTab(mainTab);
		Item.itemUnbakedPizza.setCreativeTab(mainTab);
		Item.itemBakedPizza.setCreativeTab(mainTab);

		GameRegistry.addRecipe(new ItemStack(Item.itemUnbakedPizza), "xxx", "x x", "xxx", 'x', new ItemStack(Blocks.hay_block));
		
		LogHelper.info("Initialization complete!");
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		LogHelper.info("Post-initialization complete!");
	}
	
}

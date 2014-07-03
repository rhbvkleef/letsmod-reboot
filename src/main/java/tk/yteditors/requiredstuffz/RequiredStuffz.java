package tk.yteditors.requiredstuffz;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.config.ConfigHandler;
import tk.yteditors.requiredstuffz.creativeTabs.MainTab;
import tk.yteditors.requiredstuffz.item.ItemBakedPizza;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;
import tk.yteditors.requiredstuffz.proxy.IProxy;
import tk.yteditors.requiredstuffz.reference.BlockNames;
import tk.yteditors.requiredstuffz.reference.ItemNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.renderer.TileEntityOvenRenderer;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;
import tk.yteditors.requiredstuffz.util.LogHelper;
import tk.yteditors.requiredstuffz.util.RegisterHelper;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION, guiFactory = ModInfo.GUI_FACTORY_CLASS)
public class RequiredStuffz {
	
	@Instance(value = ModInfo.MOD_ID)
	public static RequiredStuffz	instance;
	
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static IProxy			proxy;
	
	public static Block				blockOvenOff;
	public static Block				blockOvenOn;
	
	public static Item				itemUnbakedPizza;
	public static Item				itemBakedPizza;
	
	public static CreativeTabs		mainTab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
		ConfigHandler.init(e.getSuggestedConfigurationFile());
		
		blockOvenOff = new BlockOven(false).setBlockName(BlockNames.blockOven + "Off");
		blockOvenOn = new BlockOven(true).setBlockName(BlockNames.blockOven + "On");
		
		RegisterHelper.registerBlock(blockOvenOff);
		RegisterHelper.registerBlock(blockOvenOn);
		
		itemUnbakedPizza = new ItemUnbakedPizza().setUnlocalizedName(ItemNames.itemUnbakedPizza);
		itemBakedPizza = new ItemBakedPizza().setUnlocalizedName(ItemNames.itemBakedPizza);
		
		RegisterHelper.registerItem(itemUnbakedPizza);
		RegisterHelper.registerItem(itemBakedPizza);
		
		LogHelper.info("Preinitialization complete!");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.registerRenderers();
		proxy.registerTileEntities();
		
		mainTab = new MainTab();
		blockOvenOff.setCreativeTab(mainTab);
		itemUnbakedPizza.setCreativeTab(mainTab);
		itemBakedPizza.setCreativeTab(mainTab);
		
		GameRegistry.addRecipe(new ItemStack(itemUnbakedPizza), "xxx", "x x", "xxx", 'x', new ItemStack(Blocks.hay_block));
		
		LogHelper.info("Initialization complete!");
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		LogHelper.info("Postinitialization complete!");
	}
	
}

package tk.yteditors.requiredstuffz;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.creativeTabs.MainTab;
import tk.yteditors.requiredstuffz.item.ItemBakedPizza;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;
import tk.yteditors.requiredstuffz.proxy.IProxy;
import tk.yteditors.requiredstuffz.proxy.ServerProxy;
import tk.yteditors.requiredstuffz.reference.BlockNames;
import tk.yteditors.requiredstuffz.reference.ItemNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileentity.TileEntityOven;
import tk.yteditors.requiredstuffz.util.RegisterHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=ModInfo.modId, name="Required Stuffz", version="1.7.2-A-1.0")
public class RequiredStuffz {
	
	@Instance(value = ModInfo.modId)
	public static RequiredStuffz instance;
	
	@SidedProxy(clientSide = "tk.yteditors.requiredstuffz.proxy.ClientProxy",
				serverSide = "tk.yteditors.requiredstuffz.proxy.ServerProxy")
	public static IProxy proxy;
	
	public static Block blockOvenOff;
	public static Block blockOvenOn;
	
	public static Item itemUnbakedPizza;
	public static Item itemBakedPizza;
	
	public static CreativeTabs mainTab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
		
		blockOvenOff = new BlockOven(false).setBlockName(BlockNames.blockOven + "Off");
		blockOvenOn = new BlockOven(true).setBlockName(BlockNames.blockOven + "On");
		
		RegisterHelper.registerBlock(blockOvenOff);
		RegisterHelper.registerBlock(blockOvenOn);
		
		itemUnbakedPizza = new ItemUnbakedPizza().setUnlocalizedName(ItemNames.itemUnbakedPizza);
		itemBakedPizza = new ItemBakedPizza().setUnlocalizedName(ItemNames.itemBakedPizza);
		
		RegisterHelper.registerItem(itemUnbakedPizza);
		RegisterHelper.registerItem(itemBakedPizza);
		
		GameRegistry.addRecipe(new ItemStack(Items.diamond), "xxx",
															 "x x",
															 "xxx",
															 'x', new ItemStack(Blocks.dirt));
		
		mainTab = new MainTab();
		blockOvenOff.setCreativeTab(mainTab);
		itemUnbakedPizza.setCreativeTab(mainTab);
		itemBakedPizza.setCreativeTab(mainTab);
		
		proxy.registerTileEntities();
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e){
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e){
		
	}
	
}

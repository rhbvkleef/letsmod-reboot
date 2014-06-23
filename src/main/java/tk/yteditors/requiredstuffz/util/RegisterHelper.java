package tk.yteditors.requiredstuffz.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import cpw.mods.fml.common.registry.GameRegistry;

public class RegisterHelper {
	public static void registerBlock(Block block){
		GameRegistry.registerBlock(block, ModInfo.modId + "_" + block.getUnlocalizedName().substring(5));
	}
	
	public static void registerItem(Item item){
		GameRegistry.registerItem(item, ModInfo.modId + "_" +item.getUnlocalizedName().substring(5));
	}
}

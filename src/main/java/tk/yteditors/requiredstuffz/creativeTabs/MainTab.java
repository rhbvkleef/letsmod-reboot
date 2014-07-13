package tk.yteditors.requiredstuffz.creativeTabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tk.yteditors.requiredstuffz.block.Block;

public class MainTab extends CreativeTabs {
	
	public MainTab() {
		super("Required stuffz");
	}
	
	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Block.blockOvenOn);
	}
	
}

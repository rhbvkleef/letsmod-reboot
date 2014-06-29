package tk.yteditors.requiredstuffz.item;

import tk.yteditors.requiredstuffz.reference.Food;
import tk.yteditors.requiredstuffz.reference.ItemNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemBakedPizza extends ItemFood {
	
	public ItemBakedPizza() {
		super(Food.BAKED_PIZZA_HUNGER, Food.BAKED_PIZZA_SATURATION, false);
		setUnlocalizedName(ItemNames.itemBakedPizza);
		setMaxStackSize(1);
		setTextureName(ModInfo.MOD_ID + ":" + getUnlocalizedName().substring(5));
	}
	
}

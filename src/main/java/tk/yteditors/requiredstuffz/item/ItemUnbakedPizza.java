package tk.yteditors.requiredstuffz.item;

import tk.yteditors.requiredstuffz.reference.Food;
import tk.yteditors.requiredstuffz.reference.ItemNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemUnbakedPizza extends ItemFood {
	public ItemUnbakedPizza() {
		super(Food.UNBAKED_PIZZA_HUNGER, Food.UNBAKED_PIZZA_SATURATION, false);
		setUnlocalizedName(ItemNames.itemUnbakedPizza);
		setMaxStackSize(1);
		setTextureName(ModInfo.MOD_ID + ":" + getUnlocalizedName().substring(5));
	}
}

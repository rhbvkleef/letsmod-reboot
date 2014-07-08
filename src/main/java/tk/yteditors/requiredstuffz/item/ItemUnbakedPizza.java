package tk.yteditors.requiredstuffz.item;

import tk.yteditors.requiredstuffz.reference.Food;
import tk.yteditors.requiredstuffz.reference.ItemNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemUnbakedPizza extends RSItemFood {
	public ItemUnbakedPizza() {
		super(Food.UNBAKED_PIZZA_HUNGER, Food.UNBAKED_PIZZA_SATURATION);
		setUnlocalizedName(ItemNames.itemUnbakedPizza);
	}
}

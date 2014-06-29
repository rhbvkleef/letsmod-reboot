package tk.yteditors.requiredstuffz.config;

import java.io.File;

import tk.yteditors.requiredstuffz.reference.Food;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import static tk.yteditors.requiredstuffz.reference.ConfigSettings.*;

public class ConfigHandler {
	
	public static void init(File file) {
		Configuration config = new Configuration(file);
		
		try {
			config.load();
			
			Food.UNBAKED_PIZZA_HUNGER = config.get(CATEGORY_FOOD_NAME, "Unbaked_pizza_hunger", 2, "How much hunger an unbaked pizza restores").getInt(2);
			Food.UNBAKED_PIZZA_SATURATION = (float) config.get(CATEGORY_FOOD_NAME, "Unbaked_pizza_saturation", 0.5f, "How much saturation an unbaked pizza restores").getDouble(0.5f);
			
			Food.BAKED_PIZZA_HUNGER = config.get(CATEGORY_FOOD_NAME, "Baked_pizza_hunger", 10, "How much hunger a baked pizza restores").getInt(10);
			Food.BAKED_PIZZA_SATURATION = (float) config.get(CATEGORY_FOOD_NAME, "Baked_pizza_saturation", 4.0f, "How much saturation a baked pizza restores").getDouble(4.0f);
			
		} catch (Exception e) {
			//ModInfo.modLogger.error(e.toString());
		} finally {
			config.save();
		}
		
	}
}

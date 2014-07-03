package tk.yteditors.requiredstuffz.config;

import static tk.yteditors.requiredstuffz.reference.ConfigSettings.CATEGORY_FOOD_NAME;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import tk.yteditors.requiredstuffz.reference.Food;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	
	public static Configuration	config = null;
	
	public static void init(File file) {
		if(config == null){
			config = new Configuration(file);
		}
		
	}
	
	@SubscribeEvent
	public void onConfigurastionChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.modID.equalsIgnoreCase(ModInfo.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	public void loadConfiguration() {
		try {
			config.load();
			
			Food.UNBAKED_PIZZA_HUNGER = config.get(CATEGORY_FOOD_NAME, "Unbaked_pizza_hunger", 2, "How much hunger an unbaked pizza restores").getInt(2);
			Food.UNBAKED_PIZZA_SATURATION = (float) config.get(CATEGORY_FOOD_NAME, "Unbaked_pizza_saturation", 0.5f, "How much saturation an unbaked pizza restores").getDouble(0.5f);
			
			Food.BAKED_PIZZA_HUNGER = config.get(CATEGORY_FOOD_NAME, "Baked_pizza_hunger", 10, "How much hunger a baked pizza restores").getInt(10);
			Food.BAKED_PIZZA_SATURATION = (float) config.get(CATEGORY_FOOD_NAME, "Baked_pizza_saturation", 4.0f, "How much saturation a baked pizza restores").getDouble(4.0f);
			
		} catch (Exception e) {
			// Log exception
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
}

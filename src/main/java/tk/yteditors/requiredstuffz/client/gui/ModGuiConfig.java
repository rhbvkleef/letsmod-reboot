package tk.yteditors.requiredstuffz.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import tk.yteditors.requiredstuffz.config.ConfigHandler;
import tk.yteditors.requiredstuffz.reference.ConfigSettings;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class ModGuiConfig extends GuiConfig {
	
	static List<IConfigElement>	list = null;
	
	static {
		addToConfigGui(new ConfigElement(ConfigHandler.config.getCategory(ConfigSettings.CATEGORY_FOOD_NAME)).getChildElements());
		addToConfigGui(new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
	}
	
	public ModGuiConfig(GuiScreen guiScreen) {
		this(guiScreen, list, ModInfo.MOD_ID, false, false, ConfigSettings.CONFIG_MAIN_GUI_NAME, false);
	}
	
	private ModGuiConfig(GuiScreen guiScreen, List<IConfigElement> configElements, String modId, boolean modifyOnFly, boolean requireWorldRestart,
			String guiName, boolean foo) {
		super(guiScreen, configElements, modId, modifyOnFly, requireWorldRestart, guiName);
	}
	
	private static void addToConfigGui(List<IConfigElement> configElements) {
		
		if(list == null){
			list = new ArrayList<IConfigElement>();
		}
		
		for (int i = 0; i < configElements.size(); i++) {
			list.add(configElements.get(i));
		}
	}
}

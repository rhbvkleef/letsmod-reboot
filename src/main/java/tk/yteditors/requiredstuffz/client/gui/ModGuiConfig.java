package tk.yteditors.requiredstuffz.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import tk.yteditors.requiredstuffz.config.ConfigHandler;
import tk.yteditors.requiredstuffz.reference.ConfigSettings;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig {
	public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen,
				new ConfigElement(ConfigHandler.config.getCategory(ConfigSettings.CATEGORY_FOOD_NAME)).getChildElements(),
				ModInfo.MOD_ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
	}
}

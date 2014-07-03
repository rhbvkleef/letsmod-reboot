package tk.yteditors.requiredstuffz.client.gui;

import java.util.Set;

import tk.yteditors.requiredstuffz.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory {
	
	// UNUSED
	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}
	
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ModGuiConfig.class;
	}
	
	// UNUSED
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}
	
	// UNUSED
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
	
}

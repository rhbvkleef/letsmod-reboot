package tk.yteditors.requiredstuffz.compat;

import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.compat.waila.BlockOvenHandler;
import tk.yteditors.requiredstuffz.util.LogHelper;

/**
 * Created by Rolf on 10/30/2014.
 */
public class Waila implements IThirdPartyCompat {

	public static void register(IWailaRegistrar registrar) {

		registrar.registerBodyProvider(new BlockOvenHandler(), BlockOven.class);
	}

	@Override
	public void preInit() {

	}

	@Override
	public void init() {

		LogHelper.info("Registering waila with function: " + getClass().getName() + ".register");
		FMLInterModComms.sendMessage("Waila", "register", getClass().getName() + ".register");
	}

	@Override
	public void postInit() {

	}
}

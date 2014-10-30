package tk.yteditors.requiredstuffz.compat;

import mcp.mobius.waila.api.IWailaRegistrar;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.compat.waila.BlockOvenHandler;

/**
 * Created by Rolf on 10/30/2014.
 */
public class Waila {

	public static void register(IWailaRegistrar registrar) {

		registrar.registerBodyProvider(new BlockOvenHandler(), BlockOven.class);
	}
}

package tk.yteditors.requiredstuffz.block;

import tk.yteditors.requiredstuffz.reference.BlockNames;
import tk.yteditors.requiredstuffz.util.RegisterHelper;

/**
 * Created by Rolf on 7/13/2014.
 */
public class Block {

	public static net.minecraft.block.Block blockOvenOff;
	public static net.minecraft.block.Block blockOvenOn;

	public static void registerBlocks() {
		blockOvenOff = new BlockOven(false).setBlockName(BlockNames.blockOven + "Off");
		blockOvenOn = new BlockOven(true).setBlockName(BlockNames.blockOven + "On");

		RegisterHelper.registerBlock(blockOvenOff);
		RegisterHelper.registerBlock(blockOvenOn);
	}
}

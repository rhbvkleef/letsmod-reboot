package tk.yteditors.requiredstuffz.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import tk.yteditors.requiredstuffz.block.Block;
import tk.yteditors.requiredstuffz.item.ItemBakedPizza;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;

import java.util.List;

public class BlockOvenHandler implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {

		return accessor.getBlock().equals(Block.blockOvenOff) ? new ItemStack(Item.getItemFromBlock(Block.blockOvenOff)) : new ItemStack(Item.getItemFromBlock(Block.blockOvenOn));
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		TileEntity te = accessor.getTileEntity();
		if (te != null && te instanceof TileEntityOven && ((TileEntityOven) te).getHasItemInSlot(TileEntityOven.SLOT_PIZZA)) {
			int progress = 0;
			if (((TileEntityOven) te).itemStacks[TileEntityOven.SLOT_PIZZA].getItem() instanceof ItemBakedPizza) {
				progress = 100;
			} else {
				progress = ((TileEntityOven) te).itemBurnTime / 2;
			}
			currenttip.add("Progress: " + progress + "%");
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		return currenttip;
	}
}

package tk.yteditors.requiredstuffz.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOven extends TileEntity{
	private boolean burning = false;
	private ItemStack[] itemStacks = new ItemStack[2];
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound){
		super.writeToNBT(nbtCompound);
		nbtCompound.setBoolean("burning", burning);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound){
		super.readFromNBT(nbtCompound);
		burning = nbtCompound.getBoolean("burning");
	}
}

package tk.yteditors.requiredstuffz.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public class TileEntityOven extends TileEntity implements ISidedInventory {
	private int					maxBurnTime		= 0;
	private int					currentBurnTime	= 0;
	private int					itemBurnTime	= 0;
	private ItemStack[]			itemStacks		= new ItemStack[2];
	
	private static final int[]	slotsTop		= new int[] { 0 };
	private static final int[]	slotsBottom		= new int[] { 1 };
	private static final int[]	slotsSides		= new int[] { 1 };
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);
		
		nbtCompound.setInteger("maxBurnTime", maxBurnTime);
		nbtCompound.setInteger("currentBurnTime", currentBurnTime);
		nbtCompound.setInteger("itemBurnTime", itemBurnTime);
		
		NBTTagList nbttaglist = new NBTTagList();
		
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (this.itemStacks[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("slot", (byte) i);
				itemStacks[i].writeToNBT(tag);
				nbttaglist.appendTag(tag);
			}
		}
		
		nbtCompound.setTag("Items", nbttaglist);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		super.readFromNBT(nbtCompound);
		NBTTagList nbttaglist = nbtCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		itemStacks = new ItemStack[getSizeInventory()];
		
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte slot = nbttagcompound1.getByte("slot");
			
			if (slot >= 0 && slot < itemStacks.length) {
				itemStacks[slot] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		maxBurnTime = nbtCompound.getInteger("maxBurnTime");
		currentBurnTime = nbtCompound.getInteger("currentBurnTime");
		itemBurnTime = nbtCompound.getInteger("itemBurnTime");
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
	}
	
	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return itemStacks[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count) {
		if (this.itemStacks[slot] != null) {
			ItemStack itemstack;
			
			if (this.itemStacks[slot].stackSize <= count) {
				itemstack = itemStacks[slot];
				itemStacks[slot] = null;
				return itemstack;
			} else {
				itemstack = this.itemStacks[slot].splitStack(count);
				
				if (itemStacks[slot].stackSize == 0) {
					itemStacks[slot] = null;
				}
				
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (itemStacks[slot] != null) {
			ItemStack itemstack = itemStacks[slot];
			itemStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack parItemStack) {
		this.itemStacks[slot] = parItemStack;
		
		if (parItemStack != null && parItemStack.stackSize > this.getInventoryStackLimit()) {
			parItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName() {
		return "tile.blockOven.name";
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D,
				this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void closeInventory() {}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (slot == 1) {
			return isItemFuel(item);
		} else {
			return isItemUnbakedPizza(item);
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return side != 0 || slot != 1 || item.getItem() == Items.bucket;
	}
	
	public static boolean isItemFuel(ItemStack item) {
		return getItemBurnTime(item) > 0;
	}
	
	public static int getItemBurnTime(ItemStack item) {
		if (item == null) {
			return 0;
		} else {
			Item currentItem = item.getItem();
			
			if (currentItem instanceof ItemBlock && Block.getBlockFromItem(currentItem) != Blocks.air) {
				Block block = Block.getBlockFromItem(currentItem);
				
				if (block == Blocks.wooden_slab) {
					return 150;
				}
				
				if (block.getMaterial() == Material.wood) {
					return 300;
				}
				
				if (block == Blocks.coal_block) {
					return 16000;
				}
			}
			
			if (currentItem instanceof ItemTool && ((ItemTool) currentItem).getToolMaterialName().equals("WOOD"))
				return 200;
			if (currentItem instanceof ItemSword && ((ItemSword) currentItem).getToolMaterialName().equals("WOOD"))
				return 200;
			if (currentItem instanceof ItemHoe && ((ItemHoe) currentItem).getToolMaterialName().equals("WOOD"))
				return 200;
			if (currentItem == Items.stick)
				return 100;
			if (currentItem == Items.coal)
				return 1600;
			if (currentItem == Items.lava_bucket)
				return 20000;
			if (currentItem == Item.getItemFromBlock(Blocks.sapling))
				return 100;
			if (currentItem == Items.blaze_rod)
				return 2400;
			return GameRegistry.getFuelValue(item);
		}
	}
	
	public static boolean isItemUnbakedPizza(ItemStack item) {
		return true;
	}
	
	public boolean addItem(Item item) {
		if (item instanceof ItemUnbakedPizza) {
			if (itemStacks[0] != null) {
				itemStacks[0] = new ItemStack(item, 1);
				return true;
			}
		} else if (isItemFuel(new ItemStack(item, 1))) {
			if (itemStacks[1] != null) {
				itemStacks[1] = new ItemStack(item, 1);
				return true;
			}
		}
		return false;
	}
	
	public boolean getHasItemInSlot(int slot) {
		return itemStacks[slot] == null ? false : (itemStacks[slot].stackSize == 0 ? false : true);
	}
	
	public boolean insertPizza(ItemStack item) {
		if (itemStacks[0] == null || itemStacks[0].stackSize == 0) {
			itemStacks[0] = item;
			return true;
		} else {
			return false;
		}
	}
	
	public ItemStack removePizza() {
		if (itemStacks[0] != null) {
			ItemStack itemStack = itemStacks[0];
			itemStacks[0] = null;
			return itemStack;
		} else {
			return null;
		}
	}
	
	public boolean insertFuel(ItemStack item) {
		if (itemStacks[1] == null || itemStacks[1].stackSize == 0) {
			itemStacks[1] = item;
			return true;
		} else {
			return false;
		}
	}
}

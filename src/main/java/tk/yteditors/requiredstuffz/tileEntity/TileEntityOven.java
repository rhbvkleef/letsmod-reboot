package tk.yteditors.requiredstuffz.tileEntity;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.item.Item;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;

import java.util.Random;

public class TileEntityOven extends TileEntity implements ISidedInventory {
	public static final int SLOT_PIZZA = 0;
	private static final int[] slotsTop = new int[]{SLOT_PIZZA};
	public static final int SLOT_FUEL = 1;
	private static final int[] slotsBottom = new int[]{SLOT_FUEL};
	private static final int[] slotsSides = new int[]{SLOT_PIZZA, SLOT_FUEL};
	public boolean burning = false;
	private int					maxBurnTime		= -2;
	private int					itemBurnTime	= 0;
	private ItemStack[]			itemStacks		= new ItemStack[2];

	public static boolean isItemFuel(ItemStack item) {
		return getItemBurnTime(item) > 0;
	}

	public static int getItemBurnTime(ItemStack item) {
		if (item == null) {
			return 0;
		} else {
			net.minecraft.item.Item currentItem = item.getItem();

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
			if (currentItem == net.minecraft.item.Item.getItemFromBlock(Blocks.sapling))
				return 100;
			if (currentItem == Items.blaze_rod)
				return 2400;
			return GameRegistry.getFuelValue(item);
		}
	}

	public static boolean isItemUnbakedPizza(ItemStack item) {
		return item.getItem() instanceof ItemUnbakedPizza;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		super.writeToNBT(nbtCompound);
		nbtCompound.setInteger("maxBurnTime", maxBurnTime);
		nbtCompound.setInteger("itemBurnTime", itemBurnTime);
		nbtCompound.setBoolean("burning", burning);

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
		itemBurnTime = nbtCompound.getInteger("itemBurnTime");
		burning = nbtCompound.getBoolean("burning");
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();

		if (getHasItemInSlot(SLOT_FUEL)) {
			maxBurnTime += getItemBurnTime(itemStacks[SLOT_FUEL]);
			if (itemStacks[SLOT_FUEL].getItem() instanceof ItemBucket) {
				Random random = new Random();
				float rx = random.nextFloat() * 0.8F + 0.1F;
				float ry = random.nextFloat() * 0.8F + 1.1F;
				float rz = random.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(worldObj, xCoord + rx, yCoord + ry, zCoord + rz, new ItemStack(Items.bucket, 1));

				float factor = 0.05F;
				entityItem.motionX = random.nextGaussian() * factor;
				entityItem.motionY = random.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = random.nextGaussian() * factor;
				worldObj.spawnEntityInWorld(entityItem);
			}
			itemStacks[SLOT_FUEL] = null;
		}

		if (maxBurnTime >= -1 && ((getHasItemInSlot(SLOT_PIZZA) && itemStacks[SLOT_PIZZA].getItem() instanceof ItemUnbakedPizza) || burning)) {
			--maxBurnTime;

			if (!(getHasItemInSlot(SLOT_PIZZA) && itemStacks[SLOT_PIZZA].getItem() instanceof ItemUnbakedPizza)) {
				return;
			}

			itemBurnTime++;

			if (itemBurnTime >= 200) {
				itemBurnTime = 0;
				itemStacks[SLOT_PIZZA] = new ItemStack(Item.itemBakedPizza);
				// Set pizza to baked
			}

			burning = true;
			BlockOven.updateOvenBlockState(true, worldObj, xCoord, yCoord, zCoord);
		} else if (burning && maxBurnTime < -1) {

			itemBurnTime = 0;
			burning = false;

			// Disable burning

		} else if (burning) {
			--maxBurnTime;
		}
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
	public void openInventory() {
	}
	
	@Override
	public void closeInventory() {
	}
	
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
		return side != 0 || slot != SLOT_FUEL;
	}

	public boolean addItem(net.minecraft.item.Item item) {
		if (item instanceof ItemUnbakedPizza) {
			if (itemStacks[SLOT_PIZZA] != null) {
				itemStacks[SLOT_PIZZA] = new ItemStack(item, 1);
				return true;
			}
		} else if (isItemFuel(new ItemStack(item, 1))) {
			if (itemStacks[SLOT_FUEL] != null) {
				itemStacks[SLOT_FUEL] = new ItemStack(item, 1);
				return true;
			}
		}
		return false;
	}
	
	public boolean getHasItemInSlot(int slot) {
		return itemStacks[slot] == null ? false : (itemStacks[slot].stackSize == 0 ? false : true);
	}
	
	public boolean insertPizza(ItemStack item) {
		if (itemStacks[SLOT_PIZZA] == null || itemStacks[SLOT_PIZZA].stackSize == 0) {
			itemStacks[SLOT_PIZZA] = item;
			return true;
		} else {
			return false;
		}
	}
	
	public ItemStack removePizza() {
		if (itemStacks[SLOT_PIZZA] != null) {
			ItemStack itemStack = itemStacks[SLOT_PIZZA];
			itemStacks[SLOT_PIZZA] = null;
			itemBurnTime = 0;
			return itemStack;
		} else {
			return null;
		}
	}
	
	public boolean insertFuel(ItemStack item) {
		if (!getHasItemInSlot(SLOT_FUEL)) {
			itemStacks[SLOT_FUEL] = item;
			return true;
		} else {
			return false;
		}
	}
}

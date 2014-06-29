package tk.yteditors.requiredstuffz.tileentity;

import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import tk.yteditors.requiredstuffz.RequiredStuffz;
import tk.yteditors.requiredstuffz.block.BlockOven;
import tk.yteditors.requiredstuffz.item.ItemBakedPizza;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;
import static tk.yteditors.requiredstuffz.util.OvenMetaHelpers.*;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityOven extends TileEntity implements ISidedInventory {
	private int					maxBurnTime		= 0;
	private int					itemBurnTime	= 0;
	private boolean				burning			= false;
	private ItemStack[]			itemStacks		= new ItemStack[2];
	
	private static final int	slotPizza		= 0;
	private static final int	slotFuel		= 1;
	
	private static final int[]	slotsTop		= new int[] { slotPizza };
	private static final int[]	slotsBottom		= new int[] { slotFuel };
	private static final int[]	slotsSides		= new int[] { slotPizza, slotFuel };
	
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
	public boolean canUpdate(){
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote){
			compareMetaToTile();
		}else worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		
		if (getHasItemInSlot(slotFuel)) {
			maxBurnTime += getItemBurnTime(itemStacks[slotFuel]);
			if (itemStacks[slotFuel].getItem() instanceof ItemBucket) {
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
			itemStacks[slotFuel] = null;
		}
		
		if (maxBurnTime >= -1 && ((getHasItemInSlot(slotPizza) && itemStacks[slotPizza].getItem() instanceof ItemUnbakedPizza) || burning)) {
			--maxBurnTime;
			
			if (!(getHasItemInSlot(slotPizza) && itemStacks[slotPizza].getItem() instanceof ItemUnbakedPizza)) {
				return;
			}
			
			itemBurnTime++;
			
			if (itemBurnTime >= 200) {
				itemBurnTime = 0;
				itemStacks[slotPizza] = new ItemStack(RequiredStuffz.itemBakedPizza);
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getMetadata(true, true, getMetaDirection(worldObj.getBlockMetadata(
						xCoord, yCoord, zCoord))), 2);
			}
			
			burning = true;
			BlockOven.updateOvenBlockState(true, worldObj, xCoord, yCoord, zCoord);
		} else if (burning && maxBurnTime < -1) {
			
			itemBurnTime = 0;
			burning = false;
			
			boolean hasItem = getHasItemInSlot(slotPizza);
			boolean baked = false;
			if (hasItem && itemStacks[slotPizza].getItem() instanceof ItemBakedPizza)
				baked = true;
			int metadata = getMetadata(hasItem, baked, getMetaDirection(worldObj.getBlockMetadata(xCoord, yCoord, zCoord)));
			
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);
			BlockOven.updateOvenBlockState(false, worldObj, xCoord, yCoord, zCoord);
			
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
		return side != 0 || slot != slotFuel;
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
		return item.getItem() instanceof ItemUnbakedPizza;
	}
	
	public boolean addItem(Item item) {
		if (item instanceof ItemUnbakedPizza) {
			if (itemStacks[slotPizza] != null) {
				itemStacks[slotPizza] = new ItemStack(item, 1);
				return true;
			}
		} else if (isItemFuel(new ItemStack(item, 1))) {
			if (itemStacks[slotFuel] != null) {
				itemStacks[slotFuel] = new ItemStack(item, 1);
				return true;
			}
		}
		return false;
	}
	
	public boolean getHasItemInSlot(int slot) {
		return itemStacks[slot] == null ? false : (itemStacks[slot].stackSize == 0 ? false : true);
	}
	
	public boolean insertPizza(ItemStack item) {
		if (itemStacks[slotPizza] == null || itemStacks[slotPizza].stackSize == 0) {
			itemStacks[slotPizza] = item;
			return true;
		} else {
			return false;
		}
	}
	
	public ItemStack removePizza() {
		if (itemStacks[slotPizza] != null) {
			ItemStack itemStack = itemStacks[slotPizza];
			itemStacks[slotPizza] = null;
			itemBurnTime = 0;
			return itemStack;
		} else {
			return null;
		}
	}
	
	public boolean insertFuel(ItemStack item) {
		if (!getHasItemInSlot(slotFuel)) {
			itemStacks[slotFuel] = item;
			return true;
		} else {
			return false;
		}
	}
	
	public void compareMetaToTile(){
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		boolean hasPizza = getHasItemInSlot(slotPizza);
		boolean isPizzaBurned = hasPizza ? (itemStacks[slotPizza].getItem() instanceof ItemBakedPizza ? true : false) : false;
		int intendedMetadata = getMetadata(hasPizza, isPizzaBurned, getMetaDirection(metadata));
		
		if(metadata != intendedMetadata){
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, intendedMetadata, 2);
		}
	}
}

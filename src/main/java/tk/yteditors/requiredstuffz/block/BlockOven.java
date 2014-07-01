package tk.yteditors.requiredstuffz.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tk.yteditors.requiredstuffz.RequiredStuffz;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;
import tk.yteditors.requiredstuffz.reference.BlockNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOven extends BlockContainer {
	
	// Cheatsheet shortcut :)
	// BlockFurnace
	
	@SideOnly(Side.CLIENT)
	private IIcon			blockIconFrontEmpty, blockIconFrontUnbaked, blockIconFrontBaked, blockIconTop, blockIconSide;
	
	int						rotation;
	
	public final boolean	burning;
	
	public static boolean	breaking;
	
	public BlockOven(boolean burning) {
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(2f);
		setResistance(3.5f);
		setHarvestLevel("pickaxe", 0);
		this.burning = burning;
		this.setBlockBounds(0f, 0f, 0f, 1f, 1.1f, 1f);
		
		if (burning) {
			this.setLightLevel(0.857f);
		}
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		if (burning) {
			blockIconFrontUnbaked = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_front_on_unbaked");
			blockIconFrontBaked = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_front_on_baked");
			blockIconFrontEmpty = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_front_on_empty");
		} else {
			blockIconFrontUnbaked = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_front_off_unbaked");
			blockIconFrontBaked = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_front_off_baked");
			blockIconFrontEmpty = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_front_off_empty");
		}
		
		blockIconTop = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_top");
		blockIconSide = register.registerIcon(ModInfo.MOD_ID + ":" + BlockNames.blockOven + "_side");
	}
	
	/**
	 * Set block orientation according to player's face
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {
		if(entityliving == null){
			return;
		}
		
		TileEntityOven tileEntity = (TileEntityOven) world.getTileEntity(x, y, z);
		tileEntity.direction = MathHelper.floor_double((double) (entityliving.rotationYaw * 4f / 360f) + .5D) & 3;
		world.markBlockForUpdate(x, y, z);
	}
	
	public Item getItemDropped(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		if (breaking) {
			breaking = false;
			return;
		}
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}
	
	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		
		IInventory inventory = (IInventory) tileEntity;
		
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);
			
			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item
						.getItemDamage()));
				
				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				
				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(RequiredStuffz.blockOvenOff);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		
		if (burning) {
			int direction = ((TileEntityOven) world.getTileEntity(x, y, z)).direction;
			float f = x + 0.5F;
			float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float f2 = z + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;
			
			if (direction == 4) {
				world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			} else if (direction == 5) {
				world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			} else if (direction == 2) {
				world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
			} else if (direction == 3) {
				world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
			}
		}
		
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityOven();
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityOven();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par1, float par2, float par3) {
		
		TileEntityOven tileEntity = (TileEntityOven) world.getTileEntity(x, y, z);
		int direction = tileEntity.direction;
		ItemStack playerItem = player.getCurrentEquippedItem();
		
		if (tileEntity == null || player.isSneaking() || !tileEntity.isUseableByPlayer(player)) {
			return false;
		}
		
		if (world.isRemote) {
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		
		if (playerItem != null && playerItem.stackSize > 0 && side == direction) {
			
			// Player clicked on front side of furnace with an item
			if (player.getHeldItem().getItem() instanceof ItemUnbakedPizza) {
				boolean success = tileEntity.insertPizza(player.getHeldItem());
				
				if (success) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					// Add item
				}
			} else if (TileEntityOven.isItemFuel(player.getHeldItem())) {
				boolean success = tileEntity.insertFuel(new ItemStack(player.getHeldItem().getItem(), 1));
				
				if (success) {
					ItemStack current = player.inventory.getStackInSlot(player.inventory.currentItem);
					if (current.stackSize == 1) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					} else {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(current.getItem(),
								current.stackSize - 1));
					}
				}
			}
		} else if (side == direction && (playerItem == null || playerItem.stackSize == 0)) {
			
			// Player clicked on furnace without an item
			if (tileEntity.getHasItemInSlot(0)) {
				
				player.inventory.setInventorySlotContents(player.inventory.currentItem, tileEntity.removePizza());
				// Remove item
			}
		}
		
		return true;
	}
	
	public static void updateOvenBlockState(boolean makeBurning, World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		
		breaking = true;
		
		if (makeBurning) {
			world.setBlock(x, y, z, RequiredStuffz.blockOvenOn);
		} else {
			world.setBlock(x, y, z, RequiredStuffz.blockOvenOff);
		}
		
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
		
		breaking = false;
		
		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}
	
}

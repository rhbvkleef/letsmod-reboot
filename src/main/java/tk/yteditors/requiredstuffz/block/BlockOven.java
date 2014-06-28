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
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tk.yteditors.requiredstuffz.RequiredStuffz;
import tk.yteditors.requiredstuffz.item.ItemUnbakedPizza;
import tk.yteditors.requiredstuffz.reference.BlockNames;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileentity.TileEntityOven;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOven extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon			blockIconFrontEmpty, blockIconFrontUnbaked,
			blockIconFrontBaked, blockIconTop,
			blockIconSide;
	
	int						rotation;
	
	public final boolean	burning;
	
	public BlockOven(boolean burning) {
		super(Material.rock);
		setStepSound(Block.soundTypeStone);
		setHardness(2f);
		setResistance(3.5f);
		setHarvestLevel("pickaxe", 0);
		this.burning = burning;
		
		if (burning) {
			this.setLightLevel(0.857f);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		if(burning){
			blockIconFrontUnbaked = register.registerIcon(ModInfo.modId + ":"
					+ BlockNames.blockOven + "_front_on_unbaked");
			blockIconFrontBaked = register.registerIcon(ModInfo.modId + ":"
					+ BlockNames.blockOven + "_front_on_baked");
			blockIconFrontEmpty = register.registerIcon(ModInfo.modId + ":"
					+ BlockNames.blockOven + "_front_on_empty");
		}else{
			blockIconFrontUnbaked = register.registerIcon(ModInfo.modId + ":"
					+ BlockNames.blockOven + "_front_off_unbaked");
			blockIconFrontBaked = register.registerIcon(ModInfo.modId + ":"
					+ BlockNames.blockOven + "_front_off_baked");
			blockIconFrontEmpty = register.registerIcon(ModInfo.modId + ":"
					+ BlockNames.blockOven + "_front_off_empty");
		}
		
		blockIconTop = register.registerIcon(ModInfo.modId + ":"
				+ BlockNames.blockOven + "_top");
		blockIconSide = register.registerIcon(ModInfo.modId + ":"
				+ BlockNames.blockOven + "_side");
	}
	
	/**
	 * Return icons
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		int sideMeta = getDirection(metadata);
		boolean hasItem = getHasItem(metadata);
		boolean isItemBaked = getIsItemBurned(metadata);
		
		if (metadata == 0 && side == 3) {
			return blockIconFrontEmpty;
		} else if (side == 0 || side == 1) {
			return blockIconTop;
		} else if (sideMeta == side) {
			return hasItem ? (isItemBaked ? blockIconFrontBaked
					: blockIconFrontUnbaked) : blockIconFrontEmpty;
		} else {
			return blockIconSide;
		}
	}
	
	/**
	 * Set block orientation according to player's face
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityliving, ItemStack itemStack) {
		byte direction = 0;
		int facing = MathHelper
				.floor_double((entityliving.rotationYaw * 4F) / 360F + 0.5D) & 3;
		
		if (facing == 0) {
			direction = 2;
		}
		if (facing == 1) {
			direction = 5;
		}
		if (facing == 2) {
			direction = 3;
		}
		if (facing == 3) {
			direction = 4;
		}
		
		setMetadata(world, x, y, z, getMetadata(false, false, direction - 2));
	}
	
	public Item getItemDropped(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);
		dropItems(world, x, y, z);
	}
	
	private void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if (!(tileEntity instanceof IInventory)) {
			System.out.println("Tile entity not instanceof IInventory");
			return;
		}
		
		IInventory inventory = (IInventory) tileEntity;
		
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);
			
			System.out.println(item.getDisplayName());
			
			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z
						+ rz, new ItemStack(item.getItem(), item.stackSize,
						item.getItemDamage()));
				
				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound(
							(NBTTagCompound) item.getTagCompound().copy());
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
	
	public static void updateBlockState(boolean burning, World world, int x,
			int y, int z) {
		// TODO auto generated method stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z,
			Random random) {
		
		if (burning) {
			int l = getDirection(world.getBlockMetadata(x, y, z));
			float f = x + 0.5F;
			float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
			float f2 = z + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F - 0.3F;
			
			if (l == 4) {
				world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D,
						0.0D);
			} else if (l == 5) {
				world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D,
						0.0D);
			} else if (l == 2) {
				world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D,
						0.0D);
			} else if (l == 3) {
				world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D,
						0.0D);
				world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D,
						0.0D);
			}
		}
		
	}
	
	@Override
	public boolean hasTileEntity(){
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
	
	public boolean getHasItem(int metadata) {
		return metadata < 4 ? false : true;
	}
	
	public boolean getIsItemBurned(int metadata) {
		return metadata > 7 ? true : false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float par1, float par2, float par3) {
		TileEntityOven tileEntity = (TileEntityOven) world.getTileEntity(x, y,
				z);
		int metadata = world.getBlockMetadata(x, y, z);
		int direction = getDirection(metadata);
		
		ItemStack playerItem = player.getCurrentEquippedItem();
		
		if (tileEntity == null || player.isSneaking()
				|| !tileEntity.isUseableByPlayer(player))
			return false;
		
		if (playerItem != null && side == direction) {
			if (player.getHeldItem().getItem() instanceof ItemUnbakedPizza) {
				if (world.isRemote) {
					world.markBlockForUpdate(x, y, z);
				} else {
					boolean success = tileEntity.insertPizza(player
							.getHeldItem());
					
					if (success) {
						player.inventory.setInventorySlotContents(
								player.inventory.currentItem, null);
						setMetadata(world, x, y, z, getMetadata(true, false,
								getMetaDirection(metadata)));
					}
				}
			} else if (TileEntityOven.isItemFuel(player.getHeldItem())) {
				if (world.isRemote) {
					
					world.markBlockForUpdate(x, y, z);
					
				} else {
					boolean success = tileEntity.insertFuel(player
							.getHeldItem());
					
					if (success) {
						ItemStack current = player.inventory
								.getStackInSlot(player.inventory.currentItem);
						if (current.stackSize == 1) {
							player.inventory.setInventorySlotContents(
									player.inventory.currentItem, null);
						} else {
							player.inventory.setInventorySlotContents(
									player.inventory.currentItem,
									new ItemStack(current.getItem(),
											current.stackSize - 1));
						}
					}
				}
			}
		} else if (side == direction && playerItem == null) {
			if (tileEntity.getHasPizza()) {
				player.inventory.setInventorySlotContents(
						player.inventory.currentItem, tileEntity.removePizza());
				setMetadata(world, x, y, z, getMetadata(false, false,
						getMetaDirection(metadata)));
			}
		} else if (side == 1) {
			if (world.isRemote) {
				world.markBlockForUpdate(x, y, z);
			} else {
				player.addChatMessage(IChatComponent.Serializer
						.func_150699_a("This feature is not yet implemented!"));
			}
		}
		
		return true;
	}
	
	public int getDirection(int metadata) {
		return getMetaDirection(metadata) + 2;
	}
	
	public int getMetaDirection(int metadata) {
		switch (metadata) {
			case 0:
			case 4:
			case 8:
			case 12:
				return 0;
			case 1:
			case 5:
			case 9:
			case 13:
				return 1;
			case 2:
			case 6:
			case 10:
			case 14:
				return 2;
			case 3:
			case 7:
			case 11:
			case 15:
				return 3;
			default:
				return 3;
		}
	}
	
	public int getMetadata(boolean hasItem, boolean isItemBaked, int direction) {
		return hasItem ? (!isItemBaked ? direction + 4 : direction + 8)
				: direction;
	}
	
	public void setMetadata(World world, int x, int y, int z, int metadata) {
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}
	
}

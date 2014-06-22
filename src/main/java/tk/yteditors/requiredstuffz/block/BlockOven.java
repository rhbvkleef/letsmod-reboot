package tk.yteditors.requiredstuffz.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileentity.TileEntityOven;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOven extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon	blockIconFrontOffEmpty,
					blockIconFrontOnEmpty,
					blockIconFrontOffFilled,
					blockIconFrontOnFilled,
					blockIconTop, 
					blockIconSide;

	int rotation;

	public BlockOven() {
		super(Material.rock);
		setBlockName("blockOven");
		setCreativeTab(CreativeTabs.tabBlock);
		setStepSound(this.soundTypeStone);
		setHardness(2f);
		setResistance(3.5f);
		setHarvestLevel("pickaxe", 0);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIconFrontOffEmpty = register.registerIcon(ModInfo.modId + ":" + getUnlocalizedName().substring(5) + "_front_off_empty");
		blockIconFrontOnEmpty = register.registerIcon(ModInfo.modId + ":" + getUnlocalizedName().substring(5) + "_front_on_empty");
		blockIconFrontOffFilled = register.registerIcon(ModInfo.modId + ":" + getUnlocalizedName().substring(5) + "_front_off_filled");
		blockIconFrontOnFilled = register.registerIcon(ModInfo.modId + ":" + getUnlocalizedName().substring(5) + "_front_on_filled");
		blockIconTop = register.registerIcon(ModInfo.modId + ":" + getUnlocalizedName().substring(5) + "_top");
		blockIconSide = register.registerIcon(ModInfo.modId + ":" + getUnlocalizedName().substring(5) + "_side");
	}
	
	/**
	 * Return icons
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(metadata == 0 && side == 3){
			return getFront();
		} else if(side == 0 || side == 1) {
			return blockIconTop;
		} else if (metadata == side) {
			return getFront();
		} else{
			return blockIconSide;
		}
	}
	
	public IIcon getFront(){
		return blockIconFrontOffEmpty;
	}
	
	/**
	 * Set block orientation according to player's face
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {
		byte direction = 0;
		int facing = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		
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
		
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
	}
	
	/**
     * Gets an item for the block being called on.
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z){
        return Item.getItemFromBlock(this);
    }
    
    public TileEntity createTileEntity(World world, int metadata){
    	return new TileEntityOven();
    }
	
}

package tk.yteditors.requiredstuffz.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class RSBlockContainer extends BlockContainer {
	
	public RSBlockContainer(Material material) {
		super(material);
	}
}

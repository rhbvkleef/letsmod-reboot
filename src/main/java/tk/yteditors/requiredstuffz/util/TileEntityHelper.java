package tk.yteditors.requiredstuffz.util;

import net.minecraft.tileentity.TileEntity;

public class TileEntityHelper {

	public static void markTileEntityForUpdate(TileEntity tileEntity) {

		tileEntity.getWorldObj().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		tileEntity.markDirty();
	}
}

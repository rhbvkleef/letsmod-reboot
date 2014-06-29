package tk.yteditors.requiredstuffz.util;

import net.minecraft.world.World;

public class OvenMetaHelpers {
	public static int getDirection(int metadata) {
		return getMetaDirection(metadata) + 2;
	}
	
	public static int getMetaDirection(int metadata) {
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
	
	public static int getMetadata(boolean hasItem, boolean isItemBaked, int direction) {
		return (hasItem ? (!isItemBaked ? direction + 4 : direction + 8) : direction) + 4;
	}
	
	public static void setMetadata(World world, int x, int y, int z, int metadata) {
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}
	
	public static boolean getHasItem(int metadata) {
		return metadata < 8 ? false : true;
	}
	
	public static boolean getIsItemBurned(int metadata) {
		return metadata > 11 ? true : false;
	}
}

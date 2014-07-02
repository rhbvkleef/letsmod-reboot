package tk.yteditors.requiredstuffz.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tk.yteditors.requiredstuffz.models.OvenModel;
import tk.yteditors.requiredstuffz.reference.ModInfo;
import tk.yteditors.requiredstuffz.tileEntity.TileEntityOven;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityOvenRenderer extends TileEntitySpecialRenderer {
	
	public final OvenModel					model;
	private static final ResourceLocation	resourceloc	= new ResourceLocation(ModInfo.MOD_ID + ":" + "textures/tileentity/computer.png");
	
	public TileEntityOvenRenderer(OvenModel model) {
		this.model = model;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		if (te == null)
			return;
		TileEntityOven tileEntity = (TileEntityOven) te;
		int direction = te.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord); 
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + .5f, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(resourceloc);
		GL11.glPushMatrix();
		
		if(direction == 0 || direction == 2) GL11.glRotatef(direction * (-90), 0.0F, direction * (-90), 1f);
		if(direction == 1 || direction == 3) GL11.glRotatef(direction * (90), 0.0F, direction * (-90), 1f);
		
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
}

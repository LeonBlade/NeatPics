package me.leonblade.neatpics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

public class EntityNeatPic extends EntityHanging implements IEntityAdditionalSpawnData
{
	public String textureUrl;
	public int imageWidth;
	public int imageHeight;

	public EntityNeatPic(World world) 
	{
		super(world);
		this.textureUrl = "";
		this.imageWidth = 1;
		this.imageHeight = 1;
	}	
	
	public EntityNeatPic(World world, int x, int y, int z, int j, final String surl, int width, int height) 
	{
		super(world, x, y, z, j);
		this.textureUrl = surl;
		this.imageWidth = width;
		this.imageHeight = height;
		this.setDirection(j);
		this.downloadTexture();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) 
	{
		// do nothing to prevent tracking updates
	}
	
	public void openGui()
	{
		FMLClientHandler.instance().getClient().thePlayer.openGui(ModNeatPic.instance, 1, this.worldObj, this.xPosition, this.yPosition, this.zPosition);
	}
	
	public void downloadTexture()
	{
		// only download the texture if you are a client
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			FMLClientHandler.instance().getClient().renderEngine.obtainImageData(this.textureUrl, null);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) 
	{
		super.writeEntityToNBT(tag);
		tag.setString("URL", this.textureUrl);
		tag.setInteger("width", this.imageWidth);
		tag.setInteger("height", this.imageHeight);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) 
	{
		super.readEntityFromNBT(tag);
		this.textureUrl = tag.getString("URL");
		this.imageWidth = tag.getInteger("width");
		this.imageHeight = tag.getInteger("height");
		this.downloadTexture();
		this.setDirection(this.hangingDirection);
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) 
	{
		data.writeInt(this.xPosition);
		data.writeInt(this.yPosition);
		data.writeInt(this.zPosition);
		data.writeInt(this.hangingDirection);
		data.writeUTF(this.textureUrl);
		data.writeInt(this.imageWidth);
		data.writeInt(this.imageHeight);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) 
	{
		this.xPosition = data.readInt();
		this.yPosition = data.readInt();
		this.zPosition = data.readInt();
		this.hangingDirection = data.readInt();
		this.textureUrl = data.readUTF();
		this.imageWidth = data.readInt();
		this.imageHeight = data.readInt();
		this.downloadTexture();
		this.setDirection(this.hangingDirection);
	}	

	@Override
	public int func_82329_d() 
	{
		return this.imageWidth * 16;
	}

	@Override
	public int func_82330_g() 
	{
		return this.imageHeight * 16;
	}

	@Override
	public void dropItemStack() 
	{
		this.entityDropItem(new ItemStack(ModNeatPic.neatPic), 0.0F);
	}

	public boolean interact(EntityPlayer player) 
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			// set the reference for this entity
			GuiHandler.neatPicReference = this;
		}
		
		// open the gui
		player.openGui(ModNeatPic.instance, 1, player.worldObj, this.xPosition, this.yPosition, this.zPosition);
				
		return true;
	}
	
}

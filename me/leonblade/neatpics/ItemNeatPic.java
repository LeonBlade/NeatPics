package me.leonblade.neatpics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemNeatPic extends ItemHangingEntity 
{

	public ItemNeatPic(int id) 
	{
		super(id, EntityNeatPic.class);
		setIconIndex(0);
		setMaxStackSize(64);
		setItemName("neatPic");
		LanguageRegistry.addName(this, "Neat Pic!");
	}
	
	@Override
	public String getTextureFile() 
	{
		return CommonProxy.ITEMS_PNG;
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int direction, float par8, float par9, float par10) 
	{
		if (direction == 0)
        {
            return false;
        }
        else if (direction == 1)
        {
            return false;
        }
        else
        {
        	// get the direction
            int newDirection = Direction.vineGrowth[direction];
            // store in the gui handler
            GuiHandler.direction = newDirection;
            
            // open GUI
            entityPlayer.openGui(ModNeatPic.instance, 1, world, x, y, z);
            
            return false;
        }
	}

}

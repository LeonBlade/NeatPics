package me.leonblade.neatpics;

import java.net.MalformedURLException;
import java.net.URL;

import me.leonblade.neatpics.client.GuiNeatPic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler 
{
	public static int direction;
	public static EntityNeatPic neatPicReference = null;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return new GuiNeatPic(player, world, x, y, z);
	}
	
}

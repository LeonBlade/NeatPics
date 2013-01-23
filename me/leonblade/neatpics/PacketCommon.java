package me.leonblade.neatpics;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public abstract class PacketCommon 
{
	// static packet constant
	public static String PACKET_CHANNEL = "NeatPics";
	public static int PACKET_ENTITY_CREATE = 0;
	public static int PACKET_ENTITY_UPDATE = 1;
	
	// packet 250 custom
	protected Packet250CustomPayload packet = null;
	
	public PacketCommon() 
	{
		this.packet = new Packet250CustomPayload();
		this.packet.channel = PACKET_CHANNEL;
	}
	
	public Packet250CustomPayload getPacket()
	{
		return this.packet;
	}
	
	protected static EntityNeatPic getNeatPicByID(int entityId, int dimension)
	{
		Entity target;
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			target = FMLClientHandler.instance().getClient().theWorld.getEntityByID(entityId);
			if (target != null && target instanceof EntityNeatPic)
			{
				return (EntityNeatPic) target;
			}
		}
		else if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			return (EntityNeatPic) MinecraftServer.getServer().worldServerForDimension(dimension).getEntityByID(entityId);
		}
		
		return null;
	}
	
	protected static EntityPlayer getPlayer(String name, int dimension)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			return FMLClientHandler.instance().getClient().theWorld.getPlayerEntityByName(name);
		}
		else if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			return MinecraftServer.getServer().worldServerForDimension(dimension).getPlayerEntityByName(name);
		}
		
		return null;
	}
}

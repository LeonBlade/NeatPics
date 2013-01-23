package me.leonblade.neatpics.client;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import me.leonblade.neatpics.EntityNeatPic;
import me.leonblade.neatpics.GuiHandler;
import me.leonblade.neatpics.PacketCommon;

public class PacketNeatPicUpdate extends PacketCommon 
{
	public PacketNeatPicUpdate(EntityNeatPic neatPic) 
	{
		super();
	}
	
	public static Packet250CustomPayload createPacket(int entityId, String url, int width, int height)
	{
		// create a byte array output stream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// create a data output stream to write to
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
		
		try
		{
			// write data to the stream
			dataOutputStream.writeInt(PACKET_ENTITY_UPDATE);
			dataOutputStream.writeInt(entityId);
			dataOutputStream.writeUTF(url);
			dataOutputStream.writeInt(width);
			dataOutputStream.writeInt(height);
		}
		catch (Exception e) {}
		
		// create custom packet
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = PACKET_CHANNEL;
		packet.data = byteArrayOutputStream.toByteArray();
		packet.length = byteArrayOutputStream.size();
		
		return packet;
	}
	
	public static void handleData(DataInputStream data, Player player)
	{
		try
		{
			// cast the player to entity player mp
			EntityPlayer entityPlayer = (EntityPlayer) player;
			
			// read data from packet
			int entityId = data.readInt();
			String textureUrl = data.readUTF();
			int width = data.readInt();
			int height = data.readInt();
			
			// get the entity
			EntityNeatPic neatPic = getNeatPicByID(entityId, entityPlayer.worldObj.provider.dimensionId);

			// set the image width and height and url
			neatPic.textureUrl = textureUrl;
			// if it's too big it will just destroy itself anyways
			neatPic.imageWidth = width;
			neatPic.imageHeight = height;
			
			// set the direction to update stuff
			neatPic.setDirection(neatPic.hangingDirection);
			neatPic.downloadTexture();
			
			// only do this if we are in server
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			{
				PacketDispatcher.sendPacketToAllInDimension(createPacket(entityId, textureUrl, width, height), neatPic.worldObj.provider.dimensionId);
			}
		}
		catch (IOException e) {}
	}
}

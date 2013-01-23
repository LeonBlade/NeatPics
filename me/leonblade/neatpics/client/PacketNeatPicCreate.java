package me.leonblade.neatpics.client;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import me.leonblade.neatpics.EntityNeatPic;
import me.leonblade.neatpics.GuiHandler;
import me.leonblade.neatpics.PacketCommon;

public class PacketNeatPicCreate extends PacketCommon 
{
	public PacketNeatPicCreate(EntityNeatPic neatPic) 
	{
		super();
	}
	
	public static Packet250CustomPayload createPacket(int x, int y, int z, int direction, String url, int width, int height)
	{
		// create a byte array output stream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// create a data output stream to write to
		DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
		
		try
		{
			// write data to the stream
			dataOutputStream.writeInt(PACKET_ENTITY_CREATE);
			dataOutputStream.writeInt(x);
			dataOutputStream.writeInt(y);
			dataOutputStream.writeInt(z);
			dataOutputStream.writeInt(direction);
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
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			try
			{
				// cast the player to entity player mp
				EntityPlayerMP entityPlayer = (EntityPlayerMP) player;
				// get the server world
				World world = MinecraftServer.getServer().worldServerForDimension(entityPlayer.worldObj.provider.dimensionId);
				
				// read data from packet
				int x = data.readInt();
				int y = data.readInt();
				int z = data.readInt();
				int direction = data.readInt();
				String textureUrl = data.readUTF();
				int width = data.readInt();
				int height = data.readInt();
				
				// get the current item stack from the player
				ItemStack itemStack = entityPlayer.inventory.getItemStack();
				
				// create an instance of neat pic
				EntityNeatPic neatPic = new EntityNeatPic(world, x, y, z, direction, textureUrl, width, height);
				
				// can the player edit with this here?
				if (!entityPlayer.canPlayerEdit(x, y, z, direction, itemStack))
		        {
		        	return;
		        }
				else
		        {
					// 
					// if the neat pic isn't null and on a valid surface
		            if (neatPic != null && neatPic.onValidSurface())
		            {
		            	// make sure the world is on the server
		                if (!world.isRemote)
		                {
		                	world.spawnEntityInWorld(neatPic);
		                }

		                // decrease the stack size by one
		                if (!entityPlayer.capabilities.isCreativeMode)
		                	entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
		            }
		        }
			}
			catch (IOException e) {}
		}
	}
}

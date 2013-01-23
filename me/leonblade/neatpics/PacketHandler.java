package me.leonblade.neatpics;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import me.leonblade.neatpics.client.PacketNeatPicCreate;
import me.leonblade.neatpics.client.PacketNeatPicUpdate;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler 
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		// create data input stream with packet data
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		// try to read the packet id
		try
		{
			// get packet id
			int packetId = data.readInt();
			
			// switch over the packet data
			if (packetId == PacketCommon.PACKET_ENTITY_CREATE)
			{
				PacketNeatPicCreate.handleData(data, player);
			}
			else if (packetId == PacketCommon.PACKET_ENTITY_UPDATE)
			{
				PacketNeatPicUpdate.handleData(data, player);
			}
		}
		catch (Exception e) {}
	}

}

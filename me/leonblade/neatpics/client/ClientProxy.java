package me.leonblade.neatpics.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import me.leonblade.neatpics.ItemNeatPic;
import me.leonblade.neatpics.EntityNeatPic;
import me.leonblade.neatpics.ModNeatPic;
import me.leonblade.neatpics.CommonProxy;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy 
{

	@Override
	public void registerRenderers() 
	{
		MinecraftForgeClient.preloadTexture(this.ITEMS_PNG);
		MinecraftForgeClient.preloadTexture(this.GUI_PNG);
		MinecraftForgeClient.preloadTexture(this.SPIN_PNG);
	}
	
	public static void registerRenderInformation() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityNeatPic.class, new RenderNeatPic());
	}
	
}

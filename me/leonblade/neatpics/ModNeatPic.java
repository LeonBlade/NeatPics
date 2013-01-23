package me.leonblade.neatpics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import me.leonblade.neatpics.client.ClientProxy;
import me.leonblade.neatpics.client.RenderNeatPic;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="ModNeatPic", name="ModNeatPic", version="1.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = {"NeatPics"}, packetHandler=PacketHandler.class)
public class ModNeatPic 
{	
	public static final ItemNeatPic neatPic = new ItemNeatPic(6666);
	public static int texture = -1;
	
	// The instance of your mod that Forge uses.
	@Instance("ModNeatPic")
	public static ModNeatPic instance;
	
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide="me.leonblade.neatpics.client.ClientProxy", serverSide="me.leonblade.neatpics.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) 
	{
		
	}
	
	@Init
	public void load(FMLInitializationEvent event) 
	{
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			ClientProxy.registerRenderInformation();
		}
		
		proxy.registerRenderers();
		
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(EntityNeatPic.class, "neatPicEntity", entityID, instance, 80, 1, false);
		LanguageRegistry.instance().addStringLocalization("entity.neatPic.name", "en_US", "neatPic");
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		GameRegistry.addRecipe(new ItemStack(this.neatPic, 1), new Object[] 
		{
			" R ",
			"RPR",
			" R ",
			Character.valueOf('R'), Item.redstone,
			Character.valueOf('P'), Item.painting
		});
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) 
	{
		
	}
}

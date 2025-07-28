package mods.bwf;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import mods.bwf.crafting.RecipeManager;
import mods.bwf.network.GuiHandler;
import mods.bwf.proxy.CommonProxy;
import mods.bwf.util.BWFRenderMapper;

import static mods.bwf.BWFConstants.MODID;
import static mods.bwf.BWFConstants.MODNAME;


@Mod(modid = MODID, name = MODNAME, version = "${version}")
public class BetterWithForge {
    public static CommonProxy proxy;





    @SidedProxy(clientSide = "mods.bwf.proxy.ClientProxy", serverSide = "mods.bwf.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public static void PreLoad(FMLPreInitializationEvent PreEvent){

    }
    @Mod.EventHandler
    public static void load(FMLInitializationEvent event){

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        PROXY.preInit();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {

        PROXY.init();
        BWFRegistry.init();
        RecipeManager.addAllModRecipes();

    }



    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        PROXY.postInit();
        BWFRenderMapper.initTileEntityRenderers();
    }

    @Mod.EventHandler
    public void afterInit(FMLLoadCompleteEvent e) {
        PROXY.afterInit();
    }



}

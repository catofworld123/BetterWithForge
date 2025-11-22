package mods.bwf;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.bwf.proxy.CommonProxy;

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
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        BWFRegistry.init();
        PROXY.init();
    }



    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        PROXY.postInit();;
    }

    @Mod.EventHandler
    public void afterInit(FMLLoadCompleteEvent e) {
        PROXY.afterInit();
    }



}

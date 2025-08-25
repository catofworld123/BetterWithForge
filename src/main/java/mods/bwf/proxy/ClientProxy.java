package mods.bwf.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.bwf.client.RenderBlockCampfire;
import mods.bwf.client.RenderBlockLog;
import mods.bwf.client.RenderInfernalEnchanterBlock;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    public static int renderAnvil;
    public static int renderBlockTreeStage1;
    public static int renderBlockTreeStage2;
    public static int renderBlockTreeStage3;
    public static int renderBlockTreeStage4;
    public static int renderCampfire;
    public static int renderBlockCampfire;
    public static int renderInfernalEnchanter;
    public static int renderBlockLog;
    public static int renderVessel;
    public static ClientProxy instance;

    @Override
    public void preInit() {
        super.preInit();
    }


    @Override
    public void init() {
        super.init();
        renderBlockCampfire = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(renderBlockCampfire, new RenderBlockCampfire());
        renderInfernalEnchanter = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(renderInfernalEnchanter, new RenderInfernalEnchanterBlock());
        renderBlockLog = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(renderBlockLog, new RenderBlockLog());






    }




    @Override
    public void postInit() {
        super.postInit();
    }

    @Override
    public void afterInit() {
        super.afterInit();

    }




}

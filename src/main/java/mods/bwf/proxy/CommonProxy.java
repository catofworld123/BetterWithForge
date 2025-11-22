package mods.bwf.proxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import mods.bwf.BWFConstants;
import net.minecraftforge.oredict.OreDictionary;

public abstract class CommonProxy implements Proxy {
    public static int simpleTileRenderer = 0;
    public static int axleRenderer = 0;
    public static int crankRenderer = 0;
    public static int paneRenderer = 0;
    public static int potteryRenderer = 0;
    public static int planterRenderer = 0;
    public static int anchorRenderer = 0;
    public static int sawRenderer = 0;
    public static final boolean isMTPresent = Loader.isModLoaded("MineTweaker3");



    @Override
    public void preInit() {


    }

    @Override
    public void init() {

    }


    @Override
    public void postInit() {

    }

    @Override
    public void afterInit() {
        String[] dyes = {
            "dyeBlack",
            "dyeRed",
            "dyeGreen",
            "dyeBrown",
            "dyeBlue",
            "dyePurple",
            "dyeCyan",
            "dyeLightGray",
            "dyeGray",
            "dyePink",
            "dyeLime",
            "dyeYellow",
            "dyeLightBlue",
            "dyeMagenta",
            "dyeOrange",
            "dyeWhite"
        };
        for (int i = 0; i < dyes.length; i++) {
            BWFConstants.dyeOreIds[i] = OreDictionary.getOreID(dyes[i]);
        }
    }
}


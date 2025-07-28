package mods.bwf;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class BWFRegistry {

    public static void init() {}


    public static Item registerItem(String id, Item item) {
        item.setUnlocalizedName("bwp:" + id);
        item.setTextureName(String.format("%s:%s", BWFConstants.MODID, id));
       // item.setCreativeTab(bwfTab);
        GameRegistry.registerItem(item, id);
        return item;
    }


}

package mods.bwf.management;

import mods.bwf.BWFRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BWFCreativeTab extends CreativeTabs {
    public ItemStack infernalenchanter;

    public BWFCreativeTab() {
        super("bwf");
    }

    @Override
    public ItemStack getIconItemStack() {
        if (this.infernalenchanter == null) this.infernalenchanter = new ItemStack(BWFRegistry.itemShaft, 1, 0);
        return this.infernalenchanter;
    }

    @Override
    public Item getTabIconItem() {
        return BWFRegistry.itemShaft;
    }
}

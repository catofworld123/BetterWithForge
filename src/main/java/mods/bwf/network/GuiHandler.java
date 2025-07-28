package mods.bwf.network;

import cpw.mods.fml.common.network.IGuiHandler;
import mods.bwf.BWFRegistry;
import mods.bwf.client.gui.InfernalEnchanterGui;
import mods.bwf.inventory.container.InfernalEnchanterContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);

        if(entity != null) {

            if(ID == BWFRegistry.ENUM_IDS.InfernalEnchanter.ordinal()) {
                return new InfernalEnchanterContainer(player.inventory, world, x, y, z);
            }

        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);

        if(entity != null)
        {

            if(ID == BWFRegistry.ENUM_IDS.InfernalEnchanter.ordinal()) {
                return new InfernalEnchanterGui(player.inventory, world, x, y, z);
            }


        }
        return null;
    }

}

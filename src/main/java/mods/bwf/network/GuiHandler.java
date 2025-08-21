package mods.bwf.network;

import cpw.mods.fml.common.network.IGuiHandler;
import mods.bwf.BWFRegistry;
import mods.bwf.client.gui.InfernalEnchanterGui;
import mods.bwf.client.gui.WorkstumpGUI;
import mods.bwf.inventory.container.InfernalEnchanterContainer;
import mods.bwf.inventory.container.WorkstumpContainer;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);
        Block block = world.getBlock(x,y,z);

        if(entity != null || block != null) {

            if(ID == BWFRegistry.ENUM_IDS.InfernalEnchanter.ordinal()) {
                return new InfernalEnchanterContainer(player.inventory, world, x, y, z);
            }
            if(ID == BWFRegistry.ENUM_IDS.Workstump.ordinal()) {
                return new WorkstumpContainer(player.inventory, world, x, y, z);
            }

        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);
        Block block = world.getBlock(x,y,z);

        if(entity != null || block != null)
        {

            if(ID == BWFRegistry.ENUM_IDS.InfernalEnchanter.ordinal()) {
                return new InfernalEnchanterGui(player.inventory, world, x, y, z);
            }
            if(ID == BWFRegistry.ENUM_IDS.Workstump.ordinal()) {
                return new WorkstumpGUI(player.inventory, world, x, y, z);

            }


        }
        return null;
    }

}

package mods.bwf.block;

import mods.bwf.BWFRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class AxlePowerSourceBlock extends AxleBlock {
    public AxlePowerSourceBlock() {
        super();

        this.setBlockName("fcBlockAxlePowerSource");
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int iBlockID) {
    }

    public Item getItemDropped  (int iMetadata, Random rand, int iFortuneModifier) {
        return Item.getItemFromBlock(BWFRegistry.axle);
    }

    public int getMechanicalPowerLevelProvidedToAxleAtFacing(World world, int i, int j, int k, int iFacing) {
        int iAlignment = this.getAxisAlignment(world, i, j, k);
        return iFacing >> 1 == iAlignment ? 4 : 0;
    }

    protected void validatePowerLevel(World world, int i, int j, int k) {
    }

    public int getPowerLevel(IBlockAccess iBlockAccess, int i, int j, int k) {
        return 4;
    }

    public int getPowerLevelFromMetadata(int iMetadata) {
        return 4;
    }

    public void setPowerLevel(World world, int i, int j, int k, int iPowerLevel) {
    }

    public int setPowerLevelInMetadata(int iMetadata, int iPowerLevel) {
        return iMetadata;
    }

    public void setPowerLevelWithoutNotify(World world, int i, int j, int k, int iPowerLevel) {
    }

    public boolean clientCheckIfPowered(IBlockAccess blockAccess, int i, int j, int k) {
        return true;
    }
}

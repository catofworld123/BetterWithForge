package mods.bwf.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockUtil {
    public void dropItemsIndividually(World world, int i, int j, int k, Item iIDDropped, int iPileCount, int iDamageDropped, float fChanceOfPileDrop)
    {
        Block block = world.getBlock(i,j,k);
        for ( int iTempCount = 0; iTempCount < iPileCount; iTempCount++ )
        {
            if ( world.rand.nextFloat() <= fChanceOfPileDrop )
            {
                ItemStack stack = new ItemStack( iIDDropped, 1, iDamageDropped );

                block.dropBlockAsItem( world, i, j, k, stack );
            }
        }
    }


}

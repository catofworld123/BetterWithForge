package mods.bwf.management;

import net.minecraft.block.material.Material;

public interface BTWMaterialAdd  {
    default boolean getMobsCanSpawnOn(int iDimension)
    {
        if ( iDimension == -1 )
        {
            return true;
        }

        return true;

    }

    default Material setMobsCantSpawnOn()
    {


        return ((Material)(Object)this);
    }



    default Material setNetherMobsCanSpawnOn(){



        return ((Material)(Object)this);
    }

}

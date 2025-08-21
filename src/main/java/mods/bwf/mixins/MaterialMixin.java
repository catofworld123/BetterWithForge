package mods.bwf.mixins;

import mods.bwf.management.BTWBlockadd;
import mods.bwf.management.BTWMaterialAdd;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Material.class)
public abstract class MaterialMixin implements BTWMaterialAdd {
    private boolean canMobsSpawnOn = true;
    private boolean canNetherMobsSpawnOn = false;
    private boolean axesEfficientOn = false;
    private boolean axesTreatAsVegetation = false;
    private boolean breaksSaw = true;
    @Override
    public boolean getMobsCanSpawnOn(int iDimension)
    {
        if ( iDimension == -1 )
        {
            return canNetherMobsSpawnOn;
        }

        return canMobsSpawnOn;
    }
    @Override
    public Material setMobsCantSpawnOn()
    {
        canMobsSpawnOn = false;

        return ((Material)(Object)this);
    }

    @Override
    public Material setNetherMobsCanSpawnOn()
    {
        canNetherMobsSpawnOn = true;

        return ((Material)(Object)this);
    }




}

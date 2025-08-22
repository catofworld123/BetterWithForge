package mods.bwf.management;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialBinder {

    public static final Material LOG_MATERIAL = createLogMaterial();

    private static Material createLogMaterial() {
        Material material = new Material(MapColor.woodColor).setBurning().setRequiresTool();
        ((BTWMaterialAdd)material).setMobsCantSpawnOn();
        ((BTWMaterialAdd)material).setDoesNotBreakSaw();
        return material ;
    }
}

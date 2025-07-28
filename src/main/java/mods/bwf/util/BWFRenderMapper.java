package mods.bwf.util;

import cpw.mods.fml.client.registry.ClientRegistry;
import mods.bwf.block.tile.CampfireTileEntity;
import mods.bwf.client.CampfireRenderer;

public class BWFRenderMapper {
    public static void initTileEntityRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(CampfireTileEntity.class, new CampfireRenderer());
    }}

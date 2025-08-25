package mods.bwf;

import cpw.mods.fml.common.registry.GameRegistry;
import mods.bwf.block.*;
import mods.bwf.block.tile.CampfireTileEntity;
import mods.bwf.block.tile.InfernalEnchanterTileEntity;
import mods.bwf.item.ArcaneScrollItem;
import mods.bwf.item.ItemShaft;
import mods.bwf.item.tool.ItemPointyStick;
import mods.bwf.management.BTWMaterialAdd;
import mods.bwf.management.BTWSoundManager;
import mods.bwf.management.BWFCreativeTab;
import mods.bwf.management.MaterialBinder;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockWithMetadata;


public class BWFRegistry {
    public static final CreativeTabs bwpTab = new BWFCreativeTab();


    public static final Material logMaterial =  MaterialBinder.LOG_MATERIAL;
    public static final Material plankMaterial = MaterialBinder.PLANK_MATERIAL;

    public static final Material ashMaterial = new MaterialLogic(MapColor.stoneColor).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();


    public static Item material, bark, itemShaft, itemPointyStick, arcaneScroll, sawDust;
    public static CampfireBlock unlitCampfire;
    public static CampfireBlock smallCampfire;
    public static CampfireBlock mediumCampfire;
    public static CampfireBlock largeCampfire;
    public static Block infernalEnchanter;
    public static Block ashCoverBlock;
    public static Block workStump;
    public static Block blockLog;
    public enum ENUM_IDS
    {
        Nothing(), InfernalEnchanter(),Workstump,
    }

    public static void init() {
        infernalEnchanter = GameRegistry.registerBlock(new InfernalEnchanterBlock().setCreativeTab(BWFRegistry.bwpTab), ItemBlock.class,"infernalenchanter");
        GameRegistry.registerTileEntity(InfernalEnchanterTileEntity.class,"bwf.infernalenchanter");
        unlitCampfire = (CampfireBlock)GameRegistry.registerBlock(new CampfireBlock( 0).setCreativeTab((BWFRegistry.bwpTab)), ItemBlock.class, "unlitcampfire");
        smallCampfire = (CampfireBlock)GameRegistry.registerBlock(new CampfireBlock( 1).setLightLevel(0.25F), ItemBlock.class, "Campfirewithsmallfire");
        mediumCampfire = (CampfireBlock)GameRegistry.registerBlock(new CampfireBlock( 2).setLightLevel(0.5F), ItemBlock.class, "Campfirewithmediumfire");
        largeCampfire = (CampfireBlock)GameRegistry.registerBlock(new CampfireBlock( 3).setLightLevel(0.875F), ItemBlock.class, "Campfirewithbigfire");
        GameRegistry.registerTileEntity(CampfireTileEntity.class, "bwf.fcBlockCampfire");
        ashCoverBlock = (AshGroundCoverBlock)GameRegistry.registerBlock(new AshGroundCoverBlock(), ItemBlock.class,"fcBlockAshGroundCover");
        workStump = (WorkStumpBlock)GameRegistry.registerBlock(new WorkStumpBlock(), ItemBlock.class, "fcBlockWorkStump");
        blockLog = (BlockLogCustom)GameRegistry.registerBlock(new BlockLogCustom(), ItemBlock.class, "fcBlockLog");





        itemShaft = registerItem("shaft", new ItemShaft());
    //    pileOfDirt = registerItem("pileOfDirt", new DirtPileItem());
        itemPointyStick = registerItem("ItemPointyStick", new ItemPointyStick());
        arcaneScroll = registerItem("ItemArcaneScroll", new ArcaneScrollItem());



    }

    public static Item registerItem(String id, Item item) {
        item.setUnlocalizedName("bwf:" + id);
        item.setTextureName(String.format("%s:%s", BWFConstants.MODID, id));
        item.setCreativeTab(bwpTab);
        GameRegistry.registerItem(item, id);
        return item;
    }


}

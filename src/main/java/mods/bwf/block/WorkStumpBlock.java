package mods.bwf.block;

import mods.bwf.BWFConstants;
import mods.bwf.BWFRegistry;
import mods.bwf.management.BTWBlockadd;
import mods.bwf.util.BlockUtil;
import mods.bwf.util.ItemUtils;
import mods.bwf.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class WorkStumpBlock extends Block implements BTWBlockadd
{
    public WorkStumpBlock()
    {
        super( BWFRegistry.logMaterial);

        setHardness( 1.25F ); // log hardness


        // setChiselsEffectiveOn();

       // setFireProperties(Flammability.LOGS);


        setBlockName( "fcBlockWorkStump" );
    }

    @Override
    public float getBlockHardness(World world, int i, int j, int k )
    {
        // doing it this way instead of just setting the hardness in the constructor to replicate behavior of log stumps

        return super.getBlockHardness( world, i, j, k ) * 3;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
        player.openGui(BWFConstants.MODID,BWFRegistry.ENUM_IDS.Workstump.ordinal(),world, i,j,k);
        // prevent access if solid block above

        if ( !world.isRemote && !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j + 1, k, 0) ) {
            int metadata = world.getBlockMetadata(i, j, k);

            if ((isFinishedWorkStump(metadata))) {
                player.openGui(BWFConstants.MODID,BWFRegistry.ENUM_IDS.Workstump.ordinal(),world, i,j,k);
            }
        }

        return true;
    }

    private boolean isFinishedWorkStump(int metadata) {
        return (metadata & 8) == 0;
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop)
    {
        BlockUtil util = new BlockUtil();
        util.dropItemsIndividually(world, i, j, k, BWFRegistry.sawDust, 6, 0, fChanceOfDrop);

        return true;
    }

    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
        if ( !world.isRemote )
        {
            dropBlockAsItem( world, i, j, k, new ItemStack( BWFRegistry.sawDust, 3, 0 ) );

            dropBlockAsItem( world, i, j, k, new ItemStack( Blocks.planks, 1, 0 ) );
        }
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int i, int j, int k)
    {
        return true;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int i, int j, int k, int iFromSide) {
        int oldMetadata = world.getBlockMetadata(i, j, k);

     //   Block chewedLogID = BlockLog.chewedLogArray[oldMetadata & 3];

        if (!isFinishedWorkStump(oldMetadata) && isWorkStumpItemConversionTool(stack, world, i, j, k)) {
          //  world.playAuxSFX(BTWEffectManager.SHAFT_RIPPED_OFF_EFFECT_ID, i, j, k, 0);
            world.setBlockMetadataWithNotify(i, j, k, oldMetadata & 3, 2);
            return true;
        }

       // int newMetadata = BTWBlocks.oakChewedLog.setIsStump(0);

      //  world.setBlock(i, j, k, chewedLogID, newMetadata,2);

        if (!world.isRemote) {
            ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(BWFRegistry.bark, 1, oldMetadata & 3), iFromSide);
        }

        return true;
    }

    public boolean isWorkStumpItemConversionTool(ItemStack stack, World world, int i, int j, int k)
    {
       // if ( stack != null && stack.getItem() instanceof ChiselItem)
       // {
       //     int iToolLevel = ((ChiselItem)stack.getItem()).func_150913_i().getHarvestLevel();
        //
        //    return iToolLevel >= 2;
      //  }

        return false;
    }

    @Override
    public boolean getIsProblemToRemove(ItemStack toolStack, IBlockAccess blockAccess, int i, int j, int k) {
        int metadata = blockAccess.getBlockMetadata(i, j, k);

        return isFinishedWorkStump(metadata) || !isWorkStumpItemConversionTool(toolStack, (World) blockAccess, i, j, k);
    }

    @Override
    public boolean getDoesStumpRemoverWorkOnBlock(IBlockAccess blockAccess, int i, int j, int k)
    {
        return true;
    }

    @Override
    public ItemStack getStackRetrievedByBlockDispenser(World world, int i, int j, int k)
    {
        Block block = world.getBlock( i, j, k);
        int iMetadata = world.getBlockMetadata( i, j, k );

        if ( block.canSilkHarvest(world, null, i, j, k, iMetadata ) )
        {
            return createStackedBlock( iMetadata );
        }

        Item iIdDropped = getItemDropped( iMetadata, world.rand, 0 );

        if ( iIdDropped != null )
        {
            return new ItemStack( iIdDropped, 1, damageDropped( iMetadata ) );
        }

        return null;
    }

    //----------- Client Side Functionality -----------//

    public static final String[] sideTextureNames = new String[] {
        "bwf:oak_work_stump",
        "bwf:spruce_work_stump",
        "bwf:birch_work_stump",
        "bwf:jungle_work_stump"
    };

    public static final String[] topTextureNames = new String[] {
        "bwf:oak_stump_top",
        "bwf:spruce_stump_top",
        "bwf:birch_stump_top",
        "bwf:jungle_stump_top"
    };

    public static final String[] topCraftingTextureNames = new String[] {
        "bwf:oak_work_stump_top",
        "bwf:spruce_work_stump_top",
        "bwf:birch_work_stump_top",
        "bwf:jungle_work_stump_top"
    };

    private IIcon[] iconSideArray;
    private IIcon[] iconTopArray;
    private IIcon[] iconTopCraftingArray;

    @Override
    public IIcon getIcon(int iSide, int iMetadata) {
        if (iSide > 1) {
            return iconSideArray[iMetadata & 3];
        }
        else if (iSide == 1) {
            if (!isFinishedWorkStump(iMetadata)) {
                return iconTopArray[iMetadata & 3];
            }
            else {
                return iconTopCraftingArray[iMetadata & 3];
            }
        }
        else {
            return iconTopArray[iMetadata & 3];
        }
    }

    @Override
    public void registerBlockIcons( IIconRegister register )
    {
        iconSideArray = new IIcon[sideTextureNames.length];
        iconTopArray = new IIcon[sideTextureNames.length];
        iconTopCraftingArray = new IIcon[sideTextureNames.length];

        for (int iTextureID = 0; iTextureID < iconSideArray.length; iTextureID++ )
        {
            iconSideArray[iTextureID] = register.registerIcon(sideTextureNames[iTextureID]);
            iconTopArray[iTextureID] = register.registerIcon(topTextureNames[iTextureID]);
            iconTopCraftingArray[iTextureID] = register.registerIcon(topCraftingTextureNames[iTextureID]);
        }

        this.blockIcon = iconTopCraftingArray[0];
    }
}

package mods.bwf.block;

import mods.bwf.BWFConstants;
import mods.bwf.BWFRegistry;
import mods.bwf.crafting.util.FurnaceBurnTime;
import mods.bwf.management.BTWBlockadd;
import mods.bwf.management.BTWEffectManager;
import mods.bwf.proxy.ClientProxy;
import mods.bwf.util.Flammability;
import mods.bwf.util.ItemUtils;
import mods.bwf.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockLogCustom extends BlockRotatedPillar implements BTWBlockadd {
    /**
     * The type of tree this log came from.
     */
    public static final String[] woodType = new String[]{"oak", "spruce", "birch", "jungle", "acacia"};

    public static Block[] chewedLogArray;

    public BlockLogCustom() {
        super(BWFRegistry.logMaterial);
        this.setCreativeTab(CreativeTabs.tabBlock);

        setHardness(1.25F); // vanilla 2
        setResistance(3.33F);  // odd value to match vanilla resistance set through hardness of 2

        setAxesEffectiveOn();
        setChiselsEffectiveOn();

        setBuoyant();

        setStepSound(soundTypeWood);

        setBlockName("fcBlockLog");

        EntityEnderman.setCarriable(this,true);

    }
    private final int Flammability = mods.bwf.util.Flammability.LOGS.abilityToCatchFire;
    private final int Encouragement = mods.bwf.util.Flammability.LOGS.chanceToEncourageFire;

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return Flammability;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return Encouragement;
    }



    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(Blocks.log);
    }

    @Override
    public float getBlockHardness(World world, int i, int j, int k) {
        float fHardness = super.getBlockHardness(world, i, j, k);

        int iMetadata = world.getBlockMetadata(i, j, k);

        if (getIsStump(iMetadata)) {
            fHardness *= 3;
        }

        return fHardness;
    }

    @Override
    public boolean getIsProblemToRemove(ItemStack toolStack, IBlockAccess blockAccess, int i, int j, int k) {
        if (getIsStump(blockAccess, i, j, k)) {
            return !isWorkStumpItemConversionTool(toolStack, (World) blockAccess, i, j, k);
        }

        return false;
    }


    @Override
    public boolean getDoesStumpRemoverWorkOnBlock(IBlockAccess blockAccess, int i, int j, int k) {
        return getIsStump(blockAccess, i, j, k);
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int x, int y, int z, int iFromSide) {
        int oldMetadata = world.getBlockMetadata(x, y, z);
        int newMetadata = 0;

        Block chewedLogID = chewedLogArray[oldMetadata & 3];

        if (getIsStump(oldMetadata)) {
            if (isWorkStumpItemConversionTool(stack, world, x, y, z)) {
                if (!world.isRemote) {
                    world.playAuxSFX(BTWEffectManager.SHAFT_RIPPED_OFF_EFFECT_ID, x, y, z, 0);
                }
                world.setBlock(x, y, z, BWFRegistry.workStump, oldMetadata & 3 | 8,2);
                return true;
            }
            else {
                if (!world.isRemote) {
                    world.playAuxSFX(BTWEffectManager.LOG_STRIP_EFFECT_ID, x, y, z, 0);
                }
              //  newMetadata = BWFRegistry.oakChewedLog.setIsStump(oldMetadata & 12);
            }
        }
        else {
            int orientation = (oldMetadata >> 2) & 3;
            if (!world.isRemote) {
                world.playAuxSFX(BTWEffectManager.LOG_STRIP_EFFECT_ID, x, y, z, 0);
            }
         //   newMetadata = BWFRegistry.oakChewedLog.setOrientation(oldMetadata & 12, orientation);
        }

        world.setBlock(x, y, z, chewedLogID, newMetadata,2);

        if (!world.isRemote) {
            ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BWFRegistry.bark, 1, oldMetadata & 3), iFromSide);
        }

        return true;
    }

    @Override
    public boolean shouldPlayStandardConvertSound(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        byte leafCheckRange = 4;
        int chunkCheckRange = leafCheckRange + 1;

        if (world.checkChunksExist(x - chunkCheckRange, y - chunkCheckRange, z - chunkCheckRange, x + chunkCheckRange, y + chunkCheckRange,
            z + chunkCheckRange)) {
            for (int i = -leafCheckRange; i <= leafCheckRange; ++i) {
                for (int j = -leafCheckRange; j <= leafCheckRange; ++j) {
                    for (int k = -leafCheckRange; k <= leafCheckRange; ++k) {
                        Block offsetBlock = world.getBlock(x + i, y + j, z + k);

                        if (offsetBlock != null && ((offsetBlock == Blocks.leaves)||(offsetBlock == Blocks.leaves2))) {
                            int oldMetadata = world.getBlockMetadata(x + i, y + j, z + k);

                            if ((oldMetadata & 8) == 0) {
                                world.setBlockMetadataWithNotify(x + i, y + j, z + k, oldMetadata | 8, 4);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean getCanBlockBeIncinerated(World world, int i, int j, int k) {
        return !getIsStump(world, i, j, k);
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        dropItemsIndividually(world, i, j, k, BWFRegistry.sawDust, 6, 0, fChanceOfDrop);
        dropItemsIndividually(world, i, j, k, BWFRegistry.bark, 1, iMetadata & 3, fChanceOfDrop);

        return true;
    }

    @Override
    public void onDestroyedByFire(World world, int i, int j, int k, int iFireAge, boolean bForcedFireSpread) {
        convertToSmouldering(world, i, j, k);
    }

    @Override
    public int rotateMetadataAroundYAxis(int iMetadata, boolean bReverse) {
        int iAxisAlignment = iMetadata & 0xc;

        if (iAxisAlignment != 0) {
            if (iAxisAlignment == 4) {
                iAxisAlignment = 8;
            }
            else if (iAxisAlignment == 8) {
                iAxisAlignment = 4;
            }

            iMetadata = (iMetadata & (~0xc)) | iAxisAlignment;
        }

        return iMetadata;
    }

    @Override
    public int getFurnaceBurnTime(int iItemDamage) {
        int iWoodType = (iItemDamage) * 4;
        if (iWoodType == 0) // oak
        {
            return FurnaceBurnTime.PLANKS_OAK.burnTime;
        }
        else if (iWoodType == 1) // spruce
        {
            return FurnaceBurnTime.PLANKS_SPRUCE.burnTime;
        }
        else if (iWoodType == 2) // birch
        {
            return FurnaceBurnTime.PLANKS_BIRCH.burnTime;
        }
        else if (iWoodType == 3) // jungle
        {
            return FurnaceBurnTime.PLANKS_JUNGLE.burnTime;
        }
        else // blood == 4
        {
            return FurnaceBurnTime.PLANKS_BLOOD.burnTime;
        }

    }

    @Override
    public boolean isWood(IBlockAccess blockAccess, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess blockAccess, int x, int y, int z) {
        return !this.isDeadStump(blockAccess, x, y, z);
    }

    //------------- Class Specific Methods ------------//

    public void convertToSmouldering(World world, int i, int j, int k) {
       // SmolderingLogBlock smolderingLogBlock =  (SmolderingLogBlock) BWFRegistry.smolderingLog;
       // int iNewMetadata = smolderingLogBlock.setIsStump(0, getIsStump(world, i, j, k));

       // world.setBlock(i, j, k, BWFRegistry.smolderingLog, iNewMetadata,2);
    }

    public boolean getIsStump(int iMetadata) {
        return (iMetadata & 12) == 12;
    }

    public boolean getIsStump(IBlockAccess blockAccess, int i, int j, int k) {
        Block iBlockID = blockAccess.getBlock(i, j, k);

        if (iBlockID == BWFRegistry.blockLog) {
            int iMetadata = blockAccess.getBlockMetadata(i, j, k);

            if (getIsStump(iMetadata)) {
                return true;
            }
        }

        return false;
    }


    public boolean isDeadStump(IBlockAccess blockAccess, int i, int j, int k) {
        if (getIsStump(blockAccess, i, j, k)) {
            Block iBlockAboveID = blockAccess.getBlock(i, j + 1, k);

            if (iBlockAboveID != BWFRegistry.blockLog) {
                return true;
            }
        }

        return false;
    }

    public boolean isWorkStumpItemConversionTool(ItemStack stack, World world, int i, int j, int k) {
      //  if (stack != null && stack.getItem() instanceof ChiselItem) {
      //      int iToolLevel = ((ChiselItem) stack.getItem()).toolMaterial.getHarvestLevel();

   //          return iToolLevel >= 2;
      //  }

        return false;
    }

    public static int limitToValidMetadata(int par0) {
        return par0 & 3;
    }

    //----------- Client Side Functionality -----------//

    private IIcon[] sideIcons;
    private IIcon[] topIcons;

    private IIcon[] stumpSideIcons;
    private IIcon[] stumpTopIcons;

    @Override
    public IIcon getIcon(int side, int metadata) {
        int facing = metadata >> 2 & 3;

        if ((metadata & 12) == 12) {
            if (side > 1) {
                return stumpSideIcons[metadata & 3];
            }
            else {
                return stumpTopIcons[metadata & 3];
            }
        }

        return super.getIcon(side, metadata);
    }

    @Override
    public int getRenderType() {
        return ClientProxy.renderBlockLog;
    }


    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        renderer.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);

        return renderer.renderBlockLog(this, i, j, k);
    }


    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
     //   renderCookingByKiLnOverlay(renderBlocks, i, j, k, bFirstPassResult);
    }


    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.sideIcons = new IIcon[woodType.length];
        this.topIcons = new IIcon[woodType.length];
        this.stumpSideIcons = new IIcon[woodType.length];
        this.stumpTopIcons = new IIcon[woodType.length];

        for (int var2 = 0; var2 < this.sideIcons.length; ++var2) {
            this.sideIcons[var2] = par1IconRegister.registerIcon("bwf:" + "log_" + woodType[var2]);

            if (var2 == 0) {
                this.topIcons[var2] = par1IconRegister.registerIcon("bwf:" + "log_" + woodType[var2] + "_top");
            }
            else {
                this.topIcons[var2] = par1IconRegister.registerIcon("bwf:" + woodType[var2] + "_log_top");
            }

            this.stumpSideIcons[var2] = par1IconRegister.registerIcon("bwf:" + woodType[var2] + "_stump");
            this.stumpTopIcons[var2] = par1IconRegister.registerIcon("bwf:" + woodType[var2] + "_stump_top");
        }
    }

    @Override
    protected IIcon getSideIcon(int par1) {
        return this.sideIcons[par1];
    }

    @Override
    protected IIcon getTopIcon(int par1) {
        return this.topIcons[par1];
    }
}


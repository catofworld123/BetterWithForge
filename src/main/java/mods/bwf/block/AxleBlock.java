package mods.bwf.block;

import mods.bwf.BWFRegistry;
import mods.bwf.management.BTWBlockadd;
import mods.bwf.proxy.ClientProxy;
import mods.bwf.util.BlockPos;
import mods.bwf.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class AxleBlock extends Block implements BTWBlockadd {
    protected static final double AXLE_WIDTH = (double)0.25F;
    protected static final double AXLE_HALF_WIDTH = (double)0.125F;
    public static final int AXLE_TICK_RATE = 1;
    protected static final int[][] axleFacingsForAlignment = new int[][]{{0, 1}, {2, 3}, {4, 5}};
    public IIcon iconSide;
    public IIcon iconSideOn;
    public IIcon iconSideOnOverpowered;
    public boolean isPowerOnForCurrentRender;
    public boolean isOverpoweredForCurrentRender;

    public AxleBlock() {
        super(BWFRegistry.plankMaterial);
        this.setHardness(2.0F);
        this.setAxesEffectiveOn(true);
        this.setBuoyancy(1.0F);
        this.setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
        this.setStepSound(soundTypeWood);
        this.setBlockName("fcBlockAxle");
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public int tickRate(World world) {
        return 1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int onBlockPlaced(World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata) {
        return this.setAxisAlignmentInMetadataBasedOnFacing(iMetadata, iFacing);
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        world.scheduleBlockUpdate(i, j, k, this, this.tickRate(world));
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        this.setPowerLevel(world, i, j, k, 0);
        this.validatePowerLevel(world, i, j, k);
    }


    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int i, int j, int k){
        int iAxis = this.getAxisAlignment(blockAccess,i,j,k);
        this.setThembasedonAxis(iAxis);

    }
    private void setThembasedonAxis(int iAxis){
        if (iAxis == 0){
            this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
        }
        else if (iAxis == 1){
            this.setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
        }
        else  this.setBlockBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int iBlockID) {
        this.validatePowerLevel(world, i, j, k);


    }
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z)
    {

        int iAxis = this.getAxisAlignment(worldIn,x,y,z);

        if (iAxis == 0){
            return AxisAlignedBB.getBoundingBox(x+0.375F, y+0.0F, z+0.375F, x+0.625F, y+1.0F, z+0.625F);
        }
        else if (iAxis == 1){
            return AxisAlignedBB.getBoundingBox(x+0.375F, y+0.375F, z+0.0F, x+0.625F, y+0.625F, z+1.0F);
        }
        else  return AxisAlignedBB.getBoundingBox(x+0.0F, y+0.375F, z+0.375F, x+1.0F, y+0.625F, z+0.625F);


    }

    public int getMobilityFlag() {
        return 1;
    }

    public boolean hasCenterHardPointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency) {
        return this.isAxleOrientedTowardsFacing(blockAccess, i, j, k, iFacing);
    }

    public int getMechanicalPowerLevelProvidedToAxleAtFacing(World world, int i, int j, int k, int iFacing) {
        int iAlignment = this.getAxisAlignment(world, i, j, k);
        return iFacing >> 1 == iAlignment ? this.getPowerLevel(world, i, j, k) : 0;
    }

    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return 2;
    }

    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        //this.dropItemsIndividually(world, i, j, k, BTWItems.hempFibers.itemID, 4, 0, fChanceOfDrop);
        this.dropItemsIndividually(world, i, j, k, BWFRegistry.sawDust, 2, 0, fChanceOfDrop);
        return true;
    }

    public int getFacing(int iMetadata) {
        return this.getAxisAlignmentFromMetadata(iMetadata) << 1;
    }

    public int setFacing(int iMetadata, int iFacing) {
        return this.setAxisAlignmentInMetadataBasedOnFacing(iMetadata, iFacing);
    }

    public boolean toggleFacing(World world, int i, int j, int k, boolean bReverse) {
        int iAxisAlignment = this.getAxisAlignment(world, i, j, k);
        if (!bReverse) {
            ++iAxisAlignment;
            if (iAxisAlignment > 2) {
                iAxisAlignment = 0;
            }
        } else {
            --iAxisAlignment;
            if (iAxisAlignment < 0) {
                iAxisAlignment = 2;
            }
        }

        this.setAxisAlignment(world, i, j, k, iAxisAlignment);
        world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
        this.setPowerLevel(world, i, j, k, 0);
        this.validatePowerLevel(world, i, j, k);
        world.markBlockForUpdate(i, j, k);
        return true;
    }

    public boolean canGroundCoverRestOnBlock(World world, int i, int j, int k) {
        return world.doesBlockHaveSolidTopSurface(world,i, j - 1, k);
    }

    public float groundCoverRestingOnVisualOffset(IBlockAccess blockAccess, int i, int j, int k) {
        return -1.0F;
    }

    public int getAxisAlignment(IBlockAccess iBlockAccess, int i, int j, int k) {
        return iBlockAccess.getBlockMetadata(i, j, k) >> 2;
    }

    public void setAxisAlignment(World world, int i, int j, int k, int iAxisAlignment) {
        int iMetaData = world.getBlockMetadata(i, j, k) & 3;
        iMetaData |= iAxisAlignment << 2;
        world.setBlockMetadataWithNotify(i, j, k, iMetaData,2);
    }

    public int getAxisAlignmentFromMetadata(int iMetadata) {
        return iMetadata >> 2;
    }

    public void setAxisAlignmentBasedOnFacing(World world, int i, int j, int k, int iFacing) {
        int iAxis;
        switch (iFacing) {
            case 0:
            case 1:
                iAxis = 0;
                break;
            case 2:
            case 3:
                iAxis = 1;
                break;
            default:
                iAxis = 2;
        }

        int iMetaData = world.getBlockMetadata(i, j, k) & 3;
        iMetaData |= iAxis << 2;
        world.setBlockMetadataWithNotify(i, j, k, iMetaData,2);
    }

    public int setAxisAlignmentInMetadataBasedOnFacing(int iMetadata, int iFacing) {
        int iAxis;
        switch (iFacing) {
            case 0:
            case 1:
                iAxis = 0;
                break;
            case 2:
            case 3:
                iAxis = 1;
                break;
            default:
                iAxis = 2;
        }

        iMetadata &= 3;
        iMetadata |= iAxis << 2;
        return iMetadata;
    }

    public int getPowerLevel(IBlockAccess iBlockAccess, int i, int j, int k) {
        return this.getPowerLevelFromMetadata(iBlockAccess.getBlockMetadata(i, j, k));
    }

    public int getPowerLevelFromMetadata(int iMetadata) {
        return iMetadata & 3;
    }

    public void setPowerLevel(World world, int i, int j, int k, int iPowerLevel) {
        int iMetadata = world.getBlockMetadata(i, j, k);
        iMetadata = this.setPowerLevelInMetadata(iMetadata, iPowerLevel);
        world.setBlockMetadataWithNotify(i, j, k, iMetadata,2);
    }

    public int setPowerLevelInMetadata(int iMetadata, int iPowerLevel) {
        iPowerLevel &= 3;
        iMetadata &= 12;
        iMetadata |= iPowerLevel;
        return iMetadata;
    }

    public void setPowerLevelWithoutNotify(World world, int i, int j, int k, int iPowerLevel) {
        int iMetadata = world.getBlockMetadata(i, j, k);
        iMetadata = this.setPowerLevelInMetadata(iMetadata, iPowerLevel);
        world.setBlockMetadataWithNotify(i, j, k, iMetadata,2);
    }

    public boolean isAxleOrientedTowardsFacing(IBlockAccess iBlockAccess, int i, int j, int k, int iFacing) {
        int iAxis = this.getAxisAlignment(iBlockAccess, i, j, k);
        switch (iAxis) {
            case 0:
                if (iFacing == 0 || iFacing == 1) {
                    return true;
                }
                break;
            case 1:
                if (iFacing == 2 || iFacing == 3) {
                    return true;
                }
                break;
            default:
                if (iFacing == 4 || iFacing == 5) {
                    return true;
                }
        }

        return false;
    }

    public void breakAxle(World world, int i, int j, int k) {
        if (world.getBlock(i, j, k) == this) {
            this.dropComponentItemsOnBadBreak(world, i, j, k, world.getBlockMetadata(i, j, k), 1.0F);
            world.playAuxSFX(2235, i, j, k, 0);
            world.setBlock(i, j, k, Blocks.air);
        }

    }

    protected void validatePowerLevel(World world, int i, int j, int k) {
        int iCurrentPower = this.getPowerLevel(world, i, j, k);
        int iAxis = this.getAxisAlignment(world, i, j, k);
        int iMaxNeighborPower = 0;
        int iGreaterPowerNeighbors = 0;

        for(int iTempSourceIndex = 0; iTempSourceIndex < 2; ++iTempSourceIndex) {
            int iTempFacing = axleFacingsForAlignment[iAxis][iTempSourceIndex];
            BlockPos tempSourcePos = new BlockPos(i, j, k, iTempFacing);
            Block tempBlock = world.getBlock(tempSourcePos.x, tempSourcePos.y, tempSourcePos.z);
            if (tempBlock != null) {
                BTWBlockadd blockadd = (BTWBlockadd)tempBlock;
                int iTempPowerLevel = 0; //tempBlock.getMechanicalPowerLevelProvidedToAxleAtFacing(world, tempSourcePos.x, tempSourcePos.y, tempSourcePos.z, blockadd.getFacing(iTempFacing)^1);
                if (iTempPowerLevel > iMaxNeighborPower) {
                    iMaxNeighborPower = iTempPowerLevel;
                }

                if (iTempPowerLevel > iCurrentPower) {
                    ++iGreaterPowerNeighbors;
                }
            }
        }

        if (iGreaterPowerNeighbors >= 2) {
            this.breakAxle(world, i, j, k);
        } else {
            int var15;
            if (iMaxNeighborPower > iCurrentPower) {
                if (iMaxNeighborPower == 1) {
                    this.breakAxle(world, i, j, k);
                    return;
                }

                var15 = iMaxNeighborPower - 1;
            } else {
                var15 = 0;
            }

            if (var15 != iCurrentPower) {
                this.setPowerLevel(world, i, j, k, var15);
            }

        }
    }

    private void emitAxleParticles(World world, int i, int j, int k, Random random) {
        for(int counter = 0; counter < 2; ++counter) {
            float smokeX = (float)i + random.nextFloat();
            float smokeY = (float)j + random.nextFloat() * 0.5F + 0.625F;
            float smokeZ = (float)k + random.nextFloat();
            world.spawnParticle("smoke", (double)smokeX, (double)smokeY, (double)smokeZ, (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    public void overpower(World world, int i, int j, int k) {
        int iAxis = this.getAxisAlignment(world, i, j, k);
        switch (iAxis) {
            case 0:
                this.overpowerBlockToFacing(world, i, j, k, iAxis, 0);
                this.overpowerBlockToFacing(world, i, j, k, iAxis, 1);
                break;
            case 1:
                this.overpowerBlockToFacing(world, i, j, k, iAxis, 2);
                this.overpowerBlockToFacing(world, i, j, k, iAxis, 3);
                break;
            default:
                this.overpowerBlockToFacing(world, i, j, k, iAxis, 4);
                this.overpowerBlockToFacing(world, i, j, k, iAxis, 5);
        }

    }

    private void overpowerBlockToFacing(World world, int i, int j, int k, int iSourceAxis, int iFacing) {
        BlockPos targetPos = new BlockPos(i, j, k);
        targetPos.addFacingAsOffset(iFacing);
        Block iTempBlockID = world.getBlock(targetPos.x, targetPos.y, targetPos.z);
        if (iTempBlockID != BWFRegistry.axle && iTempBlockID != BWFRegistry.axlePowerSource) {
            if (iTempBlockID instanceof MechanicalBlock) {
                MechanicalBlock mechDevice = (MechanicalBlock)iTempBlockID;
                BTWBlockadd blockadd = (BTWBlockadd)mechDevice;
                if (mechDevice.canInputAxlePowerToFacing(world, targetPos.x, targetPos.y, targetPos.z, blockadd.getFacing(iFacing))) {
                    mechDevice.overpower(world, targetPos.x, targetPos.y, targetPos.z);
                }
            }
        } else {
            int iTempAxis = this.getAxisAlignment(world, targetPos.x, targetPos.y, targetPos.z);
            if (iTempAxis == iSourceAxis) {
                this.overpowerBlockToFacing(world, targetPos.x, targetPos.y, targetPos.z, iSourceAxis, iFacing);
            }
        }

    }

    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon("bwf:axle_end");
        this.iconSide = register.registerIcon("bwf:axle");
        this.iconSideOn = register.registerIcon("bwf:axle_powered");
        this.iconSideOnOverpowered = register.registerIcon("bwf:axle_overpowered");
    }


    public IIcon getIcon(int iSide, int iMetadata) {
        return iSide != 2 && iSide != 3 ? this.iconSide : this.blockIcon;
    }


    public IIcon getIcon(IBlockAccess blockAccess, int i, int j, int k, int iSide) {
        int iAxisAlignment = this.getAxisAlignment(blockAccess, i, j, k);
        if (iAxisAlignment == 0) {
            if (iSide >= 2) {
                return this.getAxleSideTextureForOnState(this.isPowerOnForCurrentRender);
            }
        } else if (iAxisAlignment == 1) {
            if (iSide != 2 && iSide != 3) {
                return this.getAxleSideTextureForOnState(this.isPowerOnForCurrentRender);
            }
        } else if (iSide < 4) {
            return this.getAxleSideTextureForOnState(this.isPowerOnForCurrentRender);
        }

        return this.blockIcon;
    }

    public IIcon getAxleSideTextureForOnState(boolean bIsOn) {
        return bIsOn ? this.iconSideOn : this.iconSide;
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (this.clientCheckIfPowered(world, i, j, k)) {
            this.emitAxleParticles(world, i, j, k, random);
            if (random.nextInt(200) == 0) {
                world.playSound((double)i + (double)0.5F, (double)j + (double)0.5F, (double)k + (double)0.5F, "random.chestopen", 0.075F, random.nextFloat() * 0.1F + 0.5F,false);
            }
        }

    }

    public boolean clientCheckIfPowered(IBlockAccess blockAccess, int i, int j, int k) {
        this.getPowerLevel(blockAccess, i, j, k);
        int iAxis = this.getAxisAlignment(blockAccess, i, j, k);

        for(int iTempFacingIndex = 0; iTempFacingIndex < 2; ++iTempFacingIndex) {
            BlockPos targetPos = new BlockPos(i, j, k);
            int iFacingOfCheck = axleFacingsForAlignment[iAxis][iTempFacingIndex];

            for(int iTempDistance = 1; iTempDistance <= 3; ++iTempDistance) {
                targetPos.addFacingAsOffset(iFacingOfCheck);
                Block iTempBlock = blockAccess.getBlock(targetPos.x, targetPos.y, targetPos.z);
                if (iTempBlock != this || this.getAxisAlignment(blockAccess, targetPos.x, targetPos.y, targetPos.z) != iAxis) {
                    if (iTempBlock == BWFRegistry.axlePowerSource && this.getAxisAlignment(blockAccess, targetPos.x, targetPos.y, targetPos.z) == iAxis) {
                        return true;
                    }

                 //   if (MechPowerUtils.isPoweredGearBox(blockAccess, targetPos.x, targetPos.y, targetPos.z)) {
                  //      return true;
                  //  }
                  //  break;
                }
            }
        }

        return false;
    }

    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        BlockPos myPos = new BlockPos(iNeighborI, iNeighborJ, iNeighborK, getFacing(iSide) ^ 1);
        if (this.isAxleOrientedTowardsFacing(blockAccess, myPos.x, myPos.y, myPos.z, iSide)) {
            Block iNeighborBlockID = blockAccess.getBlock(iNeighborI, iNeighborJ, iNeighborK);
            if (iNeighborBlockID != this) {
                return RenderUtils.shouldRenderNeighborFullFaceSide(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide);
            }

            if (this.getAxisAlignment(blockAccess, myPos.x, myPos.y, myPos.z) == this.getAxisAlignment(blockAccess, iNeighborI, iNeighborJ, iNeighborK)) {
                return false;
            }
        }

        return true;
    }

    public void clientBreakBlock(World world, int i, int j, int k, int iBlockID, int iMetadata) {
        world.markBlockRangeForRenderUpdate(i - 3, j - 3, k - 3, i + 3, j + 3, k + 3);
    }

    public void clientBlockAdded(World world, int i, int j, int k) {
        world.markBlockRangeForRenderUpdate(i - 3, j - 3, k - 3, i + 3, j + 3, k + 3);
    }
    @Override
    public int getRenderType() {
        return ClientProxy.renderAxle;
    }

    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(
        IBlockAccess blockAccess, int i, int j, int k)
    {
        int iAxis = getAxisAlignment(blockAccess, i, j, k);

        switch ( iAxis )
        {
            case 0:

                return AxisAlignedBB.getBoundingBox(
                    0.5D - AXLE_HALF_WIDTH, 0D, 0.5D - AXLE_HALF_WIDTH,
                    0.5D + AXLE_HALF_WIDTH, 1D, 0.5D + AXLE_HALF_WIDTH);

            case 1:

                return AxisAlignedBB.getBoundingBox(
                    0.5D - AXLE_HALF_WIDTH, 0.5D - AXLE_HALF_WIDTH, 0D,
                    0.5D + AXLE_HALF_WIDTH, 0.5D + AXLE_HALF_WIDTH, 1D);

            default:

                return AxisAlignedBB.getBoundingBox(
                    0D, 0.5D - AXLE_HALF_WIDTH, 0.5F - AXLE_HALF_WIDTH,
                    1D, 0.5D + AXLE_HALF_WIDTH, 0.5F + AXLE_HALF_WIDTH);
        }
    }
}


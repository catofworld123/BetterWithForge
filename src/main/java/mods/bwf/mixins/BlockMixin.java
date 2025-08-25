package mods.bwf.mixins;

import mods.bwf.crafting.util.FurnaceBurnTime;
import mods.bwf.management.BTWBlockadd;
import mods.bwf.management.BTWMaterialAdd;
import mods.bwf.util.BlockPos;
import mods.bwf.util.Flammability;
import mods.bwf.util.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements BTWBlockadd {
    private boolean shovelsEffectiveOn = false;
    private boolean chiselsEffectiveOn = false;
    private boolean axesEffectiveOn = false;
    private int defaultFurnaceBurnTime = 0;
    private static final int[] rotatedFacingsAroundJClockwise =
        new int[] { 0, 1, 4, 5, 3, 2 };

    private static final int[] rotatedFacingsAroundJCounterclockwise =
        new int[] { 0, 1, 5, 4, 2, 3 };

    private static final int[] cycledFacings =
        new int[] { 4, 0, 1, 5, 3, 2 };

    private static final int[] cycledFacingsReversed =
        new int[] { 1, 2, 5, 4, 0, 3 };
    @Override
    public boolean isGroundCover(){
        return true;
    }
    @Override
    public float groundCoverRestingOnVisualOffset(IBlockAccess blockAccess, int i, int j, int k) {
        return 0.0f;
    }
    @Override
    public boolean canGroundCoverRestOnBlock(World world, int i, int j, int k) {
        return world.doesBlockHaveSolidTopSurface(world, i, j, k);
    }
    @Override
    public boolean isSnowCoveringTopSurface(IBlockAccess blockAccess, int i, int j, int k)
    {
        Block iBlockAboveID = blockAccess.getBlock( i, j + 1, k );

        if ( iBlockAboveID != Blocks.air )
        {
            Block blockAbove = iBlockAboveID;

            Material aboveMaterial = blockAbove.getMaterial();
            BTWBlockadd blockadd = (BTWBlockadd)blockAbove;

            if ( aboveMaterial == Material.snow || aboveMaterial == Material.craftedSnow &&
                blockAbove.isBlockSolid(( blockAccess), i, j + 1, k, 0 ) )
            {
                return true;
            }
            else if (
                blockadd.groundCoverRestingOnVisualOffset(blockAccess, i, j + 1, k) < -0.99F &&
                    blockAccess.getBlock( i, j + 2, k ) == Blocks.snow)
            {
                // consider snow resting on tall grass and such

                return true;
            }
        }

        return false;
    }
    @Override
    public boolean hasMortar(IBlockAccess blockAccess, int i, int j, int k)
    {
        return false;
    }
    @Override
    public int getFacing(int iMetadata)
    {
        return 0;
    }
    @Override
    public boolean hasContactPointToFullFace(IBlockAccess blockAccess, int i, int j, int k, int iFacing)
    {
        Block block = blockAccess.getBlock(i,j,k);
        return block.isNormalCube(blockAccess, i,j,k);
    }

    @Override
    public boolean hasContactPointToSlabSideFace(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIsSlabUpsideDown)
    {
        return hasContactPointToFullFace(blockAccess, i, j, k, iFacing);
    }

    @Override
    public boolean isStickyToSnow(IBlockAccess blockAccess, int i, int j, int k)
    {
        return false;
    }
    @Override
    public boolean hasStickySnowNeighborInContact(World world, int i, int j, int k)
    {
        for ( int iTempFacing = 0; iTempFacing < 6; iTempFacing++ )
        {
            if ( WorldUtils.hasStickySnowNeighborInFullFaceContactToFacing(world, i, j, k, iTempFacing) )
            {
                return true;
            }
        }

        return false;
    }
    @Override
    public boolean hasNeighborWithMortarInContact(World world, int i, int j, int k)
    {
        for ( int iTempFacing = 0; iTempFacing < 6; iTempFacing++ )
        {
            if ( WorldUtils.hasNeighborWithMortarInFullFaceContactToFacing(world, i, j, k, iTempFacing) )
            {
                return true;
            }
        }

        return false;
    }
    @Override
    public boolean canMobsSpawnOn(World world, int i, int j, int k)
    {
        Block block = world.getBlock(i,j,k);
        Material blockMaterial = block.getMaterial();
        BTWMaterialAdd materialAdd = (BTWMaterialAdd)blockMaterial;
        return materialAdd.getMobsCanSpawnOn(world.provider.dimensionId) &&
            block.getCollisionBoundingBoxFromPool( world, i, j, k ) != null;
    }
    @Override
    public float mobSpawnOnVerticalOffset(World world, int i, int j, int k)
    {
        return 0F;
    }
    @Override
    public boolean shouldRenderNeighborHalfSlabSide(IBlockAccess blockAccess, int i, int j, int k, int iNeighborSlabSide, boolean bNeighborUpsideDown)
    {
        Block block = blockAccess.getBlock(i,j,k);
        return !block.isOpaqueCube();
    }
    @Override
    public boolean shouldRenderNeighborFullFaceSide(IBlockAccess blockAccess, int i, int j, int k, int iNeighborSide)
    {
        Block block = blockAccess.getBlock(i,j,k);
        return !block.isOpaqueCube();
    }
    @Override
    public float getMovementModifier(World world, int i, int j, int k)
    {
        float fModifier = 1.0F;
        Block block = world.getBlock(i,j,k);
        Material blockMaterial = block.getMaterial();

        if ( blockMaterial != Material.ground && blockMaterial != Material.grass )
        {
            fModifier *= 1.2F;
        }

        return fModifier;
    }
    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop)
    {
        return false;
    }
    @Override
    public void dropItemsIndividually(World world, int i, int j, int k, Item iItemDropped, int iPileCount, int iDamageDropped, float fChanceOfPileDrop)
    {
        Block block = world.getBlock(i,j,k);
        for ( int iTempCount = 0; iTempCount < iPileCount; iTempCount++ )
        {
            if ( world.rand.nextFloat() <= fChanceOfPileDrop )
            {
                ItemStack stack = new ItemStack( iItemDropped, 1, iDamageDropped );

                block.dropBlockAsItem( world, i, j, k, stack );
            }
        }
    }
    @Override
    public boolean canBePistonShoveled(World world, int i, int j, int k)
    {
        return areShovelsEffectiveOn();
    }
    @Override
    public   boolean areShovelsEffectiveOn()
    {
        return shovelsEffectiveOn;
    }
    @Override
    public boolean canBeGrazedOn(IBlockAccess blockAccess, int i, int j, int k,
                                 EntityAnimal byAnimal)
    {
        return false;
    }
    @Override
    public void onGrazed(World world, int i, int j, int k, EntityAnimal animal)
    {
        world.setBlockToAir( i, j, k );

        Block blockBelow = world.getBlock( i, j - 1, k );

        if ( blockBelow != null )
        {
            BTWBlockadd blockadd = (BTWBlockadd)blockBelow;
            blockadd.onVegetationAboveGrazed(world, i, j - 1, k, animal);
        }
    }
    @Override
    public void onVegetationAboveGrazed(World world, int i, int j, int k, EntityAnimal animal)
    {
    }
    @Override
    public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata)
    {
       // world.playAuxSFX( BTWEffectManager.BLOCK_DESTROYED_WITH_IMPROPER_TOOL_EFFECT_ID, i, j, k, world.getBlockMetadata(i,j,k) + ( iMetadata << 12 ) );

        dropComponentItemsOnBadBreak(world, i, j, k, iMetadata, 1F);
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int i, int j, int k)
    {
        return false;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int i, int j, int k, int iFromSide)
    {
        return false;
    }

    @Override
    public boolean getIsProblemToRemove(ItemStack toolStack, IBlockAccess blockAccess, int i, int j, int k)
    {
        return false;
    }

    @Override
    public boolean getDoesStumpRemoverWorkOnBlock(IBlockAccess blockAccess, int i, int j, int k)
    {
        return false;
    }

    @Override
    public boolean arechiselseffectiveon()
    {
        return chiselsEffectiveOn;
    }

    @Override
    public boolean arechiselseffectiveon(World world, int i, int j, int k)
    {
        return arechiselseffectiveon();
    }

    @Override
    public Block setAxesEffectiveOn() { return setAxesEffectiveOn(true); }

    @Override
    public Block setAxesEffectiveOn(boolean bEffective)
    {
        axesEffectiveOn = bEffective;

        return ((Block)(Object)this);
    }
    @Override
    public Block setChiselsEffectiveOn() { return setChiselsEffectiveOn(true); }

    @Override
    public Block setChiselsEffectiveOn(boolean bEffective)
    {
        chiselsEffectiveOn = bEffective;

        return ((Block)(Object)this);
    }


    private float buoyancy = -1.0F;

    @Override
    public Block setBuoyancy(float fBuoyancy)
    {
        buoyancy = fBuoyancy;

        return ((Block)(Object)this);
    }
    @Override
    public Block setBuoyant() { return setBuoyancy(1F); }
    @Override
    public Block setNonBuoyant() { return setBuoyancy(-1F); }
    @Override
    public Block setNeutralBuoyant() { return setBuoyancy(0F); }
    @Override
    public float getBuoyancy(int iMetadata)
    {
        return buoyancy;
    }

    @Override
    public Block setFireProperties(Block block, int iChanceToEncourageFire, int iAbilityToCatchFire)
    {

        Blocks.fire.setFireInfo(block, iChanceToEncourageFire, iChanceToEncourageFire);

        return ((Block)(Object)this);
    }

    @Override
    public Block setFireProperties(Block block, Flammability flammability)
    {
        return setFireProperties(block, flammability.chanceToEncourageFire,
            flammability.abilityToCatchFire);
    }
    @Override
    public boolean shouldPlayStandardConvertSound(World world, int x, int y, int z) {
        return true;
    }
    @Override
    public int rotateFacingAroundY(int iFacing, boolean bReverse)
    {
        if ( bReverse )
        {
            return rotatedFacingsAroundJCounterclockwise[iFacing];
        }

        return rotatedFacingsAroundJClockwise[iFacing];
    }
    @Override
    public int rotateMetadataAroundYAxis(int iMetadata, boolean bReverse)
    {
        int iFacing = getFacing(iMetadata);

        int iNewFacing = rotateFacingAroundY(iFacing, bReverse);

        return setFacing(iMetadata, iNewFacing);
    }
    @Override
    public void setFacing(World world, int i, int j, int k, int iFacing)
    {
        int iMetadata = world.getBlockMetadata( i, j, k );

        int iNewMetadata = setFacing(iMetadata, iFacing);

        if ( iNewMetadata != iMetadata )
        {
            world.setBlockMetadataWithNotify( i, j, k, iNewMetadata,2 );
        }
    }

    @Override
    public int setFacing(int iMetadata, int iFacing)
    {
        return iMetadata;
    }

    @Override
    public void onDestroyedByFire(World world, int i, int j, int k, int iFireAge, boolean bForcedFireSpread)
    {
        boolean trueTag = false;
        BiomeGenBase biome = world.getBiomeGenForCoords( i, k );
        if ( world.isRaining() && world.canBlockSeeTheSky( i, j, k ) &&
            j >= world.getPrecipitationHeight( i, k )  )
        {
            trueTag = biome.canSpawnLightningBolt();
        }

        if ( bForcedFireSpread || ( world.rand.nextInt( iFireAge + 10 ) < 5 &&
            !trueTag ) )
        {
            int iNewFireMetadata = iFireAge + world.rand.nextInt( 5 ) / 4;

            if ( iNewFireMetadata > 15 )
            {
                iNewFireMetadata = 15;
            }

            world.setBlock( i, j, k, Blocks.fire, iNewFireMetadata,2 );
        }
        else
        {
            world.setBlock( i, j, k, Blocks.air );
        }
    }
    @Override
    public boolean getCanBlockBeIncinerated(World world, int i, int j, int k)
    {
        Block block = world.getBlock(i,j,k);
        Material blockMaterial = block.getMaterial();
        return Blocks.fire.canBlockCatchFire( world, i, j, k ) || !blockMaterial.blocksMovement();
    }
    @Override
    public int getFurnaceBurnTime(int iItemDamage)
    {
        return defaultFurnaceBurnTime;
    }
    @Override
    public void setFurnaceBurnTime(int iBurnTime)
    {
        defaultFurnaceBurnTime = iBurnTime;
    }
    @Override
    public void setFurnaceBurnTime(FurnaceBurnTime burnTime)
    {
        setFurnaceBurnTime(burnTime.burnTime);
    }


    @Override
    public boolean hasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency )
    {
        return blockAccess.getBlock( i, j, k ).isNormalCube();
    }
    @Override
    public boolean hasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing )
    {
        return hasLargeCenterHardPointToFacing( blockAccess, i, j, k, iFacing, false );
    }
    @Override
    public boolean hasWaterToSidesOrTop(World world, int i, int j, int k)
    {
        for ( int iFacing = 1; iFacing <= 5; iFacing++ )
        {
            BlockPos tempPos = new BlockPos( i, j, k, iFacing );

            Block tempBlock = world.getBlock(tempPos.x, tempPos.y, tempPos.z);

            if ( tempBlock != null && tempBlock.getMaterial() == Material.water )
            {
                return true;
            }
        }

        return false;
    }








}

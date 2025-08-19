package mods.bwf.block;

import mods.bwf.BWFRegistry;
import mods.bwf.management.BTWBlockadd;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class AshGroundCoverBlock extends GroundCoverBlock {
    public AshGroundCoverBlock() {
        super(BWFRegistry.ashMaterial);
        this.setTickRandomly(true);
        this.setStepSound(Block.soundTypeSand);
        this.setBlockName("bwf:fcBlockAshGroundCover");
        this.setBlockTextureName("bwf:ash");
        this.setCreativeTab(BWFRegistry.bwpTab);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random rand) {
        if (isRainingAtPos( world,i, j, k)) {
            world.setBlock(i, j, k, Blocks.air);
        }
    }
    public boolean isRainingAtPos(World world, int i, int j, int k)
    {
        if ( world.isRaining() && world.canBlockSeeTheSky( i, j, k ) &&
            j >= world.getPrecipitationHeight( i, k )  )
        {
            BiomeGenBase biome = world.getBiomeGenForCoords( i, k );

            return biome.canSpawnLightningBolt();
        }

        return false;
    }

    @Override
    public Item getItemDropped(int iMetadata, Random rand, int iFortuneModifier) {
        return null;
    }

    @Override
    public boolean getCanBlockGrass() {
        return false;
    }

    @Override
    public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
        return true;
    }

    public static boolean canAshReplaceBlock(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        BTWBlockadd blockadd = (BTWBlockadd)block;
        return block == null || block == Blocks.air || blockadd.isGroundCover() && block != BWFRegistry.ashCoverBlock;
    }

    public static boolean attemptToPlaceAshAt(World world, int i, int j, int k) {
        Block iBlockBelowID;
        Block blockBelow;
        if (AshGroundCoverBlock.canAshReplaceBlock(world, i, j, k) && (blockBelow = (iBlockBelowID = world.getBlock(i, j - 1, k))) != null) {
            BTWBlockadd blockadd = (BTWBlockadd)blockBelow;
            blockadd.canGroundCoverRestOnBlock(world, i, j - 1, k);
            world.setBlock(i, j, k, BWFRegistry.ashCoverBlock);
            return true;
        }
        return false;
    }

    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random rand) {
        super.randomDisplayTick(world, i, j, k, rand);
        if (rand.nextInt(10) == 0) {
            double dYParticle = (double)j + 0.25;
            world.spawnParticle("townaura", (double)i + rand.nextDouble(), dYParticle, (double)k + rand.nextDouble(), 0.0, 0.0, 0.0);
        }
    }
}



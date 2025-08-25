package mods.bwf.util;

import mods.bwf.management.BTWBlockadd;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldUtils {

    public static boolean isReplaceableBlock(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        return block == null || block.getMaterial().isReplaceable();

    }
    static public boolean hasNeighborWithMortarInFullFaceContactToFacing(World world, int i, int j, int k, int iFacing)
    {
        BlockPosition tempBlockPos = new BlockPosition( i, j, k , ForgeDirection.getOrientation(iFacing));

        Block tempBlock = world.getBlock(tempBlockPos.x, tempBlockPos.y, tempBlockPos.z);
        BTWBlockadd blockadd = (BTWBlockadd)tempBlock;


        if ( tempBlock != null && blockadd.hasMortar(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z) )
        {
            if ( blockadd.hasContactPointToFullFace(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z, blockadd.getFacing(iFacing^1)) )
            {
                return true;
            }
        }

        return false;
    }
    static public boolean hasNeighborWithMortarInSlabSideContactToFacing(World world, int i, int j, int k, int iFacing, boolean bIsSlabUpsideDown)
    {
        BlockPosition tempBlockPos = new BlockPosition( i, j, k, ForgeDirection.getOrientation(iFacing) );

        Block tempBlock = world.getBlock(tempBlockPos.x, tempBlockPos.y, tempBlockPos.z);
        BTWBlockadd blockadd = (BTWBlockadd)tempBlock;


        if ( tempBlock != null && blockadd.hasMortar(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z) )
        {
            if ( blockadd.hasContactPointToSlabSideFace(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z,
                blockadd.getFacing(iFacing^1), bIsSlabUpsideDown) )
            {
                return true;
            }
        }

        return false;
    }

    static public boolean hasStickySnowNeighborInFullFaceContactToFacing(World world, int i, int j, int k, int iFacing)
    {
        BlockPosition tempBlockPos = new BlockPosition( i, j, k, ForgeDirection.getOrientation(iFacing) );


        Block tempBlock = world.getBlock(tempBlockPos.x, tempBlockPos.y, tempBlockPos.z);
        BTWBlockadd blockadd = (BTWBlockadd)tempBlock;

        if ( tempBlock != null && blockadd.isStickyToSnow(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z) )
        {
            if ( blockadd.hasContactPointToFullFace(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z,
                blockadd.getFacing(iFacing^1)) )
            {
                return true;
            }
        }

        return false;
    }
    static public boolean hasStickySnowNeighborInSlabSideContactToFacing(World world, int i, int j, int k, int iFacing, boolean bIsSlabUpsideDown)
    {
        BlockPosition tempBlockPos = new BlockPosition( i, j, k,ForgeDirection.getOrientation( iFacing) );

        Block tempBlock = world.getBlock(tempBlockPos.x, tempBlockPos.y, tempBlockPos.z);

        BTWBlockadd blockadd = (BTWBlockadd)tempBlock;

        if ( tempBlock != null && blockadd.isStickyToSnow(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z) )
        {
            if ( blockadd.hasContactPointToSlabSideFace(world, tempBlockPos.x, tempBlockPos.y, tempBlockPos.z,
                blockadd.getFacing(iFacing^1), bIsSlabUpsideDown) )
            {
                return true;
            }
        }

        return false;
    }

    public static int rotateFacingForCoordBaseMode(int iFacing, int iCoordBaseMode) {
        if (iCoordBaseMode == 0) {
            if (iFacing == 2) {
                return 3;
            }

            if (iFacing == 3) {
                return 2;
            }
        } else if (iCoordBaseMode == 1) {
            if (iFacing == 2) {
                return 4;
            }

            if (iFacing == 3) {
                return 5;
            }

            if (iFacing == 4) {
                return 2;
            }

            if (iFacing == 5) {
                return 3;
            }
        } else if (iCoordBaseMode == 3) {
            if (iFacing == 2) {
                return 5;
            }

            if (iFacing == 3) {
                return 4;
            }

            if (iFacing == 4) {
                return 2;
            }

            if (iFacing == 5) {
                return 3;
            }
        }

        return iFacing;
    }
    static public boolean doesBlockHaveSmallCenterHardpointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency)
    {
        Block block = blockAccess.getBlock( i, j, k );

        if ( block != null )
        {
            return block.isBlockSolid( blockAccess, i, j, k, iFacing );
        }

        return false;
    }

    static public boolean doesBlockHaveSmallCenterHardpointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing)
    {
        return doesBlockHaveSmallCenterHardpointToFacing(blockAccess, i, j, k, iFacing, false);
    }

    static public boolean doesBlockHaveCenterHardpointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency)
    {
        Block block = blockAccess.getBlock( i, j, k );

        if ( block != null )
        {
            return block.isBlockSolid( blockAccess, i, j, k, iFacing);
        }

        return false;
    }

    static public boolean doesBlockHaveCenterHardpointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing)
    {
        return doesBlockHaveCenterHardpointToFacing(blockAccess, i, j, k, iFacing, false);
    }

    static public boolean doesBlockHaveLargeCenterHardpointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency)
    {
        Block block = blockAccess.getBlock( i, j, k );

        if ( block != null )
        {
            return block.isBlockSolid( blockAccess, i, j, k, iFacing );
        }

        return false;
    }

    static public boolean doesBlockHaveLargeCenterHardpointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing)
    {
        return doesBlockHaveLargeCenterHardpointToFacing(blockAccess, i, j, k, iFacing, false);
    }


    public static void sendPacketToPlayer(NetHandlerPlayServer handler, Packet packet) {
        handler.sendPacket(packet);
    }

    public static MovingObjectPosition rayTraceBlocksAlwaysHitWaterAndLava(World world, Vec3 vec3d, Vec3 vec3d1, boolean flag, boolean flag1)
    {
        if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
        {
            return null;
        }
        if(Double.isNaN(vec3d1.xCoord) || Double.isNaN(vec3d1.yCoord) || Double.isNaN(vec3d1.zCoord))
        {
            return null;
        }
        int i = MathHelper.floor_double(vec3d1.xCoord);
        int j = MathHelper.floor_double(vec3d1.yCoord);
        int k = MathHelper.floor_double(vec3d1.zCoord);
        int l = MathHelper.floor_double(vec3d.xCoord);
        int i1 = MathHelper.floor_double(vec3d.yCoord);
        int j1 = MathHelper.floor_double(vec3d.zCoord);
        Block block = world.getBlock(l, i1, j1);
        int i2 = world.getBlockMetadata(l, i1, j1);
        if((!flag1 || block == null || block.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block != null &&
            (block.canCollideCheck(i2, flag) ||
                block == Blocks.flowing_water || block == Blocks.water ||
                block == Blocks.flowing_lava  || block == Blocks.lava ) )
        {
            MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, l, i1, j1, vec3d, vec3d1);
            if(movingobjectposition != null)
            {
                return movingobjectposition;
            }
        }
        for(int l1 = 200; l1-- >= 0;)
        {
            if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
            {
                return null;
            }
            if(l == i && i1 == j && j1 == k)
            {
                return null;
            }
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            double d = 999D;
            double d1 = 999D;
            double d2 = 999D;
            if(i > l)
            {
                d = (double)l + 1.0D;
            } else
            if(i < l)
            {
                d = (double)l + 0.0D;
            } else
            {
                flag2 = false;
            }
            if(j > i1)
            {
                d1 = (double)i1 + 1.0D;
            } else
            if(j < i1)
            {
                d1 = (double)i1 + 0.0D;
            } else
            {
                flag3 = false;
            }
            if(k > j1)
            {
                d2 = (double)j1 + 1.0D;
            } else
            if(k < j1)
            {
                d2 = (double)j1 + 0.0D;
            } else
            {
                flag4 = false;
            }
            double d3 = 999D;
            double d4 = 999D;
            double d5 = 999D;
            double d6 = vec3d1.xCoord - vec3d.xCoord;
            double d7 = vec3d1.yCoord - vec3d.yCoord;
            double d8 = vec3d1.zCoord - vec3d.zCoord;
            if(flag2)
            {
                d3 = (d - vec3d.xCoord) / d6;
            }
            if(flag3)
            {
                d4 = (d1 - vec3d.yCoord) / d7;
            }
            if(flag4)
            {
                d5 = (d2 - vec3d.zCoord) / d8;
            }
            byte byte0 = 0;
            if(d3 < d4 && d3 < d5)
            {
                if(i > l)
                {
                    byte0 = 4;
                } else
                {
                    byte0 = 5;
                }
                vec3d.xCoord = d;
                vec3d.yCoord += d7 * d3;
                vec3d.zCoord += d8 * d3;
            } else
            if(d4 < d5)
            {
                if(j > i1)
                {
                    byte0 = 0;
                } else
                {
                    byte0 = 1;
                }
                vec3d.xCoord += d6 * d4;
                vec3d.yCoord = d1;
                vec3d.zCoord += d8 * d4;
            } else
            {
                if(k > j1)
                {
                    byte0 = 2;
                } else
                {
                    byte0 = 3;
                }
                vec3d.xCoord += d6 * d5;
                vec3d.yCoord += d7 * d5;
                vec3d.zCoord = d2;
            }
            Vec3 vec3d2 = Vec3.createVectorHelper(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
            l = (int)(vec3d2.xCoord = MathHelper.floor_double(vec3d.xCoord));
            if(byte0 == 5)
            {
                l--;
                vec3d2.xCoord++;
            }
            i1 = (int)(vec3d2.yCoord = MathHelper.floor_double(vec3d.yCoord));
            if(byte0 == 1)
            {
                i1--;
                vec3d2.yCoord++;
            }
            j1 = (int)(vec3d2.zCoord = MathHelper.floor_double(vec3d.zCoord));
            if(byte0 == 3)
            {
                j1--;
                vec3d2.zCoord++;
            }
            Block block1 = world.getBlock(l, i1, j1);
            int k2 = world.getBlockMetadata(l, i1, j1);
            if((!flag1 || block1 == null || block1.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block1 != null &&
                ( block1.canCollideCheck(k2, flag) ||
                    block1 == Blocks.flowing_water || block1 == Blocks.water ||
                    block1 == Blocks.flowing_lava || block1 == Blocks.lava) )
            {
                MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(world, l, i1, j1, vec3d, vec3d1);
                if(movingobjectposition1 != null)
                {
                    return movingobjectposition1;
                }
            }
        }

        return null;
    }

    public static MovingObjectPosition rayTraceBlocksAlwaysHitWaterAndLavaAndFire(World world, Vec3 vec3d, Vec3 vec3d1, boolean flag, boolean flag1)
    {
        if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
        {
            return null;
        }
        if(Double.isNaN(vec3d1.xCoord) || Double.isNaN(vec3d1.yCoord) || Double.isNaN(vec3d1.zCoord))
        {
            return null;
        }
        int i = MathHelper.floor_double(vec3d1.xCoord);
        int j = MathHelper.floor_double(vec3d1.yCoord);
        int k = MathHelper.floor_double(vec3d1.zCoord);
        int l = MathHelper.floor_double(vec3d.xCoord);
        int i1 = MathHelper.floor_double(vec3d.yCoord);
        int j1 = MathHelper.floor_double(vec3d.zCoord);
        Block block = world.getBlock(l, i1, j1);
        int i2 = world.getBlockMetadata(l, i1, j1);
        if((!flag1 || block == null || block.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block != null &&
            (block.canCollideCheck(i2, flag) ||
                block == Blocks.flowing_water || block == Blocks.water ||
                block == Blocks.flowing_lava  || block == Blocks.lava  ||
                block == Blocks.fire))  // || block == BTWBlocks.stokedFire.blockID ) )
        {
            MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, l, i1, j1, vec3d, vec3d1);
            if(movingobjectposition != null)
            {
                return movingobjectposition;
            }
        }
        for(int l1 = 200; l1-- >= 0;)
        {
            if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
            {
                return null;
            }
            if(l == i && i1 == j && j1 == k)
            {
                return null;
            }
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            double d = 999D;
            double d1 = 999D;
            double d2 = 999D;
            if(i > l)
            {
                d = (double)l + 1.0D;
            } else
            if(i < l)
            {
                d = (double)l + 0.0D;
            } else
            {
                flag2 = false;
            }
            if(j > i1)
            {
                d1 = (double)i1 + 1.0D;
            } else
            if(j < i1)
            {
                d1 = (double)i1 + 0.0D;
            } else
            {
                flag3 = false;
            }
            if(k > j1)
            {
                d2 = (double)j1 + 1.0D;
            } else
            if(k < j1)
            {
                d2 = (double)j1 + 0.0D;
            } else
            {
                flag4 = false;
            }
            double d3 = 999D;
            double d4 = 999D;
            double d5 = 999D;
            double d6 = vec3d1.xCoord - vec3d.xCoord;
            double d7 = vec3d1.yCoord - vec3d.yCoord;
            double d8 = vec3d1.zCoord - vec3d.zCoord;
            if(flag2)
            {
                d3 = (d - vec3d.xCoord) / d6;
            }
            if(flag3)
            {
                d4 = (d1 - vec3d.yCoord) / d7;
            }
            if(flag4)
            {
                d5 = (d2 - vec3d.zCoord) / d8;
            }
            byte byte0 = 0;
            if(d3 < d4 && d3 < d5)
            {
                if(i > l)
                {
                    byte0 = 4;
                } else
                {
                    byte0 = 5;
                }
                vec3d.xCoord = d;
                vec3d.yCoord += d7 * d3;
                vec3d.zCoord += d8 * d3;
            } else
            if(d4 < d5)
            {
                if(j > i1)
                {
                    byte0 = 0;
                } else
                {
                    byte0 = 1;
                }
                vec3d.xCoord += d6 * d4;
                vec3d.yCoord = d1;
                vec3d.zCoord += d8 * d4;
            } else
            {
                if(k > j1)
                {
                    byte0 = 2;
                } else
                {
                    byte0 = 3;
                }
                vec3d.xCoord += d6 * d5;
                vec3d.yCoord += d7 * d5;
                vec3d.zCoord = d2;
            }
            Vec3 vec3d2 = Vec3.createVectorHelper(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
            l = (int)(vec3d2.xCoord = MathHelper.floor_double(vec3d.xCoord));
            if(byte0 == 5)
            {
                l--;
                vec3d2.xCoord++;
            }
            i1 = (int)(vec3d2.yCoord = MathHelper.floor_double(vec3d.yCoord));
            if(byte0 == 1)
            {
                i1--;
                vec3d2.yCoord++;
            }
            j1 = (int)(vec3d2.zCoord = MathHelper.floor_double(vec3d.zCoord));
            if(byte0 == 3)
            {
                j1--;
                vec3d2.zCoord++;
            }
            Block block1 = world.getBlock(l, i1, j1);
            int k2 = world.getBlockMetadata(l, i1, j1);
            if((!flag1 || block1 == null || block1.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block1 != null &&
                (block1.canCollideCheck(i2, flag) ||
                    block1 == Blocks.flowing_water || block1 == Blocks.water ||
                    block1 == Blocks.flowing_lava  || block1 == Blocks.lava  ||
                    block1 == Blocks.fire))  // || block1 == BTWBlocks.stokedFire.blockID ) )
            {
                MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(world, l, i1, j1, vec3d, vec3d1);
                if(movingobjectposition1 != null)
                {
                    return movingobjectposition1;
                }
            }
        }

        return null;
    }

}


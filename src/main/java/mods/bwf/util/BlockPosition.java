package mods.bwf.util;

import net.minecraftforge.common.util.ForgeDirection;

public class BlockPosition {
    public int x;
    public int y;
    public int z;
    public ForgeDirection dir;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dir = ForgeDirection.UNKNOWN;
    }

    public BlockPosition(int x, int y, int z, ForgeDirection dir) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dir = dir;
    }

    public BlockPosition(int x, int y, int z, ForgeDirection dir, boolean offsetDir) {
        this(x, y, z, dir);
        if (offsetDir) {
            this.step(dir);
        }

    }

    public BlockPosition(BlockPosition p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
        this.dir = p.dir;
    }

    public BlockPosition step(ForgeDirection dir) {
        this.x += dir.offsetX;
        this.y += dir.offsetY;
        this.z += dir.offsetZ;
        return this;
    }
}

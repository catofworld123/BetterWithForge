package mods.bwf.util;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RayUtils {
    private World world;
    private int x;
    private int y;
    private int z;
    private Vec3 start;
    private Vec3 end;
    List<MovingObjectPosition> intersections = new ArrayList();

    public RayUtils(World world, int x, int y, int z, Vec3 start, Vec3 end) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.start = Vec3.createVectorHelper(start.xCoord, start.yCoord, start.zCoord);
        this.end = Vec3.createVectorHelper(end.xCoord, end.yCoord, end.zCoord);
    }

    public void addBoxWithLocalCoordsToIntersectionList(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        Vec3 start = this.start.addVector((double)(-this.x), (double)(-this.y), (double)(-this.z));
        Vec3 end = this.end.addVector((double)(-this.x), (double)(-this.y), (double)(-this.z));
        Vec3 boxMin = Vec3.createVectorHelper(minX, minY, minZ);
        Vec3 boxMax = Vec3.createVectorHelper(maxX, maxY, maxZ);
        Vec3 xMin = start.getIntermediateWithXValue(end, boxMin.xCoord);
        Vec3 xMax = start.getIntermediateWithXValue(end, boxMax.xCoord);
        Vec3 yMin = start.getIntermediateWithYValue(end, boxMin.yCoord);
        Vec3 yMax = start.getIntermediateWithYValue(end, boxMax.yCoord);
        Vec3 zMin = start.getIntermediateWithZValue(end, boxMin.zCoord);
        Vec3 zMax = start.getIntermediateWithZValue(end, boxMax.zCoord);
        if (!this.isVecInsideYZBounds(xMin, boxMin, boxMax)) {
            xMin = null;
        }

        if (!this.isVecInsideYZBounds(xMax, boxMin, boxMax)) {
            xMax = null;
        }

        if (!this.isVecInsideXZBounds(yMin, boxMin, boxMax)) {
            yMin = null;
        }

        if (!this.isVecInsideXZBounds(yMax, boxMin, boxMax)) {
            yMax = null;
        }

        if (!this.isVecInsideYZBounds(zMin, boxMin, boxMax)) {
            zMin = null;
        }

        if (!this.isVecInsideYZBounds(zMax, boxMin, boxMax)) {
            zMax = null;
        }

        Vec3 vec = null;
        if (xMin != null && (vec == null || start.squareDistanceTo(xMin) < start.squareDistanceTo(vec))) {
            vec = xMin;
        }

        if (xMax != null && (vec == null || start.squareDistanceTo(xMax) < start.squareDistanceTo(vec))) {
            vec = xMax;
        }

        if (yMin != null && (vec == null || start.squareDistanceTo(yMin) < start.squareDistanceTo(vec))) {
            vec = yMin;
        }

        if (yMax != null && (vec == null || start.squareDistanceTo(yMax) < start.squareDistanceTo(vec))) {
            vec = yMax;
        }

        if (zMin != null && (vec == null || start.squareDistanceTo(zMin) < start.squareDistanceTo(vec))) {
            vec = zMin;
        }

        if (zMax != null && (vec == null || start.squareDistanceTo(zMax) < start.squareDistanceTo(vec))) {
            vec = zMax;
        }

        if (vec != null) {
            byte b = -1;
            if (vec == xMin) {
                b = 4;
            }

            if (vec == xMax) {
                b = 5;
            }

            if (vec == yMin) {
                b = 0;
            }

            if (vec == yMax) {
                b = 1;
            }

            if (vec == zMin) {
                b = 2;
            }

            if (vec == zMax) {
                b = 3;
            }

            this.intersections.add(new MovingObjectPosition(this.x, this.y, this.z, b, vec.addVector((double)this.x, (double)this.y, (double)this.z)));
        }

    }

    public MovingObjectPosition getFirstIntersection() {
        MovingObjectPosition firstIntersect = null;
        Iterator<MovingObjectPosition> it = this.intersections.iterator();
        double maxDistance = (double)0.0F;

        while(it.hasNext()) {
            MovingObjectPosition pos = (MovingObjectPosition)it.next();
            double currentDistance = pos.hitVec.squareDistanceTo(this.end);
            if (currentDistance > maxDistance) {
                firstIntersect = pos;
                maxDistance = currentDistance;
            }
        }

        return firstIntersect;
    }

    private boolean isVecInsideYZBounds(Vec3 vec, Vec3 min, Vec3 max) {
        if (vec == null) {
            return false;
        } else {
            return vec.yCoord >= min.yCoord && vec.yCoord <= max.yCoord && vec.zCoord >= min.zCoord && vec.zCoord <= max.zCoord;
        }
    }

    private boolean isVecInsideXZBounds(Vec3 vec, Vec3 min, Vec3 max) {
        if (vec == null) {
            return false;
        } else {
            return vec.xCoord >= min.xCoord && vec.xCoord <= max.xCoord && vec.zCoord >= min.zCoord && vec.zCoord <= max.zCoord;
        }
    }

    private boolean isVecInsideXYBounds(Vec3 vec, Vec3 min, Vec3 max) {
        if (vec == null) {
            return false;
        } else {
            return vec.xCoord >= min.xCoord && vec.xCoord <= max.xCoord && vec.yCoord >= min.yCoord && vec.yCoord <= max.yCoord;
        }
    }
}

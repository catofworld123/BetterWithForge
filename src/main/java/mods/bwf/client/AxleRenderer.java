package mods.bwf.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mods.bwf.BWFRegistry;
import mods.bwf.block.AxleBlock;
import mods.bwf.proxy.ClientProxy;
import mods.bwf.util.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import static mods.bwf.client.RenderTileEntities.renderItemBlock;

public class AxleRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        ItemStack stack = new ItemStack(block);
        RenderUtils renderUtils = new RenderUtils();
        renderer.setRenderBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
        renderUtils.setUVRotateEast(0,renderer);
        renderUtils.setUVRotateWest(0,renderer);
        renderUtils.setUVRotateSouth(0,renderer);
        renderUtils.setUVRotateNorth(3,renderer);
        renderUtils.setUVRotateTop(2,renderer);
        renderUtils.setUVRotateBottom(2,renderer);
        RenderUtils.renderInvBlockWithMetadata(renderer, block, -0.5F, -0.5F, -0.5F, stack.getItemDamage());
        renderUtils.clearUVRotation(renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer) {
        AxleBlock axle = (AxleBlock) world.getBlock(i,j,k);
        RenderUtils renderUtils = new RenderUtils();
        IBlockAccess blockAccess = renderer.blockAccess;
        int iAlignment = axle.getAxisAlignment(blockAccess, i, j, k);
        if (iAlignment == 0) {
            renderUtils.setUVRotateEast(1,renderer);
            renderUtils.setUVRotateWest(1,renderer);
            renderUtils.setUVRotateSouth(1,renderer);
            renderUtils.setUVRotateNorth(1,renderer);
            renderUtils.setUVRotateTop(0,renderer);
            renderUtils.setUVRotateBottom(0,renderer);
        } else if (iAlignment == 1) {
            renderUtils.setUVRotateEast(0,renderer);
            renderUtils.setUVRotateWest(0,renderer);
            renderUtils.setUVRotateSouth(0,renderer);
            renderUtils.setUVRotateNorth(3,renderer);
            renderUtils.setUVRotateTop(2,renderer);
            renderUtils.setUVRotateBottom(2,renderer);
        } else {
            renderUtils.setUVRotateEast(0,renderer);
            renderUtils.setUVRotateWest(3,renderer);
            renderUtils.setUVRotateSouth(0,renderer);
            renderUtils.setUVRotateNorth(0,renderer);
            renderUtils.setUVRotateTop(3,renderer);
            renderUtils.setUVRotateBottom(0,renderer);
        }
        if (axle.clientCheckIfPowered(blockAccess, i, j, k)) {
            axle.isPowerOnForCurrentRender = true;
        } else {
            axle.isPowerOnForCurrentRender = false;
        }
        renderer.setRenderBounds(axle.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k).minX,axle.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k).minY,axle.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k).minZ,axle.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k).maxX,axle.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k).maxY,axle.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k).maxZ);
        renderer.renderStandardBlock(axle, i, j, k);
        renderUtils.clearUVRotation(renderer);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.renderAxle;
    }
}

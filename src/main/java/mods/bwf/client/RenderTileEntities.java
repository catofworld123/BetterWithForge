package mods.bwf.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import mods.bwf.BetterWithForge;
import mods.bwf.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderTileEntities implements ISimpleBlockRenderingHandler {
    public RenderTileEntities() {
    }

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        Tessellator tes = Tessellator.instance;
        block.getIcon(0, metadata);
        block.getIcon(4, metadata);
        block.getIcon(2, metadata);
        CommonProxy var10001 = BetterWithForge.proxy;
        if (modelId == CommonProxy.simpleTileRenderer) {
            switch (metadata) {
                case 0:
                case 1:
                case 5:
                case 6:
                    renderItemBlock(block, renderer, metadata);
                    break;
                case 2:
                case 3:
                    renderItemVessel(block, renderer, metadata);
                    break;
                case 4:
                    this.renderHopper(block, renderer, metadata);
            }
        }

    }

    public static void renderItemBlockColored(Block block, RenderBlocks render, int meta, float r, float g, float b) {
        GL11.glColor4f(r, g, b, 1.0F);
        render.colorRedTopLeft *= r;
        render.colorRedTopRight *= r;
        render.colorRedBottomLeft *= r;
        render.colorRedBottomRight *= r;
        render.colorGreenTopLeft *= g;
        render.colorGreenTopRight *= g;
        render.colorGreenBottomLeft *= g;
        render.colorGreenBottomRight *= g;
        render.colorBlueTopLeft *= b;
        render.colorBlueTopRight *= b;
        render.colorBlueBottomLeft *= b;
        render.colorBlueBottomRight *= b;
        renderItemBlock(block, render, meta);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void renderItemBlock(Block block, RenderBlocks render, int meta) {
        block.setBlockBoundsForItemRender();
        Tessellator tes = Tessellator.instance;
        IIcon bTex = block.getIcon(0, meta);
        IIcon tTex = block.getIcon(1, meta);
        IIcon ewTex = block.getIcon(4, meta);
        IIcon nsTex = block.getIcon(2, meta);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tes.startDrawingQuads();
        tes.setNormal(0.0F, -1.0F, 0.0F);
        render.renderFaceYNeg(block, (double)0.0F, (double)0.0F, (double)0.0F, bTex);
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 1.0F, 0.0F);
        render.renderFaceYPos(block, (double)0.0F, (double)0.0F, (double)0.0F, tTex);
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, -1.0F);
        render.renderFaceXPos(block, (double)0.0F, (double)0.0F, (double)0.0F, ewTex);
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(0.0F, 0.0F, 1.0F);
        render.renderFaceXNeg(block, (double)0.0F, (double)0.0F, (double)0.0F, ewTex);
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(-1.0F, 0.0F, 0.0F);
        render.renderFaceZPos(block, (double)0.0F, (double)0.0F, (double)0.0F, nsTex);
        tes.draw();
        tes.startDrawingQuads();
        tes.setNormal(1.0F, 0.0F, 0.0F);
        render.renderFaceZNeg(block, (double)0.0F, (double)0.0F, (double)0.0F, nsTex);
        tes.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static void renderItemVessel(Block block, RenderBlocks render, int meta) {
        renderHollowBox(block, render, meta, (double)0.1875F, (double)0.0625F, (double)0.0F, (double)1.0F);
        renderHollowBox(block, render, meta, (double)0.125F, (double)0.0F, (double)0.125F, (double)0.875F);
        render.setRenderBounds((double)0.125F, -1.0E-4, (double)0.125F, (double)0.875F, 0.2501, (double)0.875F);
        renderItemBlock(block, render, meta);
    }

    public void renderHopper(Block block, RenderBlocks render, int meta) {
        renderHollowBox(block, render, meta, (double)0.125F, (double)0.0F, 0.255, (double)1.0F);
        render.setRenderBounds(1.0E-4, (double)0.25F, 1.0E-4, 0.9999, 0.377, 0.9999);
        renderItemBlock(block, render, meta);
        render.setRenderBounds((double)0.3125F, (double)0.0F, (double)0.3125F, (double)0.6875F, (double)0.25F, (double)0.6875F);
        renderItemBlock(block, render, meta);
    }

    public static void renderHollowBox(Block block, RenderBlocks render, int meta, double inner, double outer, double minY, double maxY) {
        render.setRenderBounds((double)1.0F - inner, minY, outer, (double)1.0F - outer, maxY, (double)1.0F - outer);
        renderItemBlock(block, render, meta);
        render.setRenderBounds(outer, minY, outer, inner, maxY, (double)1.0F - outer);
        renderItemBlock(block, render, meta);
        render.setRenderBounds(outer, minY, (double)1.0F - inner, (double)1.0F - outer, maxY, (double)1.0F - outer);
        renderItemBlock(block, render, meta);
        render.setRenderBounds(outer, minY, outer, (double)1.0F - outer, maxY, inner);
        renderItemBlock(block, render, meta);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        CommonProxy var10001 = BetterWithForge.proxy;
        if (modelId == CommonProxy.simpleTileRenderer) {
            switch (meta) {
                case 2:
                case 10:
                    //return this.renderCrucible(world, x, y, z, renderer);
                case 3:
                case 11:
                   // return this.renderCauldron(world, x, y, z, renderer);
                case 4:
                case 12:
                   // return this.renderHopper(world, x, y, z, renderer);
                case 5:
                case 6:
                case 13:
                case 14:
                   // return this.renderTurntable(world, x, y, z, renderer);
                case 7:
                case 8:
                case 9:
                default:
                    return renderer.renderStandardBlock(block, x, y, z);
            }
        } else {
            return false;
        }
    }



    public static boolean renderCooker(IBlockAccess world, int x, int y, int z, RenderBlocks render) {
        Block block = world.getBlock(x, y, z);
        renderHollowBox(block, x, y, z, render, (double)0.1875F, (double)0.0625F, (double)0.0F, (double)1.0F);
        renderHollowExtension(block, x, y, z, render, (double)0.0625F, (double)0.0F, (double)1.0F);
        renderHollowBox(block, x, y, z, render, (double)0.125F, (double)0.0F, (double)0.125F, (double)0.875F);
        renderHollowExtension(block, x, y, z, render, (double)0.0F, (double)0.125F, (double)0.875F);
        render.setRenderBounds((double)0.125F, -1.0E-4, (double)0.125F, (double)0.875F, 0.2501, (double)0.875F);
        render.renderStandardBlock(block, x, y, z);
        return true;
    }



    public static void renderHollowBox(Block block, int x, int y, int z, RenderBlocks render, double inner, double outer, double minY, double maxY) {
        render.setRenderBounds((double)1.0F - inner, minY, outer, (double)1.0F - outer, maxY, (double)1.0F - outer);
        render.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
        render.setRenderBounds(outer, minY, outer, inner, maxY, (double)1.0F - outer);
        render.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
        render.setRenderBounds(outer, minY, (double)1.0F - inner, (double)1.0F - outer, maxY, (double)1.0F - outer);
        render.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
        render.setRenderBounds(outer, minY, outer, (double)1.0F - outer, maxY, inner);
        render.renderStandardBlockWithColorMultiplier(block, x, y, z, 1.0F, 1.0F, 1.0F);
    }

    public static void renderHollowExtension(Block block, int x, int y, int z, RenderBlocks render, double outer, double minY, double maxY) {
        renderHollowBox(block, x, y, z, render, outer, outer - 1.0E-4, minY, maxY);
    }



    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return CommonProxy.simpleTileRenderer;
    }
}

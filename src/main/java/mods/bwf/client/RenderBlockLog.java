package mods.bwf.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.bwf.BWFRegistry;
import mods.bwf.block.BlockLogCustom;
import mods.bwf.block.CampfireBlock;
import mods.bwf.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderBlockLog implements ISimpleBlockRenderingHandler {


    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.renderBlockAsItem(Blocks.log,metadata + 1,1.0f );
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        BlockLogCustom blockLogCustom = (BlockLogCustom)(world.getBlock( x, y, z ));
        boolean bReturnValue = blockLogCustom.renderBlock(renderer, x, y, z);
        blockLogCustom.renderBlockSecondPass(renderer,x,y,z,bReturnValue);
        return blockLogCustom.renderBlock(renderer, x, y , z);
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ClientProxy.renderBlockLog;
    }
}

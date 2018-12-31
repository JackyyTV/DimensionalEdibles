package jackyy.dimensionaledibles.event;

import org.lwjgl.opengl.GL11;

import jackyy.dimensionaledibles.block.BlockCustomCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomCakeNameDisplayEvent {

    @SubscribeEvent
    public void onNameRender(DrawBlockHighlightEvent e) {
	BlockPos pos = e.getTarget().getBlockPos();
	if (e.getPlayer().getEntityWorld().getBlockState(pos).getBlock() instanceof BlockCustomCake) {
	    IBlockState cake = e.getPlayer().getEntityWorld().getBlockState(pos);
	    String dimName = "ERROR Unconfigured!";
	    double x = pos.getX() + 0.5;
	    double y = pos.getY() + 2;
	    double z = pos.getZ() + 0.5;
	    System.out.println(pos);
	    renderNameTag(dimName, x, y, z);
	}
    }

    private void renderNameTag(String name, double x, double y, double z) {
	float scale = 0.02666667F;
	float height = 0.8F;
	GlStateManager.pushMatrix();
	GlStateManager.translate(x, y + height + 0.5F, z);
	GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
	GlStateManager.scale(-scale, -scale, scale);
	GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
	GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
	GlStateManager.disableLighting();
	GlStateManager.depthMask(false);
	GlStateManager.disableDepth();
	GlStateManager.enableBlend();
	GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder vertexbuffer = tessellator.getBuffer();
	GlStateManager.disableTexture2D();
	vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
	FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
	int width = fontrenderer.getStringWidth(name) / 2;
	vertexbuffer.pos(x - width - 1, y - 1, z).color(0F, 0F, 0, 0.25F).endVertex();
	vertexbuffer.pos(x - width - 1, y + 8, z).color(0F, 0F, 0, 0.25F).endVertex();
	vertexbuffer.pos(x + width + 1, y + 8, z).color(0F, 0F, 0, 0.25F).endVertex();
	vertexbuffer.pos(x + width + 1, y - 1, z).color(0F, 0F, 0, 0.25F).endVertex();
	tessellator.draw();
	GlStateManager.enableTexture2D();
	fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, 553648127);
	GlStateManager.enableDepth();
	GlStateManager.depthMask(true);
	fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, 0, -1);
	GlStateManager.enableLighting();
	GlStateManager.disableBlend();
	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	GlStateManager.popMatrix();
    }
}

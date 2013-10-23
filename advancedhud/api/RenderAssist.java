package advancedhud.api;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * Some methods which are usually in GuiIngame, but since we don't have<br>
 * direct access when rendering in HudItem, you may need to use these.
 * 
 * @author maxpowa
 * 
 */
public class RenderAssist {

    /**
     * Controls render "level" for layering textures overtop one another.
     */
    public static float zLevel;

    /**
     * Draws a textured rectangle at the stored z-value.
     * 
     * @param x
     *            X-Axis position to render the sprite into the GUI.
     * @param y
     *            Y-Axis position to render the sprite into the GUI.
     * @param u
     *            X-Axis position on the spritesheet which this sprite is found.
     * @param v
     *            Y-Axis position on the spritesheet which this sprite is found.
     * @param width
     *            Width to render the sprite.
     * @param height
     *            Height to render the sprite.
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v,
            int width, int height) {
        float f = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, RenderAssist.zLevel,
                (u + 0) * f, (v + height) * f);
        tessellator.addVertexWithUV(x + width, y + height, RenderAssist.zLevel,
                (u + width) * f, (v + height) * f);
        tessellator.addVertexWithUV(x + width, y + 0, RenderAssist.zLevel,
                (u + width) * f, (v + 0) * f);
        tessellator.addVertexWithUV(x + 0, y + 0, RenderAssist.zLevel, (u + 0)
                * f, (v + 0) * f);
        tessellator.draw();
    }

    public static void bindTexture(ResourceLocation res) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
    }

    /**
     * Binds a texture, similar to the way renderEngine.bindTexture(String str)
     * used to work.
     * 
     * @param textureLocation
     *            Path to location, you should know how to format this.
     */
    public static void bindTexture(String textureLocation) {
        ResourceLocation res = new ResourceLocation(textureLocation);
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
    }
    
    /**
     * Draws a solid color rectangle with the specified coordinates and color.
     * 
     * @param x1    First X value
     * @param x2    Second X value
     * @param y1    First Y value
     * @param y2    Second Y Value
     */
    public static void drawRect(int x1, int y1, int x2, int y2, int color)
    {
        int j1;

        if (x1 < x2)
        {
            j1 = x1;
            x1 = x2;
            x2 = j1;
        }

        if (y1 < y2)
        {
            j1 = y1;
            y1 = y2;
            y2 = j1;
        }

        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(f1, f2, f3, f);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double)x1, (double)y2, 0.0D);
        tessellator.addVertex((double)x2, (double)y2, 0.0D);
        tessellator.addVertex((double)x2, (double)y1, 0.0D);
        tessellator.addVertex((double)x1, (double)y1, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * Renders the specified item of the inventory slot at the specified
     * location.
     */
    public static void renderInventorySlot(int slot, int x, int y,
            float partialTick, Minecraft mc) {
        RenderItem itemRenderer = new RenderItem();
        ItemStack itemstack = mc.thePlayer.inventory.mainInventory[slot];
        x += 91;
        y += 12;

        if (itemstack != null) {
            float f1 = itemstack.animationsToGo - partialTick;

            if (f1 > 0.0F) {
                GL11.glPushMatrix();
                float f2 = 1.0F + f1 / 5.0F;
                GL11.glTranslatef(x + 8, y + 12, 0.0F);
                GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef(-(x + 8), -(y + 12), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer,
                    mc.getTextureManager(), itemstack, x, y);

            if (f1 > 0.0F) {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer,
                    mc.getTextureManager(), itemstack, x, y);
        }
    }

}

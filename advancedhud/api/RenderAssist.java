package advancedhud.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
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
        Minecraft.getMinecraft().func_110434_K().func_110577_a(res);
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
        Minecraft.getMinecraft().func_110434_K().func_110577_a(res);
    }

}

package advancedhud;

import advancedhud.ahuditem.HudItem;
import advancedhud.button.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public final class RenderHelper
{
  public static void bindTexture(String string)
  {
    AHud.getMinecraftInstance().renderEngine.bindTexture(string);
  }

  public static void drawSprite(int x, int y, int spriteX, int spriteY, int spriteWidth, int spriteHeight) {
    drawSprite(x, y, spriteX, spriteY, spriteWidth, spriteHeight, 0);
  }

  public static void drawSprite(int x, int y, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int zLevel) {
    drawSpriteUV(x, y, spriteWidth, spriteHeight, spriteX / 256.0F, spriteY / 256.0F, (spriteX + spriteWidth) / 256.0F, (spriteY + spriteHeight) / 256.0F, zLevel);
  }

  public static void drawSprite(int x, int y, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int texWidth, int texHeight, int zLevel) {
    drawSpriteUV(x, y, spriteWidth, spriteHeight, spriteX / texWidth, spriteY / texHeight, (spriteX + spriteWidth) / texWidth, (spriteY + spriteHeight) / texHeight, zLevel);
  }

  public static void drawSpriteUV(int x, int y, int width, int height, float u1, float v1, float u2, float v2, int zLevel) {
    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV(x, y + height, zLevel, u1, v2);
    tessellator.addVertexWithUV(x + width, y + height, zLevel, u2, v2);
    tessellator.addVertexWithUV(x + width, y, zLevel, u2, v1);
    tessellator.addVertexWithUV(x, y, zLevel, u1, v1);
    tessellator.draw();
  }

  public static void drawRectangle(int x, int y, int width, int height, int color) {
    drawRectangle(x, y, width, height, color, color, false);
  }

  public static void drawRectangle(int x, int y, int width, int height, int color, int color2, boolean fadeVertical) {
    float colorR = (color2 >> 24 & 0xFF) / 255.0F;
    float colorG = (color2 >> 16 & 0xFF) / 255.0F;
    float colorB = (color2 >> 8 & 0xFF) / 255.0F;
    float colorA = (color2 & 0xFF) / 255.0F;

    float color2R = (color >> 24 & 0xFF) / 255.0F;
    float color2G = (color >> 16 & 0xFF) / 255.0F;
    float color2B = (color >> 8 & 0xFF) / 255.0F;
    float color2A = (color & 0xFF) / 255.0F;

    GL11.glDisable(3553);
    GL11.glShadeModel(7425);

    Tessellator tessellator = Tessellator.instance;

    tessellator.startDrawingQuads();

    tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
    tessellator.addVertex(x, y + height, 0.0D);

    if (!fadeVertical) {
      tessellator.setColorRGBA_F(color2R, color2G, color2B, color2A);
    }
    tessellator.addVertex(x + width, y + height, 0.0D);

    if (fadeVertical) {
      tessellator.setColorRGBA_F(color2R, color2G, color2B, color2A);
    }
    tessellator.addVertex(x + width, y, 0.0D);

    if (!fadeVertical) {
      tessellator.setColorRGBA_F(colorR, colorG, colorB, colorA);
    }
    tessellator.addVertex(x, y, 0.0D);

    tessellator.draw();

    GL11.glEnable(3553);
    GL11.glShadeModel(7424);
  }

  public static void drawHeader(String caption, HudItem hudItem) {
    GL11.glEnable(3042);
    drawRectangle(0, 0, AHud.screenWidth, 40, 128);
    drawRectangle(0, 40, AHud.screenWidth, 2, 64, 0, true);
    FontRenderer fontRenderer = AHud.getMinecraftInstance().fontRenderer;
    fontRenderer.drawString(caption, 34, 16, 16777215);

    if (hudItem == null)
      fontRenderer.drawString("Advanced Hud v4.8.1 by TurboSlow -- Updated by maxpowa", 5, AHud.screenHeight - 2 - fontRenderer.FONT_HEIGHT, 14737632);
    else
      hudItem.drawIcon(12, 12);
  }

  public static void drawToolTip(Button button, int mouseX, int mouseY)
  {
    if ((button.toolTip != null) && (button.isMouseOver(mouseX, mouseY))) {
      mouseX += 12;
      mouseY -= 4;

      FontRenderer fontRenderer = AHud.getMinecraftInstance().fontRenderer;
      int height = fontRenderer.splitStringWidth(button.toolTip, 150);
      drawRectangle(mouseX - 3, mouseY - 3, 153, height + 4, 187);
      fontRenderer.drawSplitString(button.toolTip, mouseX, mouseY, 150, -1);
    }
  }
}
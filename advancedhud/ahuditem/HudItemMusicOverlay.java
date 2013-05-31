package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.src.ModLoader;
import org.lwjgl.opengl.GL11;

public class HudItemMusicOverlay extends HudItem
{
  public void render(float f)
  {
    Minecraft mc = AHud.getMinecraftInstance();
    String recordPlayingMessage = "";
    float recordPlayingUpFor = 0.0F;
    try
    {
      recordPlayingMessage = (String)ModLoader.getPrivateValue(GuiIngame.class, mc.ingameGUI, 5);
      recordPlayingUpFor = ((Integer)ModLoader.getPrivateValue(GuiIngame.class, mc.ingameGUI, 6)).intValue() - f;
    } catch (Exception e) {
      e.printStackTrace();
      AHud.disableHudItem(this);
      mc.ingameGUI.getChatGUI().printChatMessage("Something went wrong while rendering the music overlay. Is your hud's base class modded?");
      return;
    }

    if (recordPlayingUpFor > 0.0F) {
      int alpha = (int)(recordPlayingUpFor * 256.0F / 20.0F);

      if (alpha > 255) {
        alpha = 255;
      }

      if (alpha > 2) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        int color = 16777215;

        if (alpha > 1) {
          color = Color.HSBtoRGB(recordPlayingUpFor / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
        }

        mc.fontRenderer.drawString(recordPlayingMessage, posX - mc.fontRenderer.getStringWidth(recordPlayingMessage) / 2 + getWidth() / 2, posY + 1, color + (alpha << 24));
        GL11.glDisable(3042);
      }
    }
  }

  public String getName()
  {
    return "Music Overlay";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 - getWidth() / 2;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 53;
  }

  public int getWidth()
  {
    return 135;
  }

  public int getHeight()
  {
    return 10;
  }

  public void drawIcon(int posX, int posY) {
    RenderHelper.bindTexture("/gui/items.png");
    RenderHelper.drawSprite(posX, posY, 144, 240, 16, 16);
  }
}
package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class HudItemLvlCounter extends HudItem
{
  public void render(float f)
  {
    Minecraft mc = AHud.getMinecraftInstance();
    if ((mc.playerController.func_78763_f()) && (mc.thePlayer.experienceLevel > 0))
    {
      int alpha = (int)(255.0F * DefaultHudItems.xpBar.properties.getFloat("alpha"));
      if (alpha < 2) {
        return;
      }
      alpha <<= 24;

      String lvl = mc.thePlayer.experienceLevel + "";

      int x = posX + 8 - mc.fontRenderer.getStringWidth(lvl) / 2;
      int y = posY + 1;

      mc.fontRenderer.drawString(lvl, x + 1, y, alpha);
      mc.fontRenderer.drawString(lvl, x - 1, y, alpha);
      mc.fontRenderer.drawString(lvl, x, y + 1, alpha);
      mc.fontRenderer.drawString(lvl, x, y - 1, alpha);
      mc.fontRenderer.drawString(lvl, x, y, 8453920 + alpha);
    }
  }

  public String getName()
  {
    return "Level counter";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 - 7;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 36;
  }

  public int getWidth()
  {
    return 15;
  }

  public int getHeight()
  {
    return 9;
  }

  public void drawIcon(int posX, int posY) {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 32, 16, 16, 16);
  }
}
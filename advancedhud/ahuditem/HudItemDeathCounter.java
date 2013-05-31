package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import advancedhud.button.ButtonUpDown;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;

public class HudItemDeathCounter extends HudItem
{
  protected ButtonUpDown buttonOffset = new ButtonUpDown(2, "Offset");
  protected boolean isPlayerDead;

  public void render(float f)
  {
    Minecraft mc = AHud.getMinecraftInstance();
    if (mc.playerController.func_78763_f())
    {
      int alpha = -16777216;

      String deaths = "Death Counter: " + buttonOffset.value;

      int x = posX;
      int y = posY + 1;

      mc.fontRenderer.drawString(deaths, x + 1, y + 1, alpha);
      mc.fontRenderer.drawString(deaths, x, y, 13647422 + alpha);
    }
  }

  public void tick()
  {
    if ((AHud.getMinecraftInstance().thePlayer.getHealth() <= 0) && (!isPlayerDead)) {
      buttonOffset.value += 1;
      isPlayerDead = true;
    }

    if ((isPlayerDead) && (AHud.getMinecraftInstance().thePlayer.getHealth() > 0))
      isPlayerDead = false;
  }

  public List getButtonList()
  {
    List buttonList = new ArrayList();

    buttonList.addAll(super.getButtonList());
    buttonList.add(buttonOffset);

    return buttonList;
  }

  public String getName()
  {
    return "Death Counter";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.TOPRIGHT;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth - getWidth() - 30;
  }

  public int getDefaultPosY()
  {
    return 10;
  }

  public int getWidth()
  {
    if (buttonOffset == null) {
      return 60;
    }
    return AHud.getMinecraftInstance().fontRenderer.getStringWidth("Death Counter: " + buttonOffset.value);
  }

  public int getHeight()
  {
    return 9;
  }

  public boolean isEnabledByDefault()
  {
    return false;
  }

  public void drawIcon(int posX, int posY) {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 96, 16, 16, 16);
  }
}
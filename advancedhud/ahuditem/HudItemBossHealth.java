package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import advancedhud.button.ButtonToggle;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.boss.BossStatus;

public class HudItemBossHealth extends HudItem
{
  protected ButtonToggle buttonLabel = new ButtonToggle(2, "Label", true);

  public void render(float f)
  {
    if ((BossStatus.bossName == null) || (BossStatus.statusBarLength <= 0)) {
      return;
    }

    BossStatus.statusBarLength -= 1;

    int spriteWidth = (int)(BossStatus.healthScale * (getWidth() + 1));

    RenderHelper.bindTexture("/gui/icons.png");
    RenderHelper.drawSprite(posX, posY + getHeight() - 5, 0, 74, getWidth(), 5);
    if (spriteWidth > 0) {
      RenderHelper.drawSprite(posX, posY + getHeight() - 5, 0, 79, spriteWidth, 5);
    }

    if (buttonLabel.value == true) {
      String s = BossStatus.bossName;
      FontRenderer fontRenderer = AHud.getMinecraftInstance().fontRenderer;
      fontRenderer.drawStringWithShadow(s, posX + getWidth() / 2 - fontRenderer.getStringWidth(s) / 2, posY, 16711935);
    }
  }

  public List getButtonList()
  {
    List buttonList = super.getButtonList();
    buttonList.add(buttonLabel);
    return buttonList;
  }

  public String getName()
  {
    return "Boss Health";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.TOPCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 - 91;
  }

  public int getDefaultPosY()
  {
    return 12;
  }

  public int getWidth()
  {
    return 182;
  }

  public int getHeight()
  {
    return buttonLabel.value ? 15 : 5;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 48, 16, 16, 16);
  }
}
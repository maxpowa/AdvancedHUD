package advancedhud;

import advancedhud.ahuditem.HudItem;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class GuiScreenSettings extends GuiScreen
{
  protected GuiScrollBar scrollBar;

  public void initGui()
  {
    buttonList.clear();
    buttonList.add(new GuiButton(0, width - 110, 9, 100, 20, "Global Settings"));
    buttonList.add(this.scrollBar = new GuiScrollBar(width - 8, 40, 8, height - 40));
    scrollBar.contentHeight = ((int)(Math.ceil(AHud.getActiveHudItemList().size() / 3.0F) * 36.0D + 15.0D));
  }

  public void drawScreen(int mouseX, int mouseY, float f) {
    drawDefaultBackground();

    for (int i = 0; i < AHud.getActiveHudItemList().size(); i++) {
      int posX = width / 2 + 124 * (i % 3) - 184;
      int posY = i / 3 * 36 + 45 + scrollBar.getScrollDistance();
      drawLabel(posX, posY, (HudItem)AHud.getActiveHudItemList().get(i), mouseX, mouseY);
    }

    RenderHelper.drawHeader("Hud Settings Menu", null);
    super.drawScreen(mouseX, mouseY, f);
  }

  protected void actionPerformed(GuiButton guibutton) {
    if (guibutton.id == 0)
      mc.displayGuiScreen(new GuiScreenGlobalSettings());
  }

  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
  {
    if (mouseButton == 0)
    {
      for (int i = 0; i < AHud.getActiveHudItemList().size(); i++) {
        int posX = width / 2 + 124 * (i % 3) - 184;
        int posY = i / 3 * 36 + 45 + scrollBar.getScrollDistance();

        if (isMouseOver(mouseX, mouseY, posX, posX + 120, posY, posY + 32)) {
          mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
          mc.displayGuiScreen(((HudItem)AHud.getActiveHudItemList().get(i)).getSettingsGuiScreen());
        }
      }
    }
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  protected void drawLabel(int posX, int posY, HudItem hudItem, int mouseX, int mouseY) {
    boolean mouseOver = isMouseOver(mouseX, mouseY, posX, posX + 120, posY, posY + 32);

    if (mouseOver)
      GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
    else {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    RenderHelper.bindTexture("/gui/inventory.png");
    RenderHelper.drawSprite(posX, posY, 0, 166, 120, 32);

    hudItem.drawIcon(posX + 8, posY + 8);

    mc.fontRenderer.drawStringWithShadow(hudItem.getName(), posX + 30, posY + 12, mouseOver ? 16777120 : 16777215);
  }

  private boolean isMouseOver(int x, int y, int minX, int maxX, int minY, int maxY) {
    return (x > minX) && (x < maxX) && (y > minY) && (y < maxY);
  }

  protected void keyTyped(char c, int i) {
    if (i == 1) {
      String DS = File.separator;
      SaveController.saveConfig("active", "config" + DS + "AdvancedHud" + DS + "active");
      mc.displayGuiScreen(null);
      mc.setIngameFocus();
    }
  }
}
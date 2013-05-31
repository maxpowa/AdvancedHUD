package advancedhud;

import advancedhud.ahuditem.HudItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiScreenReposition extends GuiScreen
{
  protected GuiScreen parentScreen;
  protected HudItem hudItem;
  protected boolean axisAlign;
  protected int oldPosX;
  protected int oldPosY;

  public GuiScreenReposition(GuiScreen parentScreen, HudItem hudItem)
  {
    this.parentScreen = parentScreen;
    this.hudItem = hudItem;
    oldPosX = hudItem.posX;
    oldPosY = hudItem.posY;
  }

  public void handleMouseInput() {
    int mouseX = Mouse.getEventX() * width / mc.displayWidth;
    int mouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1;

    hudItem.posX = (mouseX - hudItem.getWidth() / 2);
    hudItem.posY = (mouseY - hudItem.getHeight() / 2);

    if (axisAlign) {
      if ((hudItem.posX > oldPosX - 5) && (hudItem.posX < oldPosX + 5)) {
        hudItem.posX = oldPosX;
      }
      if ((hudItem.posY > oldPosY - 5) && (hudItem.posY < oldPosY + 5)) {
        hudItem.posY = oldPosY;
      }
    }

    hudItem.fixBounds();

    super.handleMouseInput();
  }

  public void drawScreen(int mouseX, int mouseY, float f) {
    drawCenteredString(fontRenderer, "CLICK to confirm, ESCAPE to cancel, R to reset, CTRL to align", width / 2, 16, 16777215);
    drawCenteredString(fontRenderer, "Alignment: " + Alignment.calculateAlignment(mouseX, mouseY), width / 2, 26, 16777215);

    drawRect(hudItem.posX, hudItem.posY, hudItem.posX + hudItem.getWidth(), hudItem.posY + hudItem.getHeight(), -2147483648);

    if (axisAlign) {
      int x = oldPosX + hudItem.getWidth() / 2;
      int y = oldPosY + hudItem.getHeight() / 2;
      drawRect(x - 1, 0, x + 1, height, 1073741824);
      drawRect(0, y - 1, width, y + 1, 1073741824);
    }
  }

  protected void mouseClicked(int mouseX, int mouseY, int mouseState) {
    if (mouseState == 0) {
      hudItem.alignment = Alignment.calculateAlignment(mouseX, mouseY);
      mc.displayGuiScreen(parentScreen);
    }
  }

  public void handleKeyboardInput() {
    super.handleKeyboardInput();

    if (Keyboard.getEventKey() == 29)
      axisAlign = Keyboard.getEventKeyState();
  }

  protected void keyTyped(char keyChar, int keyCode)
  {
    if (keyCode == 1) {
      hudItem.posX = oldPosX;
      hudItem.posY = oldPosY;
      mc.displayGuiScreen(parentScreen);
    }
    if (keyCode == 19) {
      hudItem.posX = hudItem.getDefaultPosX();
      hudItem.posY = hudItem.getDefaultPosY();
      hudItem.alignment = hudItem.getDefaultAlignment();
      hudItem.fixBounds();
      mc.displayGuiScreen(parentScreen);
    }
  }
}
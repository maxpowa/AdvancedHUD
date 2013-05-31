package advancedhud.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonUpDown extends ButtonLabel
{
  public String name;
  public int value;
  protected Button buttonLeftFast = new Button(0, 20, 20, "<<");
  protected Button buttonLeft = new Button(0, 20, 20, "<");
  protected Button buttonRight = new Button(0, 20, 20, ">");
  protected Button buttonRightFast = new Button(0, 20, 20, ">>");

  public ButtonUpDown(int id, String displayString) {
    super(id, displayString);
    name = displayString;
  }

  public ButtonUpDown(int id, int width, int height, String displayString) {
    super(id, width, height, displayString);
  }

  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    if (!drawButton) {
      return;
    }

    displayString = (name + ": " + value);

    buttonLeftFast.xPosition = xPosition;
    buttonLeft.xPosition = (xPosition + buttonLeftFast.getWidth());
    buttonRightFast.xPosition = (xPosition + getWidth() - buttonRight.getWidth());
    buttonRight.xPosition = (buttonRightFast.xPosition - buttonRight.getWidth());
    buttonLeftFast.yPosition = (buttonLeft.yPosition = buttonRightFast.yPosition = buttonRight.yPosition = yPosition);

    super.drawButton(mc, mouseX, mouseY);
    buttonLeftFast.drawButton(mc, mouseX, mouseY);
    buttonLeft.drawButton(mc, mouseX, mouseY);
    buttonRight.drawButton(mc, mouseX, mouseY);
    buttonRightFast.drawButton(mc, mouseX, mouseY);
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    boolean isPressed = false;
    if (buttonLeftFast.mousePressed(mc, mouseX, mouseY)) {
      value -= 10;
      isPressed = true;
    }
    if (buttonLeft.mousePressed(mc, mouseX, mouseY)) {
      value -= 1;
      isPressed = true;
    }
    if (buttonRight.mousePressed(mc, mouseX, mouseY)) {
      value += 1;
      isPressed = true;
    }
    if (buttonRightFast.mousePressed(mc, mouseX, mouseY)) {
      value += 10;
      isPressed = true;
    }
    return isPressed;
  }
}
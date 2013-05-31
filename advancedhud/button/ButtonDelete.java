package advancedhud.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonDelete extends ButtonLabel
{
  public Button button;

  public ButtonDelete(int id, String displayString)
  {
    super(id, displayString);
    button = new Button(-1, 20, 20, "X");
  }

  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    button.xPosition = (xPosition + width - button.getWidth());
    button.yPosition = yPosition;

    super.drawButton(mc, mouseX, mouseY);
    button.drawButton(mc, mouseX, mouseY);
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    return button.mousePressed(mc, mouseX, mouseY);
  }
}
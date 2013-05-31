package advancedhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Mouse;

public class GuiScrollBar extends GuiButton
{
  protected float scrollAmount;
  protected int contentHeight;
  protected boolean isDragging;

  public GuiScrollBar(int posX, int posY, int width, int height)
  {
    super(-1, posX, posY, width, height, "");
  }

  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    if (contentHeight > height) {
      doInput(mouseY);

      drawRect(xPosition + 1, yPosition + 1, xPosition + width, yPosition + height, -16777216);

      int sliderY = yPosition + 1 + getSliderOffset();
      int sliderY2 = sliderY + getSliderHeight();

      boolean hilight = (isHovering(mouseX, mouseY)) || (isDragging);
      drawRect(xPosition + 1, sliderY, xPosition + width, sliderY2, hilight ? -10721635 : -8355712);
      drawRect(xPosition + 1, sliderY, xPosition + width - 1, sliderY2 - 1, hilight ? -8484673 : -4144960);
    }
  }

  public void setContentHeight(int height) {
    contentHeight = height;
  }

  public int getScrollDistance() {
    return -(int)((contentHeight - height) * scrollAmount);
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    isDragging = ((enabled) && (drawButton) && (isHovering(mouseX, mouseY)));
    return isDragging;
  }

  public void mouseReleased(int mosueX, int mouseY) {
    isDragging = false;
  }

  protected boolean isHovering(int mouseX, int mouseY) {
    return (mouseX >= xPosition) && (mouseX < xPosition + width) && (mouseY >= yPosition) && (mouseY < yPosition + height);
  }

  protected void doInput(int mouseY)
  {
    if (isDragging) {
      float offset = (mouseY - yPosition - getSliderHeight() / 2) / (height - getSliderHeight());
      scrollAmount = Math.min(1.0F, Math.max(0.0F, offset));
    }

    float dWheel = Mouse.getDWheel();
    if (dWheel != 0.0F)
      scrollAmount = Math.min(1.0F, Math.max(0.0F, scrollAmount - dWheel / 1200.0F));
  }

  protected int getSliderOffset()
  {
    return (int)((height - getSliderHeight()) * scrollAmount);
  }

  protected int getSliderHeight() {
    return (int)(height / contentHeight * height);
  }
}
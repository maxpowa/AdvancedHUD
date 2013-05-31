package advancedhud.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;

public class Button extends GuiButton
{
  public String toolTip;

  public Button(int id, String displayString)
  {
    super(id, 0, 0, 200, 20, displayString);
  }

  public Button(int id, int width, int height, String displayString) {
    super(id, 0, 0, width, height, displayString);
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    if ((enabled) && (drawButton) && (isMouseOver(mouseX, mouseY))) {
      onClick();
      return true;
    }
    return false;
  }

  public boolean isMouseOver(int mouseX, int mouseY)
  {
    return (mouseX >= xPosition) && (mouseX < xPosition + width) && (mouseY >= yPosition) && (mouseY < yPosition + height);
  }

  public void onClick()
  {
  }

  public void setNBT(NBTTagCompound nbtTagCompound) {
  }

  public NBTTagCompound getNBT() {
    return new NBTTagCompound();
  }
}
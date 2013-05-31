package advancedhud.button;

import advancedhud.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class ButtonToggle extends Button
{
  public String stateOn;
  public String stateOff;
  public boolean value;

  public ButtonToggle(int id, String displayString)
  {
    this(id, displayString, "On", "Off");
  }

  public ButtonToggle(int id, String displayString, boolean defaultValue) {
    this(id, displayString, "On", "Off", defaultValue);
  }

  public ButtonToggle(int id, String displayString, String stateOn, String stateOff) {
    this(id, 200, 20, displayString, stateOn, stateOff, false);
  }

  public ButtonToggle(int id, String displayString, String stateOn, String stateOff, boolean defaultValue) {
    this(id, 200, 20, displayString, stateOn, stateOff, defaultValue);
  }

  public ButtonToggle(int id, int width, int height, String displayString, String stateOn, String stateOff, boolean defaultValue) {
    super(id, width, height, displayString);
    this.stateOn = stateOn;
    this.stateOff = stateOff;
    value = defaultValue;
  }

  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    if (!drawButton) {
      return;
    }

    boolean mouseOver = _isMouseOver(mouseX, mouseY);
    int i = getHoverState(mouseOver);

    RenderHelper.bindTexture("/gui/gui.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    drawTexturedModalRect(xPosition + width / 2, yPosition, 0, 46, width / 4, height);
    drawTexturedModalRect(xPosition + width / 2 + width / 4, yPosition, 200 - width / 4, 46, width / 4, height);

    if (value) {
      drawTexturedModalRect(xPosition + width / 2, yPosition, 0, 66, width / 8, height);
      drawTexturedModalRect(xPosition + width / 2 + width / 8, yPosition, 200 - width / 8, 66, width / 8, height);
    } else {
      drawTexturedModalRect(xPosition + width / 2 + width / 4, yPosition, 0, 66, width / 8, height);
      drawTexturedModalRect(xPosition + width - width / 8, yPosition, 200 - width / 8, 66, width / 8, height);
    }

    mouseDragged(mc, mouseX, mouseY);

    int color = 14737632;
    if (!enabled)
      color = -6250336;
    else if (mouseOver) {
      color = 16777120;
    }

    drawString(mc.fontRenderer, displayString, xPosition + 2, yPosition + (height - 8) / 2, color);

    if (value) {
      drawCenteredString(mc.fontRenderer, stateOn, xPosition + width / 2 + width / 8, yPosition + (height - 8) / 2, color);
      drawCenteredString(mc.fontRenderer, stateOff, xPosition + width - width / 8, yPosition + (height - 8) / 2, -6250336);
    } else {
      drawCenteredString(mc.fontRenderer, stateOn, xPosition + width / 2 + width / 8, yPosition + (height - 8) / 2, -6250336);
      drawCenteredString(mc.fontRenderer, stateOff, xPosition + width - width / 8, yPosition + (height - 8) / 2, color);
    }
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    if ((enabled) && (drawButton) && (isMouseOver(mouseX, mouseY))) {
      value = (!value);
      return true;
    }
    return false;
  }

  protected boolean _isMouseOver(int mouseX, int mouseY) {
    return (mouseX >= xPosition + width / 2) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height);
  }

  public void setNBT(NBTTagCompound nbt)
  {
    value = nbt.getBoolean("value");
  }

  public NBTTagCompound getNBT()
  {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setBoolean("value", value);
    return nbt;
  }
}
package advancedhud.button;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class ButtonMultiState extends Button
{
  public String[] states;
  public int value;
  public String name;

  public ButtonMultiState(int id, String displayString, String[] states)
  {
    this(id, 200, 20, displayString, states, 0);
  }

  public ButtonMultiState(int id, String displayString, String[] states, int defaultValue) {
    this(id, 200, 20, displayString, states, defaultValue);
  }

  public ButtonMultiState(int id, int width, int height, String displayString, String[] states) {
    this(id, 200, 20, displayString, states, 0);
  }

  public ButtonMultiState(int id, int width, int height, String displayString, String[] states, int defaultValue) {
    super(id, width, height, "");
    this.states = states;
    name = displayString;
    value = Math.min(states.length, defaultValue);
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    if (super.mousePressed(mc, mouseX, mouseY)) {
      if (++value >= states.length) {
        value = 0;
      }
      return true;
    }
    return false;
  }

  public void drawButton(Minecraft mc, int mousex, int mousey)
  {
    displayString = (name + ": " + states[value]);
    super.drawButton(mc, mousex, mousey);
    displayString = name;
  }

  public void setNBT(NBTTagCompound nbt)
  {
    value = nbt.getInteger("value");
  }

  public NBTTagCompound getNBT()
  {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("value", value);
    return nbt;
  }
}
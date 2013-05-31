package advancedhud.renderer;

import advancedhud.ahuditem.HudItemDynamic;
import advancedhud.button.Button;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Renderer
{
  protected HudItemDynamic hudItem;

  public Renderer(HudItemDynamic hudItem)
  {
    this.hudItem = hudItem;
  }
  public abstract void render(float paramFloat);

  public void tick(NBTTagCompound properties) {
  }
  public abstract String getName();

  public abstract int getWidth();

  public abstract int getHeight();

  public boolean isMoveable() { return true; }


  public List getButtonList()
  {
    return new ArrayList();
  }

  public void onButtonClick(Button button, GuiScreen parent)
  {
  }

  public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);

  public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound);
}
package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.GuiScreenReposition;
import advancedhud.GuiScreenSetting;
import advancedhud.RenderHelper;
import advancedhud.button.Button;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;

public abstract class HudItem
{
  public Alignment alignment;
  public int posX;
  public int posY;
  protected Button buttonReposition = new Button(0, "Reposition");

  public abstract String getName();

  public abstract Alignment getDefaultAlignment();

  public abstract int getDefaultPosX();

  public abstract int getDefaultPosY();

  public abstract int getWidth();

  public abstract int getHeight();

  public HudItem() { alignment = getDefaultAlignment();
    posX = getDefaultPosX();
    posY = getDefaultPosY();
  }

  public abstract void render(float paramFloat);

  public void tick()
  {
  }

  public boolean isMoveable()
  {
    return true;
  }

  public boolean isEnabledByDefault()
  {
    return true;
  }

  public boolean isRenderedInCreative()
  {
    return true;
  }

  public List getButtonList()
  {
    List buttonList = new ArrayList();

    if (isMoveable()) {
      buttonList.add(buttonReposition);
    }

    return buttonList;
  }

  public void onButtonClick(Button button, GuiScreen parent)
  {
    if (button == buttonReposition) {
      AHud.getMinecraftInstance().displayGuiScreen(new GuiScreenReposition(parent, this));
    } else {
      parent.initGui();
      fixBounds();
    }
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 0, 0, 16, 16);
  }

  public GuiScreen getSettingsGuiScreen()
  {
    return new GuiScreenSetting(this);
  }

  public void fixBounds() {
    posX = Math.max(0, Math.min(AHud.screenWidth - getWidth(), posX));
    posY = Math.max(0, Math.min(AHud.screenHeight - getHeight(), posY));
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    posX = nbt.getInteger("posX");
    posY = nbt.getInteger("posY");
    alignment = Alignment.fromString(nbt.getString("alignment"));
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setInteger("posX", posX);
    nbt.setInteger("posY", posY);
    nbt.setString("alignment", alignment.toString());
  }
}
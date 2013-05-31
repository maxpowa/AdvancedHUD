package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import advancedhud.button.Button;
import advancedhud.button.ButtonCrosshairGrid;
import advancedhud.button.ButtonToggle;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class HudItemCrosshair extends HudItem
{
  protected ButtonToggle buttonEnabled = new ButtonToggle(0, "Custom crosshair");
  protected ButtonCrosshairGrid buttonGrid = new ButtonCrosshairGrid(1, "Select Crosshair", "/gui/ahud_crosshairs.png");

  public void render(float f)
  {
    int spriteY;
    int spriteX = spriteY = 0;

    if (!buttonEnabled.value) {
      RenderHelper.bindTexture("/gui/icons.png");
    } else {
      RenderHelper.bindTexture("/gui/ahud_crosshairs.png");

      spriteX = buttonGrid.spriteOffsetX;
      spriteY = buttonGrid.spriteOffsetY;
    }

    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glBlendFunc(775, 769);

    RenderHelper.drawSprite(AHud.screenWidth / 2 - 7, AHud.screenHeight / 2 - 7, spriteX, spriteY, 16, 16);

    GL11.glBlendFunc(770, 771);
  }

  public List getButtonList()
  {
    return Arrays.asList(new Button[] { buttonEnabled, buttonGrid });
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    buttonEnabled.setNBT(nbt.getCompoundTag("buttonEnabled"));
    buttonGrid.setNBT(nbt.getCompoundTag("buttonGrid"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("buttonEnabled", buttonEnabled.getNBT());
    nbt.setCompoundTag("buttonGrid", buttonGrid.getNBT());
    super.saveToNBT(nbt);
  }

  public void onButtonClick(Button button, GuiScreen parent)
  {
  }

  public String getName() {
    return "Crosshair";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.CENTERCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 - 7;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight / 2 - 7;
  }

  public int getWidth()
  {
    return 16;
  }

  public int getHeight()
  {
    return 16;
  }

  public boolean isMoveable()
  {
    return false;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 96, 0, 16, 16);
  }
}
package advancedhud.renderer;

import advancedhud.AHud;
import advancedhud.RenderHelper;
import advancedhud.ahuditem.HudItemDynamic;
import net.minecraft.nbt.NBTTagCompound;

public class RendererXpBarSolid extends Renderer
{
  protected float fill;
  protected int alpha;

  public RendererXpBarSolid(HudItemDynamic hudItem)
  {
    super(hudItem);
  }

  public void tick(NBTTagCompound properties)
  {
    fill = (properties.hasKey("fill") ? properties.getFloat("fill") : 1.0F);
    alpha = ((int)(255.0F * (properties.hasKey("alpha") ? properties.getFloat("alpha") : 1.0F)));
  }

  public void render(float f)
  {
    int width = (int)(fill * AHud.screenWidth);
    int posY = hudItem.posY < AHud.screenHeight / 2 ? 0 : AHud.screenHeight - 3;

    RenderHelper.drawRectangle(0, posY, AHud.screenWidth, 3, 0x81B05B00 | alpha, 0x2C491500 | alpha, false);
    if (width > 0)
      RenderHelper.drawRectangle(width, posY, AHud.screenWidth - width, 3, 0x0 | alpha);
  }

  public String getName()
  {
    return "Full Width";
  }

  public int getWidth()
  {
    return 0;
  }

  public int getHeight()
  {
    return 0;
  }

  public void readFromNBT(NBTTagCompound nbt)
  {
  }

  public void writeToNBT(NBTTagCompound nbt)
  {
  }
}
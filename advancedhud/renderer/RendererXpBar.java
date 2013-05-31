package advancedhud.renderer;

import advancedhud.RenderHelper;
import advancedhud.ahuditem.HudItemDynamic;
import advancedhud.button.Button;
import advancedhud.button.ButtonSlider;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class RendererXpBar extends Renderer
{
  protected ButtonSlider buttonWidth = new ButtonSlider(1, 20, 182, "width");
  protected float fill;
  protected float alpha;

  public RendererXpBar(HudItemDynamic hudItem)
  {
    super(hudItem);
    buttonWidth.value = 1.0F;
  }

  public void tick(NBTTagCompound properties)
  {
    fill = (properties.hasKey("fill") ? properties.getFloat("fill") : 1.0F);
    alpha = (properties.hasKey("alpha") ? properties.getFloat("alpha") : 1.0F);
  }

  public void render(float f)
  {
    RenderHelper.bindTexture("/gui/icons.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);

    int halfWidth = getWidth() / 2;
    int widthLeft = (int)Math.min(halfWidth, fill * getWidth());
    int widthRight = (int)(fill * getWidth() - halfWidth);

    if (widthLeft < halfWidth) {
      RenderHelper.drawSprite(hudItem.posX, hudItem.posY, 0, 64, halfWidth, 5, -90);
    }

    if (fill < 1.0F) {
      RenderHelper.drawSprite(hudItem.posX + halfWidth, hudItem.posY, 182 - halfWidth, 64, halfWidth, 5, -90);
    }

    if (fill > 0.0F) {
      RenderHelper.drawSprite(hudItem.posX, hudItem.posY, 0, 69, widthLeft, 5, -90);
    }

    if (widthRight > 0)
      RenderHelper.drawSprite(hudItem.posX + halfWidth, hudItem.posY, 182 - halfWidth, 69, widthRight, 5, -90);
  }

  public String getName()
  {
    return "default XP Bar";
  }

  public int getWidth()
  {
    return buttonWidth.getIntValue();
  }

  public int getHeight()
  {
    return 5;
  }

  public List getButtonList()
  {
    return Arrays.asList(new Button[] { buttonWidth });
  }

  public void readFromNBT(NBTTagCompound nbt)
  {
    buttonWidth.setNBT(nbt.getCompoundTag("buttonWidth"));
  }

  public void writeToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("buttonWidth", buttonWidth.getNBT());
  }
}
package advancedhud.renderer;

import advancedhud.RenderHelper;
import advancedhud.ahuditem.HudItemDynamic;
import advancedhud.button.Button;
import advancedhud.button.ButtonSlider;
import advancedhud.button.ButtonToggle;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class RendererSolidBar1 extends Renderer
{
  protected float colorR;
  protected float colorG;
  protected float colorB;
  protected float fill;
  protected Random rand = new Random();
  protected int regenOffset;
  protected float alpha;
  protected ButtonSlider buttonWidth = new ButtonSlider(1, 20, 500, "width");
  protected ButtonSlider buttonHeight = new ButtonSlider(2, 5, 20, "height");
  protected ButtonToggle buttonSmoothing = new ButtonToggle(3, "Smooth transistion");
  protected ButtonToggle buttonMirror = new ButtonToggle(4, "Mirror");
  protected ButtonToggle buttonOrientation = new ButtonToggle(5, "Rotate");

  public RendererSolidBar1(HudItemDynamic hudItem, int color) {
    super(hudItem);
    setColor(color);

    buttonWidth.setIntValue(80);
    buttonHeight.setIntValue(9);
    alpha = 1.0F;
  }

  public void tick(NBTTagCompound properties) {
    alpha = (properties.hasKey("alpha") ? properties.getFloat("alpha") : 1.0F);

    if (buttonSmoothing.value)
      fill += ((properties.hasKey("fill") ? properties.getFloat("fill") : 1.0F) - fill) / 3.0F;
    else {
      fill = (properties.hasKey("fill") ? properties.getFloat("fill") : 1.0F);
    }

    if (properties.getBoolean("regenEffect"))
      regenOffset += 1;
    else
      regenOffset = 0;
  }

  public void render(float f)
  {
    if ((buttonMirror.value) || (buttonOrientation.value)) {
      GL11.glPushMatrix();
    }

    if (buttonMirror.value) {
      GL11.glDisable(2884);
      GL11.glScalef(-1.0F, 1.0F, 1.0F);
      GL11.glTranslatef(-hudItem.posX * 2 - hudItem.getWidth(), 0.0F, 0.0F);
    }

    if (buttonOrientation.value) {
      GL11.glTranslatef(hudItem.posX, hudItem.posY, 0.0F);
      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-hudItem.posX - hudItem.getHeight(), -hudItem.posY, 0.0F);
    }

    if (fill < 1.0F) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
      renderTheBar(8, 48, 1.0F);
    }

    if (fill > 0.0F)
    {
      float B;
      float R;
      float G;
      if (regenOffset > 0) {
        R = (float)(colorR + (1.0F - colorR) * Math.sin(regenOffset / 5.0F));
        G = (float)(colorG + (1.0F - colorG) * Math.sin(regenOffset / 5.0F));
        B = 0.0F;
      } else {
        R = colorR;
        G = colorG;
        B = colorB;
      }
      GL11.glColor4f(R, G, B, alpha);
      renderTheBar(0, 48, fill);
    }

    if (buttonMirror.value) {
      GL11.glEnable(2884);
    }

    if ((buttonMirror.value) || (buttonOrientation.value))
      GL11.glPopMatrix();
  }

  public void renderTheBar(int spriteX, int spriteY, float fill)
  {
    int posX = hudItem.posX;
    int posY = hudItem.posY;
    int width = buttonOrientation.value ? getHeight() : getWidth();
    int height = buttonOrientation.value ? getWidth() : getHeight();

    if (hudItem.properties.getBoolean("wiggle")) {
      posX += rand.nextInt(2);
      posY += rand.nextInt(2);
    }

    RenderHelper.bindTexture("/gui/ahud_icons.png");

    int fillWidth = (int)(width * fill);

    int widthLeft = Math.min(fillWidth, 4);
    int widthRight = 4 + fillWidth - width;

    int heightTop = Math.min(height - 2, 4);
    int heightBottom = Math.min(height - 2, 4);

    int widthCenter = Math.min(width - 8, fillWidth - 4);
    int heightCenter = height - heightTop - heightBottom;

    float u0 = spriteX / 256.0F;
    float u1 = (spriteX + widthLeft) / 256.0F;
    float u2 = (spriteX + 4) / 256.0F;
    float u3 = (spriteX + 4 + widthRight) / 256.0F;

    float v0 = spriteY / 256.0F;
    float v1 = (spriteY + heightTop) / 256.0F;
    float v2 = (spriteY + 8 - heightBottom) / 256.0F;
    float v3 = (spriteY + 8) / 256.0F;

    if (widthLeft > 0)
    {
      if (heightTop > 0) {
        RenderHelper.drawSpriteUV(posX, posY, widthLeft, heightTop, u0, v0, u1, v1, 0);
      }

      if (heightCenter > 0) {
        RenderHelper.drawSpriteUV(posX, posY + heightTop, widthLeft, heightCenter, u0, v1, u1, v2, 0);
      }

      if (heightBottom > 0) {
        RenderHelper.drawSpriteUV(posX, posY + height - heightBottom, widthLeft, heightTop, u0, v2, u1, v3, 0);
      }
    }

    if (widthCenter > 0)
    {
      if (heightTop > 0) {
        RenderHelper.drawSpriteUV(posX + widthLeft, posY, widthCenter, heightTop, u1, v0, u2, v1, 0);
      }

      if (heightCenter > 0) {
        RenderHelper.drawSpriteUV(posX + widthLeft, posY + heightTop, widthCenter, heightCenter, u1, v1, u2, v2, 0);
      }

      if (heightBottom > 0) {
        RenderHelper.drawSpriteUV(posX + widthLeft, posY + height - heightBottom, widthCenter, heightBottom, u1, v2, u2, v3, 0);
      }
    }

    if (widthRight > 0)
    {
      if (heightTop > 0) {
        RenderHelper.drawSpriteUV(posX + width - 4, posY, widthRight, heightTop, u2, v0, u3, v1, 0);
      }

      if (heightCenter > 0) {
        RenderHelper.drawSpriteUV(posX + width - 4, posY + heightTop, widthRight, heightCenter, u2, v1, u3, v2, 0);
      }

      if (heightBottom > 0)
        RenderHelper.drawSpriteUV(posX + width - 4, posY + height - heightBottom, widthRight, heightBottom, u2, v2, u3, v3, 0);
    }
  }

  public int getWidth()
  {
    return buttonOrientation.value ? buttonHeight.getIntValue() : buttonWidth.getIntValue();
  }

  public int getHeight()
  {
    return buttonOrientation.value ? buttonWidth.getIntValue() : buttonHeight.getIntValue();
  }

  public String getName()
  {
    return "SolidBar";
  }

  public List getButtonList()
  {
    return Arrays.asList(new Button[] { buttonWidth, buttonHeight, buttonSmoothing, buttonMirror, buttonOrientation });
  }

  public void setColor(int color) {
    colorR = ((color >> 16 & 0xFF) / 255.0F);
    colorG = ((color >> 8 & 0xFF) / 255.0F);
    colorB = ((color & 0xFF) / 255.0F);
  }

  public void readFromNBT(NBTTagCompound nbt)
  {
    buttonWidth.setNBT(nbt.getCompoundTag("buttonWidth"));
    buttonHeight.setNBT(nbt.getCompoundTag("buttonHeight"));
    buttonSmoothing.setNBT(nbt.getCompoundTag("buttonSmoothing"));
    buttonMirror.setNBT(nbt.getCompoundTag("buttonMirror"));
    buttonOrientation.setNBT(nbt.getCompoundTag("buttonOrientation"));
  }

  public void writeToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("buttonWidth", buttonWidth.getNBT());
    nbt.setCompoundTag("buttonHeight", buttonHeight.getNBT());
    nbt.setCompoundTag("buttonSmoothing", buttonSmoothing.getNBT());
    nbt.setCompoundTag("butonMirror", buttonMirror.getNBT());
    nbt.setCompoundTag("buttonOrientation", buttonOrientation.getNBT());
  }
}
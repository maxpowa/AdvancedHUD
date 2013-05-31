package advancedhud.renderer;

import advancedhud.AHud;
import advancedhud.RenderHelper;
import advancedhud.ahuditem.HudItemDynamic;
import advancedhud.button.Button;
import advancedhud.button.ButtonToggle;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class RendererIconStrip extends Renderer
{
  protected int spriteX;
  protected int spriteY;
  protected int spriteHalfX;
  protected int spriteHalfY;
  protected int spriteBgX;
  protected int spriteBgY;
  protected ButtonToggle buttonDouble = new ButtonToggle(1, "Double");
  protected ButtonToggle buttonMirror = new ButtonToggle(2, "Mirror");
  protected ButtonToggle buttonOrientation = new ButtonToggle(3, "Vertical");
  protected Random rand = new Random();
  protected int fill = 20;
  protected int regenOffset;
  protected boolean drawRtoL;
  protected float alpha;

  public RendererIconStrip(HudItemDynamic hudItem)
  {
    super(hudItem);
  }

  public RendererIconStrip setFullSpritePos(int x, int y) {
    spriteX = x;
    spriteY = y;
    return this;
  }

  public RendererIconStrip setHalfSpritePos(int x, int y) {
    spriteHalfX = x;
    spriteHalfY = y;
    return this;
  }

  public RendererIconStrip setBackgroundSpritePos(int x, int y) {
    spriteBgX = x;
    spriteBgY = y;
    return this;
  }

  public RendererIconStrip setDrawRtoL(boolean flag) {
    drawRtoL = flag;
    return this;
  }

  public void tick(NBTTagCompound properties)
  {
    fill = ((int)(20.0F * (properties.hasKey("fill") ? properties.getFloat("fill") : 1.0F)));
    alpha = (properties.hasKey("alpha") ? properties.getFloat("alpha") : 1.0F);
    regenOffset = (properties.getBoolean("regenEffect") ? AHud.updateCounter % 25 : -1);
  }

  public void render(float f)
  {
    RenderHelper.bindTexture("/gui/icons.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);

    if ((buttonMirror.value) || (buttonOrientation.value)) {
      GL11.glPushMatrix();
    }

    if (buttonMirror.value) {
      GL11.glDisable(2884);
      GL11.glScalef(-1.0F, 1.0F, 1.0F);
      GL11.glTranslatef(-hudItem.posX * 2 - hudItem.getWidth(), 0.0F, 0.0F);
    }

    if (buttonOrientation.value) {
      if (drawRtoL) {
        GL11.glTranslatef(hudItem.posX, hudItem.posY, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-hudItem.posX + hudItem.getHeight() - 9, -hudItem.posY - hudItem.getWidth(), 0.0F);
      } else {
        GL11.glTranslatef(hudItem.posX, hudItem.posY, 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-hudItem.posX - hudItem.getHeight(), -hudItem.posY, 0.0F);
      }
    }

    int amount = buttonDouble.value ? 20 : 10;
    for (byte i = 0; i < amount; i = (byte)(i + 1)) {
      int x = drawRtoL ? hudItem.posX + hudItem.getWidth() - i * 80 / amount - 9 : hudItem.posX + i * 80 / amount;
      int y = hudItem.posY;

      if (hudItem.properties.getBoolean("wiggle")) {
        y += rand.nextInt(2);
      }

      if (i == regenOffset) {
        y -= 2;
      }

      RenderHelper.drawSprite(x, y, spriteBgX, spriteBgY, 9, 9);
      if (buttonDouble.value) {
        if (i + 1 <= fill)
          RenderHelper.drawSprite(x, y, spriteX, spriteY, 9, 9);
      }
      else
      {
        if (i * 2 + 1 < fill) {
          RenderHelper.drawSprite(x, y, spriteX, spriteY, 9, 9);
        }

        if (i * 2 + 1 == fill) {
          RenderHelper.drawSprite(x, y, spriteHalfX, spriteHalfY, 9, 9);
        }
      }
    }

    if (buttonMirror.value) {
      GL11.glEnable(2884);
    }

    if ((buttonMirror.value) || (buttonOrientation.value))
      GL11.glPopMatrix();
  }

  protected int getOrientatedWidth()
  {
    return buttonDouble.value ? 85 : 81;
  }

  public int getWidth()
  {
    return buttonOrientation.value ? 9 : getOrientatedWidth();
  }

  public int getHeight()
  {
    return buttonOrientation.value ? getOrientatedWidth() : 9;
  }

  public List getButtonList()
  {
    return Arrays.asList(new Button[] { buttonDouble, buttonMirror, buttonOrientation });
  }

  public String getName()
  {
    return "Iconstrip";
  }

  public void readFromNBT(NBTTagCompound nbt)
  {
    spriteX = nbt.getInteger("spriteX");
    spriteY = nbt.getInteger("spriteY");
    spriteHalfX = nbt.getInteger("spriteHalfX");
    spriteHalfY = nbt.getInteger("spriteHalfY");
    spriteBgX = nbt.getInteger("spriteBgX");
    spriteBgY = nbt.getInteger("spriteBgY");
    buttonDouble.setNBT(nbt.getCompoundTag("buttonDouble"));
    buttonMirror.setNBT(nbt.getCompoundTag("buttonMirror"));
    buttonOrientation.setNBT(nbt.getCompoundTag("buttonOrientation"));
  }

  public void writeToNBT(NBTTagCompound nbt)
  {
    nbt.setInteger("spriteX", spriteX);
    nbt.setInteger("spriteY", spriteY);
    nbt.setInteger("spriteHalfX", spriteHalfX);
    nbt.setInteger("spriteHalfY", spriteHalfY);
    nbt.setInteger("spriteBgX", spriteBgX);
    nbt.setInteger("spriteBgY", spriteBgY);
    nbt.setCompoundTag("buttonDouble", buttonDouble.getNBT());
    nbt.setCompoundTag("butonMirror", buttonMirror.getNBT());
    nbt.setCompoundTag("buttonOrientation", buttonOrientation.getNBT());
  }
}
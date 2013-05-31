package advancedhud.button;

import advancedhud.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class ButtonCrosshairGrid extends Button
{
  protected String textureName;
  protected boolean dispGrid;
  public int spriteOffsetX;
  public int spriteOffsetY;

  public ButtonCrosshairGrid(int id, String displayString, String textureName)
  {
    super(id, displayString);
    this.textureName = textureName;
  }

  public void drawButton(Minecraft mc, int mousex, int mousey)
  {
    GL11.glEnable(3042);
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
    if (dispGrid) {
      RenderHelper.bindTexture(textureName);
      RenderHelper.drawSprite(xPosition + width / 2 - 128, yPosition, 0, 0, 255, 255);

      updateOffsets(mousex, mousey);

      drawSelector(spriteOffsetX + xPosition + width / 2 - 128, spriteOffsetY + yPosition);
    } else {
      super.drawButton(mc, mousex, mousey);
    }
  }

  protected void drawSelector(int posX, int posY) {
    RenderHelper.drawRectangle(posX, posY, 16, 16, -176);
  }

  protected void updateOffsets(int mousex, int mousey) {
    int x = xPosition + width / 2 - 128;
    int y = yPosition;

    int selectorx = (mousex - x) / 16 * 16;
    int selectory = (mousey - y) / 16 * 16;

    spriteOffsetX = Math.min(Math.max(selectorx, 0), 239);
    spriteOffsetY = Math.min(Math.max(selectory, 0), 239);
  }

  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    boolean mouseOver;
    if (dispGrid) {
      mouseOver = (enabled) && (drawButton) && (mouseX > xPosition + width / 2 - 128) && (mouseX < xPosition + width / 2 + 128) && (mouseY > yPosition) && (mouseY < yPosition + 255);
      dispGrid = (!mouseOver);
    } else {
      mouseOver = super.mousePressed(mc, mouseX, mouseY);
      dispGrid = mouseOver;
    }

    return mouseOver;
  }

  public void setNBT(NBTTagCompound nbt)
  {
    spriteOffsetX = nbt.getInteger("spriteOffsetX");
    spriteOffsetY = nbt.getInteger("spriteOffsetY");
  }

  public NBTTagCompound getNBT()
  {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("spriteOffsetX", spriteOffsetX);
    nbt.setInteger("spriteOffsetY", spriteOffsetY);
    return nbt;
  }
}

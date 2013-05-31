package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.RenderHelper;
import advancedhud.renderer.RendererXpBar;
import advancedhud.renderer.RendererXpBarSolid;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class HudItemXpBar extends HudItemDynamic
{
  protected FadeHelper fadeHelper = new FadeHelper(8);
  protected float experience;

  public HudItemXpBar()
  {
    addRenderer(new RendererXpBar(this));
    addRenderer(new RendererXpBarSolid(this));
  }

  public void tick(NBTTagCompound properties)
  {
    EntityClientPlayerMP player = AHud.getMinecraftInstance().thePlayer;
    properties.setFloat("fill", player.experience);

    fadeHelper.tick(experience != player.experience);
    experience = player.experience;
    properties.setFloat("alpha", fadeHelper.getAlpha());
  }

  public void render(float f)
  {
    if (AHud.getMinecraftInstance().thePlayer.xpBarCap() > 0)
      super.render(f);
  }

  public List getButtonList()
  {
    List buttonList = super.getButtonList();

    buttonList.addAll(fadeHelper.getButtonList());

    return buttonList;
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    fadeHelper.setNBT(nbt.getCompoundTag("fadeHelper"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("fadeHelper", fadeHelper.getNBT());
    super.saveToNBT(nbt);
  }

  public String getName()
  {
    return "XP Bar";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 - 91;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 29;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 64, 0, 16, 16);
  }

  public boolean isRenderedInCreative()
  {
    return false;
  }
}
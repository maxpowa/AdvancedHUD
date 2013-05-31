package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.RenderHelper;
import advancedhud.button.ButtonToggle;
import advancedhud.renderer.Renderer;
import advancedhud.renderer.RendererIconStrip;
import advancedhud.renderer.RendererSolidBar1;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class HudItemBreathBar extends HudItemDynamic
{
  protected ButtonToggle buttonHideOnDry = new ButtonToggle(2, "out of water", "show", "hide", false);
  protected FadeHelper fadeHelper = new FadeHelper(8);
  protected int prevBreath;

  public HudItemBreathBar()
  {
    addRenderer(new RendererIconStrip(this).setFullSpritePos(16, 18).setHalfSpritePos(25, 18).setBackgroundSpritePos(247, 247).setDrawRtoL(true));

    addRenderer(new RendererSolidBar1(this, 33023));
  }

  public void tick(NBTTagCompound properties)
  {
    EntityPlayer player = AHud.getMinecraftInstance().thePlayer;
    float fill = (float)(Math.ceil((player.getAir() - 2) / 30.0F) / 10.0D);

    if (fill != (float)(Math.ceil(player.getAir() / 30.0F) / 10.0D))
      fill = (float)(Math.floor(fill * 10.0F) / 10.0D) + 0.05F;
    else {
      fill = (float)(Math.floor(fill * 10.0F) / 10.0D);
    }

    properties.setFloat("fill", fill);

    fadeHelper.tick((AHud.getMinecraftInstance().thePlayer.isInsideOfMaterial(Material.water)) || (buttonHideOnDry.value));
    prevBreath = player.getAir();
    properties.setFloat("alpha", fadeHelper.getAlpha());
  }

  public void render(float f)
  {
    if ((AHud.getMinecraftInstance().thePlayer.isInsideOfMaterial(Material.water)) || (fadeHelper.isFadeEnabled()) || (buttonHideOnDry.value))
      getCurrentRenderer().render(f);
  }

  public List getButtonList()
  {
    List buttonList = super.getButtonList();

    buttonList.add(buttonHideOnDry);
    buttonList.addAll(fadeHelper.getButtonList());

    return buttonList;
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    fadeHelper.setNBT(nbt.getCompoundTag("fadeHelper"));
    buttonHideOnDry.setNBT(nbt.getCompoundTag("buttonHideOnEmpty"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("fadeHelper", fadeHelper.getNBT());
    nbt.setCompoundTag("buttonHideOnEmpty", buttonHideOnDry.getNBT());
    super.saveToNBT(nbt);
  }

  public String getName()
  {
    return "Breath Bar";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 + 9;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 49;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 48, 0, 16, 16);
  }

  public boolean isRenderedInCreative()
  {
    return false;
  }
}
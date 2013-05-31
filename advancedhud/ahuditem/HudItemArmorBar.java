package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.RenderHelper;
import advancedhud.button.ButtonSlider;
import advancedhud.button.ButtonToggle;
import advancedhud.renderer.Renderer;
import advancedhud.renderer.RendererIconStrip;
import advancedhud.renderer.RendererSolidBar1;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class HudItemArmorBar extends HudItemDynamic
{
  protected ButtonToggle buttonHideOnEmpty = new ButtonToggle(2, "when empty", "show", "hide", false);
  protected ButtonSlider buttonFadeThreshold = new ButtonSlider(1, 1, 20, "Fade threshold", null, null, 0.85F);
  protected FadeHelper fadeHelper = new FadeHelper(8);
  protected int prevArmor;

  public HudItemArmorBar()
  {
    addRenderer(new RendererIconStrip(this).setFullSpritePos(34, 9).setHalfSpritePos(25, 9).setBackgroundSpritePos(16, 9));

    addRenderer(new RendererSolidBar1(this, 15132415));
  }

  public void render(float f)
  {
    if ((buttonHideOnEmpty.value) || (properties.getFloat("fill") > 0.0F))
      getCurrentRenderer().render(f);
  }

  public void tick(NBTTagCompound properties)
  {
    EntityPlayer player = AHud.getMinecraftInstance().thePlayer;
    properties.setFloat("fill", player.getTotalArmorValue() / 20.0F);

    fadeHelper.tick((prevArmor != player.getTotalArmorValue()) || (player.getTotalArmorValue() <= buttonFadeThreshold.getIntValue()));
    prevArmor = player.getTotalArmorValue();
    properties.setFloat("alpha", fadeHelper.getAlpha());
  }

  public List getButtonList()
  {
    List buttonList = super.getButtonList();

    buttonList.add(buttonHideOnEmpty);
    buttonList.addAll(fadeHelper.getButtonList());
    if (fadeHelper.isFadeEnabled()) {
      buttonList.add(buttonFadeThreshold);
    }

    return buttonList;
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    fadeHelper.setNBT(nbt.getCompoundTag("fadeHelper"));
    buttonHideOnEmpty.setNBT(nbt.getCompoundTag("buttonHideOnEmpty"));
    buttonFadeThreshold.setNBT(nbt.getCompoundTag("buttonFadeThreshold"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("fadeHelper", fadeHelper.getNBT());
    nbt.setCompoundTag("buttonHideOnEmpty", buttonHideOnEmpty.getNBT());
    nbt.setCompoundTag("buttonFadeThreshold", buttonFadeThreshold.getNBT());
    super.saveToNBT(nbt);
  }

  public String getName()
  {
    return "Armor Bar";
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
    return AHud.screenHeight - 49;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 16, 0, 16, 16);
  }

  public boolean isRenderedInCreative()
  {
    return false;
  }
}
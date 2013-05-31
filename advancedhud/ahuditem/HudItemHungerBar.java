package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.RenderHelper;
import advancedhud.button.ButtonSlider;
import advancedhud.renderer.RendererIconStrip;
import advancedhud.renderer.RendererSolidBar1;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;

public class HudItemHungerBar extends HudItemDynamic
{
  protected ButtonSlider buttonFadeThreshold = new ButtonSlider(1, 1, 20, "Fade threshold", null, null, 0.85F);
  RendererIconStrip renderIconStrip = new RendererIconStrip(this);
  RendererSolidBar1 renderSolidBar = new RendererSolidBar1(this, 15171610);
  protected FadeHelper fadeHelper = new FadeHelper(8);
  protected int hunger;

  public HudItemHungerBar()
  {
    addRenderer(renderIconStrip);
    addRenderer(renderSolidBar);

    renderIconStrip.setFullSpritePos(52, 27);
    renderIconStrip.setHalfSpritePos(61, 27);
    renderIconStrip.setBackgroundSpritePos(16, 27);
    renderIconStrip.setDrawRtoL(true);
  }

  public void tick(NBTTagCompound properties)
  {
    EntityPlayer player = AHud.getMinecraftInstance().thePlayer;
    properties.setFloat("fill", player.getFoodStats().getFoodLevel() / 20.0F);
    properties.setBoolean("wiggle", (player.getFoodStats().getSaturationLevel() <= 0.0F) && (AHud.updateCounter % (player.getFoodStats().getFoodLevel() * 3 + 1) == 0));

    if (getCurrentRenderer() == renderIconStrip)
      renderIconStrip.setFullSpritePos(player.isPotionActive(Potion.hunger) ? 88 : 52, 27);
    else {
      renderSolidBar.setColor(player.isPotionActive(Potion.hunger) ? 10271250 : 15171610);
    }

    fadeHelper.tick((hunger != player.getFoodStats().getFoodLevel()) || (player.getFoodStats().getFoodLevel() <= buttonFadeThreshold.getIntValue()));
    hunger = player.getFoodStats().getFoodLevel();
    properties.setFloat("alpha", fadeHelper.getAlpha());
  }

  public List getButtonList()
  {
    List buttonList = super.getButtonList();

    buttonList.addAll(fadeHelper.getButtonList());
    if (fadeHelper.isFadeEnabled()) {
      buttonList.add(buttonFadeThreshold);
    }

    return buttonList;
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    fadeHelper.setNBT(nbt.getCompoundTag("fadeHelper"));
    buttonFadeThreshold.setNBT(nbt.getCompoundTag("buttonFadeThreshold"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("fadeHelper", fadeHelper.getNBT());
    nbt.setCompoundTag("buttonFadeThreshold", buttonFadeThreshold.getNBT());
    super.saveToNBT(nbt);
  }

  public String getName()
  {
    return "Hunger Bar";
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
    return AHud.screenHeight - 39;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 32, 0, 16, 16);
  }

  public boolean isRenderedInCreative()
  {
    return false;
  }
}
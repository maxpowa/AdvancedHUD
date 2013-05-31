package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.button.ButtonSlider;
import advancedhud.renderer.RendererIconStrip;
import advancedhud.renderer.RendererSolidBar1;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.storage.WorldInfo;

public class HudItemHealthBar extends HudItemDynamic
{
  protected ButtonSlider buttonFadeThreshold = new ButtonSlider(1, 1, 20, "Fade threshold", null, null, 0.85F);
  protected RendererIconStrip renderIconStrip;
  protected RendererSolidBar1 rendererSolidBar1;
  protected FadeHelper fadeHelper = new FadeHelper(8);
  protected int prevHealth;

  public HudItemHealthBar()
  {
    addRenderer(this.renderIconStrip = new RendererIconStrip(this));
    addRenderer(this.rendererSolidBar1 = new RendererSolidBar1(this, 16711680));

    renderIconStrip.setFullSpritePos(52, 0);
    renderIconStrip.setBackgroundSpritePos(16, 0);
  }

  public void tick(NBTTagCompound properties)
  {
    EntityPlayer player = AHud.getMinecraftInstance().thePlayer;
    properties.setFloat("fill", player.getHealth() / player.getMaxHealth());
    properties.setBoolean("wiggle", player.getHealth() <= 4);
    properties.setBoolean("regenEffect", player.isPotionActive(Potion.regeneration));

    if (getCurrentRenderer() == renderIconStrip)
    {
      int spriteX;
      if (player.isPotionActive(Potion.wither)) {
        spriteX = 142;
      }
      else
      {
        if (player.isPotionActive(Potion.poison))
          spriteX = 88;
        else {
          spriteX = 52;
        }
      }
      int spriteY = AHud.getMinecraftInstance().theWorld.getWorldInfo().isHardcoreModeEnabled() ? 45 : 0;

      renderIconStrip.setFullSpritePos(spriteX, spriteY);
      renderIconStrip.setHalfSpritePos(spriteX + 9, spriteY);

      int spriteBgX = (player.hurtResistantTime < 10) && (player.hurtResistantTime / 3 % 2 == 1) ? 25 : 16;
      renderIconStrip.setBackgroundSpritePos(spriteBgX, 0);
    }
    else if (player.isPotionActive(Potion.wither)) {
      rendererSolidBar1.setColor(4210752);
    } else if (player.isPotionActive(Potion.poison)) {
      rendererSolidBar1.setColor(13549641);
    } else {
      rendererSolidBar1.setColor(16711680);
    }

    fadeHelper.tick((prevHealth != player.getHealth()) || (player.getHealth() <= buttonFadeThreshold.getIntValue()));
    prevHealth = player.getHealth();
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
    return "Health Bar";
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
    return AHud.screenHeight - 39;
  }

  public boolean isRenderedInCreative()
  {
    return false;
  }
}
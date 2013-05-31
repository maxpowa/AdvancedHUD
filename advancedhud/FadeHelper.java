package advancedhud;

import advancedhud.button.ButtonSlider;
import advancedhud.button.ButtonToggle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;

public class FadeHelper
{
  protected float alpha;
  protected int updateCounter;
  protected ButtonToggle buttonFade;
  protected ButtonSlider buttonFadeSpeed;

  public FadeHelper(int initialFadeSpeed)
  {
    this(initialFadeSpeed, false);
  }

  public FadeHelper(int initialFadeSpeed, boolean isEnabled) {
    buttonFade = new ButtonToggle(0, "Fade", isEnabled);
    buttonFadeSpeed = new ButtonSlider(1, 1, 60, "Fade speed", null, null, "s", 0.5F);
    buttonFadeSpeed.setIntValue(initialFadeSpeed);
  }

  public void tick(boolean resetTimer) {
    if ((resetTimer) || (!isFadeEnabled())) {
      alpha = 1.0F;

      int fadeSpeed = buttonFadeSpeed.getIntValue() * 20;

      updateCounter = (AHud.updateCounter + fadeSpeed);
    } else {
      alpha = ((updateCounter - AHud.updateCounter) / 20.0F);
      alpha = Math.min(Math.max(alpha, 0.0F), 1.0F);
    }
  }

  public float getAlpha() {
    return alpha;
  }

  public boolean isFadeEnabled() {
    return buttonFade.value;
  }

  public void setNBT(NBTTagCompound nbt) {
    buttonFade.setNBT(nbt.getCompoundTag("buttonFade"));
    buttonFadeSpeed.setNBT(nbt.getCompoundTag("buttonFadeSpeed"));
  }

  public NBTTagCompound getNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setCompoundTag("buttonFade", buttonFade.getNBT());
    nbt.setCompoundTag("buttonFadeSpeed", buttonFadeSpeed.getNBT());
    return nbt;
  }

  public Collection getButtonList() {
    List buttonList = new ArrayList();

    buttonList.add(buttonFade);
    if (isFadeEnabled()) {
      buttonList.add(buttonFadeSpeed);
    }

    return buttonList;
  }
}
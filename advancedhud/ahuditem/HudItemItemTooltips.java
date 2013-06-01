package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.RenderHelper;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HudItemItemTooltips extends HudItem
{
  protected String itemName;
  protected String itemRarityColorCode;
  protected int stringPosX;
  protected int stringPosY;
  protected int stringColor;
  protected FadeHelper fadeHelper = new FadeHelper(8, true);

  public void tick()
  {
    if (AHud.getMinecraftInstance().thePlayer != null) {
      ItemStack currentItem = AHud.getMinecraftInstance().thePlayer.inventory.getCurrentItem();
      String currentName = currentItem == null ? "" : currentItem.getDisplayName();

      fadeHelper.tick(!currentName.equals(itemName));
      itemName = currentName;

      if (currentItem != null) {
        itemRarityColorCode = ("\u00A7" + Integer.toHexString(currentItem.getRarity().rarityColor));
        stringColor = (16777215 + ((int)(fadeHelper.getAlpha() * 255.0F) << 24));
      }
    }
  }

  public void render(float f)
  {
    if ((fadeHelper.getAlpha() > 0.0F) && (!itemName.isEmpty())) {
      FontRenderer fontrenderer = AHud.getMinecraftInstance().fontRenderer;
      int posX = 0;
      if (Alignment.isLeft(alignment))
        posX = this.posX;
      else if (Alignment.isHorizontalCenter(alignment))
        posX = this.posX + (getWidth() - AHud.getMinecraftInstance().fontRenderer.getStringWidth(itemName)) / 2;
      else {
        posX = this.posX + getWidth() - AHud.getMinecraftInstance().fontRenderer.getStringWidth(itemName);
      }

      fontrenderer.drawStringWithShadow(itemRarityColorCode + itemName, posX, posY, stringColor);
    }
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

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 112, 0, 16, 16);
  }

  public String getName()
  {
    return "Item Tooltips";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMCENTER;
  }

  public int getDefaultPosX()
  {
    return (AHud.screenWidth - getWidth()) / 2;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 59;
  }

  public int getWidth()
  {
    return 100;
  }

  public int getHeight()
  {
    return 8;
  }
}
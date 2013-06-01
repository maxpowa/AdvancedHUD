package advancedhud;

import advancedhud.ahuditem.HudItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.ModLoader;

public class AHud
{
  public static final String version = "@VERSION@";
  protected static final String MCversion = "@MCVERSION@";
  protected static List hudItemList = new ArrayList();

  protected static List hudItemListActive = new ArrayList();

  public static KeyBinding keyBinding = new KeyBinding("Advanced HUD", 35);
  public static int screenWidth;
  public static int screenHeight;
  public static int updateCounter;

  public static void registerHudItem(HudItem hudItem)
  {
    if (!hudItemList.contains(hudItem)) {
      hudItemList.add(hudItem);
      if (hudItem.isEnabledByDefault())
        enableHudItem(hudItem);
    }
  }

  public static List getHudItemList()
  {
    return hudItemList;
  }

  public static void enableHudItem(HudItem hudItem) {
    if ((hudItemList.contains(hudItem)) && (!hudItemListActive.contains(hudItem)))
      hudItemListActive.add(hudItem);
  }

  public static void disableHudItem(HudItem hudItem)
  {
    hudItemListActive.remove(hudItem);
  }

  public static List getActiveHudItemList()
  {
    return hudItemListActive;
  }

  public static boolean isActiveHudItem(HudItem hudItem)
  {
    return getActiveHudItemList().contains(hudItem);
  }

  public static Minecraft getMinecraftInstance() {
    return ModLoader.getMinecraftInstance();
  }

  public static String getMinecraftVersion() {
    return MCversion;
  }

  public static void checkForResize() {
    Minecraft mc = getMinecraftInstance();

    ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
    if ((scaledresolution.getScaledWidth() != screenWidth) || (scaledresolution.getScaledHeight() != screenHeight)) {
      if (screenWidth != 0) {
        fixHudItemOffsets(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), screenWidth, screenHeight);
      }
      screenWidth = scaledresolution.getScaledWidth();
      screenHeight = scaledresolution.getScaledHeight();
    }
  }

  private static void fixHudItemOffsets(int newScreenWidth, int newScreenHeight, int oldScreenWidth, int oldScreenHeight) {
    for (Object hudItem_ : hudItemList)
    {
	HudItem hudItem = (HudItem) hudItem_;
      if (Alignment.isHorizontalCenter(hudItem.alignment)) {
        int offsetX = hudItem.posX - oldScreenWidth / 2;
        hudItem.posX = (newScreenWidth / 2 + offsetX);
      } else if (Alignment.isRight(hudItem.alignment)) {
        int offsetX = hudItem.posX - oldScreenWidth;
        hudItem.posX = (newScreenWidth + offsetX);
      }

      if (Alignment.isVerticalCenter(hudItem.alignment)) {
        int offsetY = hudItem.posY - oldScreenHeight / 2;
        hudItem.posY = (newScreenHeight / 2 + offsetY);
      } else if (Alignment.isBottom(hudItem.alignment)) {
        int offsetY = hudItem.posY - oldScreenHeight;
        hudItem.posY = (newScreenHeight + offsetY);
      }
    }
  }

  public static void readFromNBT(NBTTagCompound nbt)
  {
    screenWidth = nbt.getInteger("screenWidth");
    screenHeight = nbt.getInteger("screenHeight");

    hudItemListActive = new ArrayList(hudItemList);
    for (int i = 0; i < hudItemList.size(); i++) {
      HudItem hudItem = (HudItem)hudItemList.get(i);
      if (!nbt.hasKey("active-" + hudItem.getName()))
        disableHudItem(hudItem);
    }
  }

  public static void writeToNBT(NBTTagCompound nbt)
  {
    nbt.setInteger("screenWidth", screenWidth);
    nbt.setInteger("screenHeight", screenHeight);

    for (Object hudItem_ : hudItemListActive) {
	HudItem hudItem = (HudItem) hudItem_;
      nbt.setString("active-" + hudItem.getName(), "");
  }
  }
}
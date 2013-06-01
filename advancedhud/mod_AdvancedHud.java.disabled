package advancedhud;

import advancedhud.AGuiInGame;
import advancedhud.AHud;
import advancedhud.SaveController;
import advancedhud.ahuditem.DefaultHudItems;
import advancedhud.ahuditem.HudItemDeathCounter;
import advancedhud.ahuditem.HudItemLightMeter;
import advancedhud.ahuditem.HudItemMap;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ReportedException;
import net.minecraftforge.client.GuiIngameForge;

public class mod_AdvancedHud extends BaseMod
{
  public void load()
  {
    registerKeyBinding();

    AHud.checkForResize();

    AHud.registerHudItem(DefaultHudItems.healthBar);
    AHud.registerHudItem(DefaultHudItems.armorBar);
    AHud.registerHudItem(DefaultHudItems.breathBar);
    AHud.registerHudItem(DefaultHudItems.hungerBar);
    AHud.registerHudItem(DefaultHudItems.itemBar);
    AHud.registerHudItem(DefaultHudItems.xpBar);
    AHud.registerHudItem(DefaultHudItems.lvlCounter);
    AHud.registerHudItem(DefaultHudItems.chat);
    AHud.registerHudItem(DefaultHudItems.crossHair);
    AHud.registerHudItem(DefaultHudItems.bossHealth);
    AHud.registerHudItem(DefaultHudItems.debug);
    AHud.registerHudItem(DefaultHudItems.musicOverlay);
    AHud.registerHudItem(DefaultHudItems.itemTooltips);

    AHud.registerHudItem(new HudItemMap());
    AHud.registerHudItem(new HudItemLightMeter());
    AHud.registerHudItem(new HudItemDeathCounter());

    ModLoader.setInGameHook(this, true, true);
  }

  public boolean onTickInGame(float f, Minecraft mc) {
    if (mc.ingameGUI.getClass() == GuiIngame.class || mc.ingameGUI.getClass() == GuiIngameForge.class)
      mc.ingameGUI = new AGuiInGame(mc);
    else if (mc.ingameGUI.getClass() != AGuiInGame.class) {
      throw new ReportedException(new CrashReport("Hud has already been modded.", new Throwable()));
    }

    String DS = File.separator;
    SaveController.saveConfig("default", "config" + DS + "AdvancedHud" + DS + "active");
    SaveController.loadConfig("active", "config" + DS + "AdvancedHud" + DS + "active");

    return false;
  }

  public String getVersion() {
    return AHud.version;
  }

  private void registerKeyBinding() {
    Minecraft mc = ModLoader.getMinecraftInstance();
    KeyBinding[] keyBindings = (KeyBinding[])mc.gameSettings.keyBindings.clone();
    mc.gameSettings.keyBindings = new KeyBinding[keyBindings.length + 1];
    for (int i = 0; i < keyBindings.length; i++) {
      mc.gameSettings.keyBindings[i] = keyBindings[i];
      mc.gameSettings.keyBindings[keyBindings.length] = AHud.keyBinding;
    }
  }
}
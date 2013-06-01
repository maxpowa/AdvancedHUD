package advancedhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.ModLoader;
import advancedhud.ahuditem.DefaultHudItems;
import advancedhud.ahuditem.HudItemDeathCounter;
import advancedhud.ahuditem.HudItemLightMeter;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="advancedhud", name="Advanced HUD", version="0.0.1")
public class mod_AdvancedHud {

        // The instance of your mod that Forge uses.
        @Instance("advancedhud")
        public static mod_AdvancedHud instance;
       
        @PreInit
        public void preInit(FMLPreInitializationEvent event) {
                // Stub Method
        }
       
        @Init
        public void load(FMLInitializationEvent event) {
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

            AHud.registerHudItem(new HudItemLightMeter());
            AHud.registerHudItem(new HudItemDeathCounter());

            TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
        }
       
        @PostInit
        public void postInit(FMLPostInitializationEvent event) {
                // Stub Method
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
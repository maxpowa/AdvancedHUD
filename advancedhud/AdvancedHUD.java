package advancedhud;

import java.io.File;
import java.util.logging.Logger;

import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.SaveController;
import advancedhud.client.huditems.HudItemHealth;
import advancedhud.client.huditems.HudItemHotbar;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "AdvancedHUD", name = "AdvancedHUD", version = "Version [@VERSION@] for @MCVERSION@")
public class AdvancedHUD {

    public static String MC_VERSION = "@MCVERSION@";
    public static String ADVHUD_VERSION = "@VERSION@";
    
    public static File configFile = null;
    
    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        System.out.println("Loaded config file: " + configFile.getAbsolutePath());
        KeyBindingRegistry.registerKeyBinding(new KeyRegister());
        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
        
        registerHUDItems();
    }
    
    @EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        if (!SaveController.loadConfig("config")) {
            ;
            SaveController.saveConfig("config");
        }
    }
    
    private void registerHUDItems() {
        HUDRegistry.registerHudItem(new HudItemHotbar());
        HUDRegistry.registerHudItem(new HudItemHealth());
    }    
}

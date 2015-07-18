package advancedhud;

import advancedhud.api.HUDRegistry;
import advancedhud.client.huditems.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = AdvancedHUD.MOD_ID, name = "AdvancedHUD", version = AdvancedHUD.VERSION)
public class AdvancedHUD {

    public final static String MOD_ID = "advancedhud";
    public final static String VERSION = "@VERSION@";

    public final static String MC_VERSION = "@MCVERSION@";
    public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new TickHandler());
        FMLCommonHandler.instance().bus().register(new KeyRegister());

        registerHUDItems();
    }

    private void registerHUDItems() {
        HUDRegistry.registerHudItem(new HudItemHotbar());
        HUDRegistry.registerHudItem(new HudItemHealth());
        HUDRegistry.registerHudItem(new HudItemAir());
        HUDRegistry.registerHudItem(new HudItemFood());
        HUDRegistry.registerHudItem(new HudItemArmor());
        HUDRegistry.registerHudItem(new HudItemBossBar());
        HUDRegistry.registerHudItem(new HudItemJumpBar());
        HUDRegistry.registerHudItem(new HudItemHealthMount());
        HUDRegistry.registerHudItem(new HudItemExperienceBar());
        HUDRegistry.registerHudItem(new HudItemCrosshairs());
        HUDRegistry.registerHudItem(new HudItemTooltips());
        HUDRegistry.registerHudItem(new HudItemRecordDisplay());
        HUDRegistry.setInitialLoadComplete(true);
    }
}

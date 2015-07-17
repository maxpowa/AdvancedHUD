package advancedhud;

import advancedhud.api.HUDRegistry;
import advancedhud.client.huditems.HudItemAir;
import advancedhud.client.huditems.HudItemArmor;
import advancedhud.client.huditems.HudItemBossBar;
import advancedhud.client.huditems.HudItemCrosshairs;
import advancedhud.client.huditems.HudItemExperienceBar;
import advancedhud.client.huditems.HudItemFood;
import advancedhud.client.huditems.HudItemHealth;
import advancedhud.client.huditems.HudItemHealthMount;
import advancedhud.client.huditems.HudItemHotbar;
import advancedhud.client.huditems.HudItemJumpBar;
import advancedhud.client.huditems.HudItemRecordDisplay;
import advancedhud.client.huditems.HudItemTooltips;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;




@Mod(modid = "AdvancedHUD", name = "AdvancedHUD", version = "1.1.0")
public class AdvancedHUD {

    public static String MC_VERSION = "1.8";
    public static String ADVHUD_VERSION = "1.1.0";
    public static Logger log;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
    }
    @Mod.EventHandler
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

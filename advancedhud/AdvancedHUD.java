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
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "AdvancedHUD", name = "AdvancedHUD", version = "Version [@VERSION@] for @MCVERSION@")
public class AdvancedHUD {

    public static String MC_VERSION = "@MCVERSION@";
    public static String ADVHUD_VERSION = "@VERSION@";

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        KeyBindingRegistry.registerKeyBinding(new KeyRegister());
        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);

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
    }
}

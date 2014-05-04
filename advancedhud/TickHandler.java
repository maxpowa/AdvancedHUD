package advancedhud;

import advancedhud.api.HUDRegistry;
import advancedhud.client.GuiAdvancedHUD;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;

public class TickHandler {

    private boolean ticked = false;
    private boolean firstload = true;

    @SubscribeEvent
    public void RenderTickEvent(RenderTickEvent event) {
        if ((event.type == Type.RENDER || event.type == Type.CLIENT) && event.phase == Phase.END) {
            if (!ticked && Minecraft.getMinecraft().ingameGUI != null) {
                Minecraft.getMinecraft().ingameGUI = new GuiAdvancedHUD(
                        Minecraft.getMinecraft());
                ticked = true;
            }
            if (firstload && Minecraft.getMinecraft() != null) {
                if (!SaveController.loadConfig("config")) {
                    HUDRegistry.checkForResize();
                    HUDRegistry.resetAllDefaults();
                    SaveController.saveConfig("config");
                }
                firstload = false;
    
            }
        }
    }

}

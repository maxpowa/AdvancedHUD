package advancedhud;

import net.minecraft.client.Minecraft;
import advancedhud.api.HUDRegistry;
import advancedhud.client.GuiAdvancedHUD;
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
            Minecraft mc = Minecraft.getMinecraft();
            if (!ticked && mc.ingameGUI != null) {
                mc.ingameGUI = new GuiAdvancedHUD(mc);
                ticked = true;
            }
            if (firstload && mc != null) {
                if (!SaveController.loadConfig("config")) {
                    HUDRegistry.checkForResize();
                    HUDRegistry.resetAllDefaults();
                    SaveController.saveConfig("config");
                }
                firstload = false;
            }
            // TODO Add notification on main menu when an update for advancedhud is available :)
            // if (mc.currentScreen instanceof GuiMainMenu) {
            // int mouseX = Mouse.getX() * mc.currentScreen.width / mc.displayWidth;
            // int mouseY = mc.currentScreen.height - Mouse.getY() * mc.currentScreen.height / mc.displayHeight - 1;
            // RenderAssist.drawCircle(mouseX, mouseY, 3, 24, 0xFFFFFFFF);
            // RenderAssist.drawRect(1, 1, 70, 11, 0x608f3eff);
            // mc.currentScreen.drawString(mc.fontRenderer, "AdvancedHUD!", 2, 2, 0x48dce9);
            // }
        }
    }

}

package advancedhud;

import advancedhud.api.HUDRegistry;
import advancedhud.client.GuiAdvancedHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler {

    private boolean ticked = false;
    private boolean firstload = true;

    @SubscribeEvent
    public void RenderTickEvent(TickEvent.RenderTickEvent event) {
        if ((event.type == TickEvent.Type.RENDER || event.type == TickEvent.Type.CLIENT) && event.phase == TickEvent.Phase.END) {
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

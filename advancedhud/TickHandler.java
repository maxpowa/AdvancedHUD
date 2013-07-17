package advancedhud;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import advancedhud.api.HUDRegistry;
import advancedhud.client.GuiAdvancedHUD;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

    private boolean ticked = false;
    private boolean firstload = true;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        // Do nothing.
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
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

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
    }

    @Override
    public String getLabel() {
        return "AdvancedHUD";
    }

}

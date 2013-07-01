package advancedhud;

import java.util.EnumSet;

import advancedhud.client.GuiAdvancedHUD;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

    boolean ticked;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        if (!ticked && Minecraft.getMinecraft().ingameGUI != null) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.ingameGUI = new GuiAdvancedHUD(mc);
            ticked = true;
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

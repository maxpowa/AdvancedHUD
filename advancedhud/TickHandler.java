package advancedhud;

import java.io.File;
import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.client.GuiIngameForge;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.ingameGUI.getClass() == GuiIngame.class || mc.ingameGUI.getClass() == GuiIngameForge.class)
          mc.ingameGUI = new AGuiInGame(mc);
        else if (mc.ingameGUI.getClass() != AGuiInGame.class) {
          throw new ReportedException(new CrashReport("Hud has already been modded.", new Throwable()));
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT, TickType.RENDER);
    }

    @Override
    public String getLabel() {
        return "Advanced HUD";
    }

}

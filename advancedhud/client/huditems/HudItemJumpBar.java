package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenHudItem;
import advancedhud.client.ui.GuiScreenReposition;


public class HudItemJumpBar extends HudItem {

    @Override
    public String getName() {
        return "jumpbar";
    }

    @Override
    public String getButtonLabel() {
        return "JUMPBAR";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight - 29;
    }

    @Override
    public int getWidth() {
        return 182;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    @Override
    public int getDefaultID() {
        return 9;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.startSection("jumpBar");
        RenderAssist.bindTexture(Gui.icons);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float charge = mc.thePlayer.getHorseJumpPower();
        final int barWidth = 182;
        int x = posX;
        int filled = (int) (charge * (barWidth + 1));
        int top = posY;

        RenderAssist.drawTexturedModalRect(x, top, 0, 84, barWidth, 5);

        if ((mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition)
                && filled == 0) {
            filled = 182;
        }

        if (filled > 0) {
            RenderAssist.drawTexturedModalRect(x, top, 0, 89, filled, 5);
        }

        mc.mcProfiler.endSection();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldDrawOnMount() {
        return true;
    }

    @Override
    public boolean shouldDrawAsPlayer() {
        return false;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen,
                this);
    }

    @Override
    public void rotate() {
        // TODO Auto-generated method stub
        
    }

}

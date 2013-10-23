package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;

public class HudItemCrosshairs extends HudItem {

    @Override
    public String getName() {
        return "crosshair";
    }

    @Override
    public String getButtonLabel() {
        return "CROSSHAIRS";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.CENTERCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 - 8;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight / 2 - 8;
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public int getDefaultID() {
        return 11;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.startSection("crosshair");
        RenderAssist.bindTexture(Gui.icons);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR,
                GL11.GL_ONE_MINUS_SRC_COLOR);
        RenderAssist.drawTexturedModalRect(posX, posY, 0, 0, 16, 16);
        GL11.glDisable(GL11.GL_BLEND);
        mc.mcProfiler.endSection();
    }

    @Override
    public boolean isMoveable() {
        return false;
    }

    @Override
    public boolean shouldDrawOnMount() {
        return true;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen,
                this);
    }
}


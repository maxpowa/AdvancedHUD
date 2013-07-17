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

public class HudItemExperienceBar extends HudItem {

    @Override
    public String getName() {
        return "experiencebar";
    }

    @Override
    public String getButtonLabel() {
        return "EXPERIENCEBAR";
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
        return 8;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderAssist.bindTexture(Gui.field_110324_m);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        mc.mcProfiler.startSection("expBar");
        int cap = mc.thePlayer.xpBarCap();
        int left = posX;

        if (cap > 0) {
            short barWidth = 182;
            int filled = (int) (mc.thePlayer.experience * (barWidth + 1));
            int top = posY;
            RenderAssist.drawTexturedModalRect(left, top, 0, 64, barWidth, 5);

            if ((mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition)
                    && filled == 0) {
                filled = 182;
            }

            if (filled > 0) {
                RenderAssist.drawTexturedModalRect(left, top, 0, 69, filled, 5);
            }
        }

        mc.mcProfiler.endSection();

        if (mc.playerController.func_78763_f()
                && mc.thePlayer.experienceLevel > 0) {
            mc.mcProfiler.startSection("expLevel");
            boolean flag1 = false;
            int color = flag1 ? 16777215 : 8453920;
            String text = "" + mc.thePlayer.experienceLevel;
            int x = posX + 91 - mc.fontRenderer.getStringWidth(text) / 2;
            int y = posY - 6;
            mc.fontRenderer.drawString(text, x + 1, y, 0);
            mc.fontRenderer.drawString(text, x - 1, y, 0);
            mc.fontRenderer.drawString(text, x, y + 1, 0);
            mc.fontRenderer.drawString(text, x, y - 1, 0);
            mc.fontRenderer.drawString(text, x, y, color);
            mc.mcProfiler.endSection();
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isRenderedInCreative() {
        return false;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen,
                this);
    }

}

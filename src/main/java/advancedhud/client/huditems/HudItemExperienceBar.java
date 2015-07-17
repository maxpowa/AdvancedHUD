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
        return rotated? Alignment.CENTERRIGHT : Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        if (rotated)
            return HUDRegistry.screenWidth - 29;
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        if (rotated)
            return HUDRegistry.screenHeight / 2 - 91;
        return HUDRegistry.screenHeight - 29;
    }

    @Override
    public int getWidth() {
        return rotated ? 4 : 182;
    }

    @Override
    public int getHeight() {
        return rotated ? 182 : 4;
    }

    @Override
    public int getDefaultID() {
        return 8;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        RenderAssist.bindTexture(Gui.icons);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int cap = mc.thePlayer.xpBarCap();
        int left = posX;

        if (cap > 0) {
            short barWidth = 182;
            int filled = (int) (mc.thePlayer.experience * (barWidth + 1));
            int top = posY;
            if (rotated) {
                GL11.glTranslatef(left+5, top, 0.0F);
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
            } else {
                GL11.glTranslatef(left, top, 0.0F);
            }
            RenderAssist.drawTexturedModalRect(0, 0, 0, 64, barWidth, 5);

            if ((mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition) && filled == 0) {
                filled = 182;
            }

            if (filled > 0) {
                RenderAssist.drawTexturedModalRect(0, 0, 0, 69, filled, 5);
            }
        }

        if (mc.playerController.isNotCreative() && mc.thePlayer.experienceLevel > 0) {
            boolean flag1 = false;
            int color = flag1 ? 16777215 : 8453920;
            String text = "" + mc.thePlayer.experienceLevel;
            int x = posX + 91 - mc.fontRendererObj.getStringWidth(text) / 2;
            int y = posY - 6;
            mc.fontRendererObj.drawString(text, x + 1, y, 0);
            mc.fontRendererObj.drawString(text, x - 1, y, 0);
            mc.fontRendererObj.drawString(text, x, y + 1, 0);
            mc.fontRendererObj.drawString(text, x, y - 1, 0);
            mc.fontRendererObj.drawString(text, x, y, color);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public boolean isRenderedInCreative() {
        return false;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen, this);
    }
}

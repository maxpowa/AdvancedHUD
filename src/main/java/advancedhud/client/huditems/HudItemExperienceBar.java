package advancedhud.client.huditems;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenHudItem;
import advancedhud.client.ui.GuiScreenReposition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class HudItemExperienceBar extends HudItem {

    private float max_xp = 0;
    private float current_xp = 0;
    private int current_level = 0;

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
        return rotated ? 5 : 182;
    }

    @Override
    public int getHeight() {
        return rotated ? 182 : 5;
    }

    @Override
    public int getDefaultID() {
        return 8;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderAssist.bindTexture(Gui.icons);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int left = posX;

        if (this.max_xp > 0) {
            short barWidth = 182;
            int filled = (int) (this.current_xp * (barWidth + 1));
            int top = posY;
            if (rotated) {
                GL11.glTranslatef(left+5, top, 0.0F);
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
            } else {
                GL11.glTranslatef(left, top, 0.0F);
            }
            RenderAssist.drawTexturedModalRect(0, 0, 0, 64, barWidth, 5);

            if ((mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition) && filled == 0) {
                filled = 91;
            }

            if (filled > 0) {
                RenderAssist.drawTexturedModalRect(0, 0, 0, 69, filled, 5);
            }
        }

        if (mc.playerController.isNotCreative() && this.current_level > 0) {
            int color = 8453920;
            String text = "" + this.current_level;
            int x = (this.getWidth() / 2) - mc.fontRenderer.getStringWidth(text) / 2;
            int y = -Math.round(this.getHeight() * 0.75f);
            mc.fontRenderer.drawString(text, x + 1, y, 0);
            mc.fontRenderer.drawString(text, x - 1, y, 0);
            mc.fontRenderer.drawString(text, x, y + 1, 0);
            mc.fontRenderer.drawString(text, x, y - 1, 0);
            mc.fontRenderer.drawString(text, x, y, color);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void tick() {
        Minecraft mc = Minecraft.getMinecraft();
        this.max_xp = mc.thePlayer.xpBarCap();
        this.current_xp = mc.thePlayer.experience;
        this.current_level = mc.thePlayer.experienceLevel;
    }

    @Override
    public boolean needsTick() {
        return true;
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

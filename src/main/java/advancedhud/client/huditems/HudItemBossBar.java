package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.boss.BossStatus;

import org.lwjgl.opengl.GL11;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenHudItem;
import advancedhud.client.ui.GuiScreenReposition;

public class HudItemBossBar extends HudItem {

    @Override
    public String getName() {
        return "bossbar";
    }

    @Override
    public String getButtonLabel() {
        return "BOSSBAR";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.TOPCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 182;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getDefaultID() {
        return 6;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        RenderAssist.bindTexture("textures/gui/icons.png");
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0 || mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition) {
            if (BossStatus.bossName != null) {
                --BossStatus.statusBarTime;
            }
            FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;

            short short1 = 182;
            int j = posX;
            int k = (int) (BossStatus.healthScale * (short1 + 1));
            int b0 = posY + 11;
            RenderAssist.drawTexturedModalRect(j, b0, 0, 74, short1, 5);
            RenderAssist.drawTexturedModalRect(j, b0, 0, 74, short1, 5);

            if (BossStatus.bossName == null) {
                k = 182;
            }
            if (k > 0) {
                RenderAssist.drawTexturedModalRect(j, b0, 0, 79, k, 5);
            }

            String s = BossStatus.bossName != null ? BossStatus.bossName : "AdvancedHUD Config";
            fontrenderer.drawStringWithShadow(s, posX + 91 - fontrenderer.getStringWidth(s) / 2, b0 - 10, 16777215);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderAssist.bindTexture(Gui.icons);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen, this);
    }

}

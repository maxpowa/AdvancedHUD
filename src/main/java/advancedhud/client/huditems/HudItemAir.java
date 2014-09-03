package advancedhud.client.huditems;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenHudItem;
import advancedhud.client.ui.GuiScreenReposition;

public class HudItemAir extends HudItem {

    @Override
    public String getName() {
        return "air";
    }

    @Override
    public String getButtonLabel() {
        return "AIR";
    }

    @Override
    public Alignment getDefaultAlignment() {
        if (rotated)
            return Alignment.CENTERRIGHT;
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        if (rotated)
            return HUDRegistry.screenWidth - 49;
        return HUDRegistry.screenWidth / 2 + 10;
    }

    @Override
    public int getDefaultPosY() {
        if (rotated)
            return HUDRegistry.screenHeight / 2 + 10;
        return HUDRegistry.screenHeight - 49;
    }

    @Override
    public int getWidth() {
        if (rotated)
            return 9;
        return 81;
    }

    @Override
    public int getHeight() {
        if (rotated)
            return 81;
        return 9;
    }

    @Override
    public int getDefaultID() {
        return 3;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        RenderAssist.bindTexture(Gui.icons);
        int left = posX + 81;
        int top = posY;

        if (mc.thePlayer.isInsideOfMaterial(Material.water) || mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition) {
            int air = mc.thePlayer.getAir();
            int full = MathHelper.ceiling_double_int((air - 2) * 10.0D / 300.0D);
            int partial = MathHelper.ceiling_double_int(air * 10.0D / 300.0D) - full;

            for (int i = 0; i < full + partial; ++i) {
                if (!rotated)
                    RenderAssist.drawTexturedModalRect(left - i * 8 - 9, top, i < full ? 16 : 25, 18, 9, 9);
                else
                    RenderAssist.drawTexturedModalRect(left - 81, top + 72 - i * 8, i < full ? 16 : 25, 18, 9, 9);
            }
        }
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

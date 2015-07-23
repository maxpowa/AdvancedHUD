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
import net.minecraftforge.common.ForgeHooks;

public class HudItemArmor extends HudItem {

    @Override
    public String getName() {
        return "armor";
    }

    @Override
    public String getButtonLabel() {
        return "ARMOR";
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
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        if (rotated)
            return HUDRegistry.screenHeight / 2 - 91;
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
        return 5;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderAssist.bindTexture(Gui.icons);

        int left = posX;
        int top = posY;

        int level = ForgeHooks.getTotalArmorValue(mc.thePlayer);
        if ((mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition) && level == 0) {
            level = 10;
        }
        for (int i = 1; level > 0 && i < 20; i += 2) {
            if (i < level) {
                RenderAssist.drawTexturedModalRect(left, top, 34, 9, 9, 9);
            } else if (i == level) {
                RenderAssist.drawTexturedModalRect(left, top, 25, 9, 9, 9);
            } else if (i > level) {
                RenderAssist.drawTexturedModalRect(left, top, 16, 9, 9, 9);
            }
            if (!rotated) {
                left += 8;
            } else {
                top += 8;
            }
        }
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

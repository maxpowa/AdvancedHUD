package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeHooks;
import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.GuiAdvancedHUD;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenReposition;

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
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight - 49;
    }

    @Override
    public int getWidth() {
        return 81;
    }

    @Override
    public int getHeight() {
        return 9;
    }

    @Override
    public int getDefaultID() {
        return 5;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.startSection("armor");
        RenderAssist.bindTexture(GuiAdvancedHUD.field_110324_m);

        int left = posX;
        int top = posY;

        int level = ForgeHooks.getTotalArmorValue(mc.thePlayer);
        if ((mc.currentScreen instanceof GuiAdvancedHUDConfiguration
                || mc.currentScreen instanceof GuiScreenReposition) && level == 0) {
            level = 10;
        }
        for (int i = 1; level > 0 && i < 20; i += 2)
        {
            if (i < level)
            {
                RenderAssist.drawTexturedModalRect(left, top, 34, 9, 9, 9);
            }
            else if (i == level)
            {
                RenderAssist.drawTexturedModalRect(left, top, 25, 9, 9, 9);
            }
            else if (i > level)
            {
                RenderAssist.drawTexturedModalRect(left, top, 16, 9, 9, 9);
            }
            left += 8;
        }

        mc.mcProfiler.endSection();
    }

}

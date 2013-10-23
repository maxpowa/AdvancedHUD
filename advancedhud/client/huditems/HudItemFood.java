package advancedhud.client.huditems;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;

public class HudItemFood extends HudItem {

    Random rand = new Random();

    @Override
    public String getName() {
        return "food";
    }

    @Override
    public String getButtonLabel() {
        return "FOOD";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 + 10;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight - 39;
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
        return 4;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.startSection("food");
        RenderAssist.bindTexture(Gui.icons);

        int left = posX + 81;
        int top = posY;
        boolean unused = false;// Unused flag in vanilla, seems to be part of a
                               // 'fade out' mechanic

        FoodStats stats = mc.thePlayer.getFoodStats();
        int level = stats.getFoodLevel();
        int levelLast = stats.getPrevFoodLevel();

        for (int i = 0; i < 10; ++i) {

            int idx = i * 2 + 1;
            int x = left - i * 8 - 9;
            int y = top;
            int icon = 16;
            byte backgound = 0;

            if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                icon += 36;
                backgound = 13;
            }
            if (unused) {
                backgound = 1; // Probably should be a += 1 but vanilla never
                               // uses this
            }

            if (mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F
                    && mc.ingameGUI.getUpdateCounter() % (level * 3 + 1) == 0) {
                y = top + rand.nextInt(3) - 1;
            }

            RenderAssist.drawTexturedModalRect(x, y, 16 + backgound * 9, 27, 9,
                    9);

            if (unused) {
                if (idx < levelLast) {
                    RenderAssist.drawTexturedModalRect(x, y, icon + 54, 27, 9,
                            9);
                } else if (idx == levelLast) {
                    RenderAssist.drawTexturedModalRect(x, y, icon + 63, 27, 9,
                            9);
                }
            }

            if (idx < level) {
                RenderAssist.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
            } else if (idx == level) {
                RenderAssist.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
            }
        }
        mc.mcProfiler.endSection();
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

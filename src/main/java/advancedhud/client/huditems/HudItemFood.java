package advancedhud.client.huditems;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import org.lwjgl.opengl.GL11;

import java.util.Random;

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
        if (rotated)
            return Alignment.CENTERRIGHT;
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        if (rotated)
            return HUDRegistry.screenWidth - 39;
        return HUDRegistry.screenWidth / 2 + 10;
    }

    @Override
    public int getDefaultPosY() {
        if (rotated)
            return HUDRegistry.screenHeight / 2 + 10;
        return HUDRegistry.screenHeight - 39;
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
        return 4;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderAssist.bindTexture(Gui.icons);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.getOpacity());

        int left = posX + 81;
        int top = posY;

        FoodStats stats = mc.thePlayer.getFoodStats();
        int level = stats.getFoodLevel();

        for (int i = 0; i < 10; ++i) {

            int idx = i * 2 + 1;
            int x = left - i * 8 - 9;
            int y = top;
            
            if (rotated){
                x = left - 81;
                y = top + 82 - i*8 -9;
            }
            
            int icon = 16;
            byte backgound = 0;

            if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                icon += 36;
                backgound = 13;
            }

            if (mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F && mc.ingameGUI.getUpdateCounter() % (level * 3 + 1) == 0) {
                y = top + rand.nextInt(3) - 1;
                if (rotated) {
                    x = left-81+rand.nextInt(3)-1;
                    y = top + 82 - i*8 -9;
                }
            }

            RenderAssist.drawTexturedModalRect(x, y, 16 + backgound * 9, 27, 9, 9);

            if (idx < level) {
                RenderAssist.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
            } else if (idx == level) {
                RenderAssist.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
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

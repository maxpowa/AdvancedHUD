package advancedhud.client.huditems;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.client.ui.GuiScreenHudItem;

public class HudItemRecordDisplay extends HudItem {

    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    private String recordPlaying;

    @Override
    public String getName() {
        return "record";
    }

    @Override
    public String getButtonLabel() {
        return "RECORDDISPLAY";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight - 48;
    }

    @Override
    public int getWidth() {
        return 36;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public int getDefaultID() {
        return 12;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen, this);
    }

    @Override
    public void render(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (recordPlayingUpFor > 0) {
            float hue = recordPlayingUpFor - partialTicks;
            int opacity = (int) (hue * 256.0F / 20.0F);
            if (opacity > 255) {
                opacity = 255;
            }

            if (opacity > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef(posX, posY, 0.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int color = recordIsPlaying ? Color.HSBtoRGB(hue / 50.0F, 0.7F, 0.6F) & 0xFFFFFF : 0xFFFFFF;
                mc.fontRenderer.drawString(recordPlaying, -mc.fontRenderer.getStringWidth(recordPlaying) / 2, -4, color | opacity << 24);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    public boolean needsTick() {
        return true;
    }

    @Override
    public void tick() {
        if (recordPlayingUpFor > 0) {
            --recordPlayingUpFor;
        }
    }

}

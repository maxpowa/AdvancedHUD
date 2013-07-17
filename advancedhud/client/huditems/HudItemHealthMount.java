package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.GuiAdvancedHUD;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenReposition;

public class HudItemHealthMount extends HudItem {

    @Override
    public String getName() {
        return "healthmount";
    }

    @Override
    public String getButtonLabel() {
        return "HEALTH(MOUNT)";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.BOTTOMRIGHT;
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
        return 7;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.mcProfiler.startSection("healthmount");
        Entity tmp = mc.thePlayer.ridingEntity;
        int right_height = 1;
        if (tmp == null && (mc.currentScreen instanceof GuiAdvancedHUDConfiguration
                || mc.currentScreen instanceof GuiScreenReposition)) {
            tmp = new EntityHorse(mc.theWorld);
        }
        if (!(tmp instanceof EntityLivingBase)) return;

        RenderAssist.bindTexture(GuiAdvancedHUD.field_110324_m);
        
        boolean unused = false;
        int left_align = posX+81;
        
        EntityLivingBase mount = (EntityLivingBase)tmp;
        int health = (int)Math.ceil((double)mount.func_110143_aJ());
        float healthMax = mount.func_110138_aP();
        int hearts = (int)(healthMax + 0.5F) / 2;

        if (hearts > 30) hearts = 30;
        
        final int MARGIN = 52;
        final int BACKGROUND = MARGIN + (unused ? 1 : 0);
        final int HALF = MARGIN + 45;
        final int FULL = MARGIN + 36;

        for (int heart = 0; hearts > 0; heart += 20)
        {
            int top = posY+1-right_height;
            
            int rowCount = Math.min(hearts, 10);
            hearts -= rowCount;

            for (int i = 0; i < rowCount; ++i)
            {
                int x = left_align - i * 8 - 9;
                RenderAssist.drawTexturedModalRect(x, top, BACKGROUND, 9, 9, 9);

                if (i * 2 + 1 + heart < health)
                    RenderAssist.drawTexturedModalRect(x, top, FULL, 9, 9, 9);
                else if (i * 2 + 1 + heart == health)
                    RenderAssist.drawTexturedModalRect(x, top, HALF, 9, 9, 9);

                right_height = (i+1);
            }
        }
    }

    public boolean isDrawnWhenOnMount() {
        return true;
    }

}

package advancedhud.client.huditems;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class HudItemHealth extends HudItem {

    Minecraft mc = null;
    Random rand = new Random();
    Float prevHealth;

    @Override
    public String getName() {
        return "health";
    }

    @Override
    public String getButtonLabel() {
        return "HEALTH";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return rotated? Alignment.CENTERRIGHT : Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        if (rotated)
            return HUDRegistry.screenWidth - 39;
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        if (rotated)
            return HUDRegistry.screenHeight / 2 - 91;
        return HUDRegistry.screenHeight - 39;
    }

    @Override
    public boolean isRenderedInCreative() {
        return false;
    }

    @Override
    public int getWidth() {
        return rotated ? 9 : 81;
    }

    @Override
    public int getHeight() {
        return rotated ? 81 : 9;
    }

    @Override
    public void render(float partialTicks) {
        if (mc == null)
            mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        RenderAssist.bindTexture(Gui.icons);
        boolean highlight = mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
        
        if (mc.thePlayer.hurtResistantTime < 10)
        {
            highlight = false;
        }

        IAttributeInstance attrMaxHealth = mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        int health = MathHelper.ceiling_float_int(mc.thePlayer.getHealth());
        int healthLast = MathHelper.ceiling_float_int(prevHealth == null ? health : prevHealth);
        prevHealth = (float) health;
        float healthMax = (float)attrMaxHealth.getAttributeValue();
        float absorb = mc.thePlayer.getAbsorptionAmount();

        int healthRows = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        this.rand.setSeed((long)(mc.ingameGUI.getUpdateCounter() * 312871));

        int left = posX;
        int top = posY;

        int regen = -1;
        if (mc.thePlayer.isPotionActive(Potion.regeneration))
        {
            regen = mc.ingameGUI.getUpdateCounter() % 25;
        }

        final int TOP =  9 * (mc.theWorld.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
        final int BACKGROUND = (highlight ? 25 : 16);
        int MARGIN = 16;
        if (mc.thePlayer.isPotionActive(Potion.poison))      MARGIN += 36;
        else if (mc.thePlayer.isPotionActive(Potion.wither)) MARGIN += 72;
        float absorbRemaining = absorb;
        if (!rotated) {
            for (int i = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F) - 1; i >= 0; --i)
            {
                //int b0 = (highlight ? 1 : 0);
                int row = MathHelper.ceiling_float_int((float)(i + 1) / 10.0F) - 1;
                int x = left + i % 10 * 8;
                int y = top - row * rowHeight;
    
                if (health <= 4) y += rand.nextInt(2);
                if (i == regen) y -= 2;
    
                RenderAssist.drawTexturedModalRect(x, y, BACKGROUND, TOP, 9, 9);
    
                if (highlight)
                {
                    if (i * 2 + 1 < healthLast)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 54, TOP, 9, 9); //6
                    else if (i * 2 + 1 == healthLast)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 63, TOP, 9, 9); //7
                }
    
                if (absorbRemaining > 0.0F)
                {
                    if (absorbRemaining == absorb && absorb % 2.0F == 1.0F)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 153, TOP, 9, 9); //17
                    else
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 144, TOP, 9, 9); //16
                    absorbRemaining -= 2.0F;
                }
                else
                {
                    if (i * 2 + 1 < health)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 36, TOP, 9, 9); //4
                    else if (i * 2 + 1 == health)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 45, TOP, 9, 9); //5
                }
            }
        } else {
            for (int i = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F) - 1; i >= 0; --i)
            {
                //int b0 = (highlight ? 1 : 0);
                int row = MathHelper.ceiling_float_int((float)(i + 1) / 10.0F) - 1;
                int x = left - row * rowHeight;
                int y = top + i % 10 * 8;
    
                if (health <= 4) y += rand.nextInt(2);
                if (i == regen) y -= 2;
    
                RenderAssist.drawTexturedModalRect(x, y, BACKGROUND, TOP, 9, 9);
    
                if (highlight)
                {
                    if (i * 2 + 1 < healthLast)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 54, TOP, 9, 9); //6
                    else if (i * 2 + 1 == healthLast)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 63, TOP, 9, 9); //7
                }
    
                if (absorbRemaining > 0.0F)
                {
                    if (absorbRemaining == absorb && absorb % 2.0F == 1.0F)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 153, TOP, 9, 9); //17
                    else
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 144, TOP, 9, 9); //16
                    absorbRemaining -= 2.0F;
                }
                else
                {
                    if (i * 2 + 1 < health)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 36, TOP, 9, 9); //4
                    else if (i * 2 + 1 == health)
                        RenderAssist.drawTexturedModalRect(x, y, MARGIN + 45, TOP, 9, 9); //5
                }
            }
            
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public int getDefaultID() {
        return 2;
    }

    @Override
    public boolean shouldDrawOnMount() {
        return true;
    }

    @Override
    public boolean shouldDrawAsPlayer() {
        return true;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen, this);
    }
}

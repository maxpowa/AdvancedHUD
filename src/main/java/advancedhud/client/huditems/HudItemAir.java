package advancedhud.client.huditems;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenHudItem;
import advancedhud.client.ui.GuiScreenReposition;
import aurelienribon.tweenengine.Tween;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class HudItemAir extends HudItem {

    private boolean wasInWater = true;

    public HudItemAir() {
        // If we override the constructor, we should ALWAYS call super()! It's important!
        super();
        // Set up tweening for opacity
        Tween.registerAccessor(HudItemAir.class, new HudItemAir.Engine());
    }

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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft mc = Minecraft.getMinecraft();
        RenderAssist.bindTexture(Gui.icons);
        int left = posX + 81;
        int top = posY;

        if (mc.thePlayer.isInsideOfMaterial(Material.water) || mc.currentScreen instanceof GuiAdvancedHUDConfiguration || mc.currentScreen instanceof GuiScreenReposition) {
            if (!wasInWater) {
                Tween.to(this, Engine.OPACITY, 0.1f).target(1.0f).start(manager);
                wasInWater = true;
            }
        }
        if (!mc.thePlayer.isInsideOfMaterial(Material.water) && wasInWater) {
            Tween.to(this, Engine.OPACITY, 1.0f).delay(1.0f).target(0.0f).start(manager);
            wasInWater = false;
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.getOpacity());
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

    @Override
    public boolean needsTween() {
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

    // This is the tweening engine's interface. Here we register any tween actions we might want to perform.
    public static class Engine implements TweenEngine<HudItemAir> {

        // For HudItemAir we really only need opacity, so we only register this action.
        public static final int OPACITY = 1;

        @Override
        public int getValues(HudItemAir target, int tweenType, float[] returnValues) {
            switch (tweenType) {
                case OPACITY: returnValues[0] = target.getOpacity(); return 1;
                default: assert false; return -1;
            }
        }

        @Override
        public void setValues(HudItemAir target, int tweenType, float[] newValues) {
            switch (tweenType) {
                case OPACITY: target.setOpacity(newValues[0]); break;
                default: assert false; break;
            }

        }
    }

}

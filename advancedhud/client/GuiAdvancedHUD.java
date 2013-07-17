package advancedhud.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.client.GuiIngameForge;

import org.lwjgl.opengl.GL11;

import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;

public class GuiAdvancedHUD extends GuiIngameForge {

    public static float partialTicks;

    private ScaledResolution res = null;

    public GuiAdvancedHUD(Minecraft mc) {
        super(mc);
    }

    @Override
    public void renderGameOverlay(float partialTicks, boolean hasScreen,
            int mouseX, int mouseY) {
        Profiler profiler = mc.mcProfiler;
        profiler.startSection("Advanced Hud");

        GuiAdvancedHUD.partialTicks = partialTicks;

        HUDRegistry.checkForResize();

        mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);

        res = new ScaledResolution(mc.gameSettings, mc.displayWidth,
                mc.displayHeight);

        for (HudItem huditem : HUDRegistry.getHudItemList()) {
            if (mc.playerController.isInCreativeMode()
                    && !huditem.isRenderedInCreative()) {
                continue;
            }
            if (mc.thePlayer.ridingEntity instanceof EntityLivingBase) {
                if (huditem.shouldDrawOnMount()) {
                    huditem.fixBounds();
                    huditem.render(partialTicks);
                }
            } else {
                if (huditem.shouldDrawAsPlayer()) {
                    huditem.fixBounds();
                    huditem.render(partialTicks);
                }
            }
        }
        profiler.endSection();
    }

    @Override
    public ScaledResolution getResolution() {
        return res;
    }

    @Override
    public void updateTick() {
        super.updateTick();
    }
}

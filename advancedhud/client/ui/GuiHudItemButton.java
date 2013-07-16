package advancedhud.client.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.client.GuiAdvancedHUD;

public class GuiHudItemButton extends GuiButton {

    public GuiHudItemButton(int id, int xPosition, int yPosition, int width,
            int height, String label) {
        super(id, xPosition, yPosition, width, height, label);
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        displayString = label;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (drawButton) {
            GL11.glPushMatrix();
            FontRenderer fontrenderer = mc.fontRenderer;
            bind(new ResourceLocation("/gui/gui.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hoverState = mouseX >= xPosition && mouseY >= yPosition
                    && mouseX < xPosition + width
                    && mouseY < yPosition + height;
            for (HudItem huditem : HUDRegistry.getActiveHudItemList()) {
                if (huditem.getButtonLabel().equalsIgnoreCase(displayString)) {
                    huditem.render(GuiAdvancedHUD.partialTicks);
                }
            }
            GL11.glPopMatrix();

            if (hoverState) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0, 0, 200F);
                this.drawCenteredString(fontrenderer, displayString, xPosition
                        + width / 2, yPosition + (height - 8) / 2 + 1, 0xFFFFFF);
                GL11.glPopMatrix();
            }

        }
    }
    
    private void bind(ResourceLocation res)
    {
        Minecraft.getMinecraft().func_110434_K().func_110577_a(res);
    }
}

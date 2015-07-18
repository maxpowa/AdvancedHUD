package advancedhud.client.ui;

import advancedhud.AdvancedHUD;
import advancedhud.api.RenderAssist;
import advancedhud.client.huditems.HudItemCrosshairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiButtonIconGrid extends GuiButton {

    private ResourceLocation resourceLocation = new ResourceLocation(AdvancedHUD.MOD_ID, "textures/gui/crosshairs.png");

    private final HudItemCrosshairs crosshairs;

    public GuiButtonIconGrid(int id, int xPosition, int yPosition, HudItemCrosshairs crosshairs, String label) {
        super(id, xPosition, yPosition, 256, 64, label);
        this.crosshairs = crosshairs;
    }

    public GuiButtonIconGrid(int id, int xPosition, int yPosition, HudItemCrosshairs crosshairs, String label, ResourceLocation resourceLocation) {
        super(id, xPosition, yPosition, 256, 64, label);
        this.resourceLocation = resourceLocation;
        this.crosshairs = crosshairs;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        RenderAssist.drawRect(xPosition - 0.5F, yPosition - 0.5F, xPosition + 256.5F, yPosition + 64.5F, 0x80000000);
        RenderAssist.drawUnfilledRect(xPosition - 0.5F, yPosition - 0.5F, xPosition + 256.5F, yPosition + 64.5F, 0xFFFFFFFF);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        //GL11.glScalef(0.5F, 0.5F, 1F);
        RenderAssist.bindTexture(resourceLocation);
        RenderAssist.drawTexturedModalRect(xPosition, yPosition, 0, 0, 256, 64);
        if (mouseX > xPosition && mouseY > yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            float posX = ((mouseX - xPosition) - (mouseX - xPosition)%16) + xPosition;
            float posY = ((mouseY - yPosition) - (mouseY - yPosition)%16) + yPosition;
            int color = 0xFF1f95ff;
            if (Mouse.isButtonDown(0)) 
                color = 0xFF1059f7;
            RenderAssist.drawUnfilledRect(posX-0.125F, posY-0.125F, posX + 16.125F, posY + 16.125F, color);
        }
        if (crosshairs.getSelectedIconX() >= 0 && crosshairs.getSelectedIconY() >= 0)
            RenderAssist.drawUnfilledRect(xPosition+crosshairs.getSelectedIconX() - 0.375F, yPosition+crosshairs.getSelectedIconY() - 0.5F, xPosition+crosshairs.getSelectedIconX() + 16, yPosition+crosshairs.getSelectedIconY() + 16F, 0xFFFFFFFF);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (mouseX > xPosition && mouseY > yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            crosshairs.setSelectedIconX((mouseX - xPosition) - (mouseX - xPosition)%16);
            crosshairs.setSelectedIconY((mouseY - yPosition) - (mouseY - yPosition)%16);
        }
        //AdvancedHUD.log.info("Selected Icon: "+selectedIconX+","+selectedIconY);
        return super.mousePressed(mc, mouseX, mouseY);
    }

}

package advancedhud.client.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import advancedhud.AdvancedHUD;
import advancedhud.api.HUDRegistry;
import advancedhud.api.RenderAssist;

public class GuiButtonIconGrid extends GuiButton {

    private ResourceLocation resloc = new ResourceLocation("advancedhud", "textures/gui/ahud_crosshairs.png");
    private int selectedIconX = -1;
    private int selectedIconY = -1;

    public GuiButtonIconGrid(int id, int xPosition, int yPosition, String label) {
        super(id, xPosition, yPosition, 128, 32, label);
    }

    public GuiButtonIconGrid(int id, int xPosition, int yPosition, String label, ResourceLocation resourceLocation) {
        super(id, xPosition, yPosition, 128, 32, label);
        resloc = resourceLocation;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        RenderAssist.drawRect(xPosition - 0.375F, yPosition - 0.5F, xPosition + 128.5F, yPosition + 32F, 0x80000000);
        RenderAssist.drawUnfilledRect(xPosition - 0.375F, yPosition - 0.5F, xPosition + 128.5F, yPosition + 32F, 0xFFFFFFFF);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glScalef(0.5F, 0.5F, 1F);
        RenderAssist.bindTexture(resloc);
        RenderAssist.drawTexturedModalRect(xPosition * 2, yPosition * 2, 0, 0, 255, 65);
        if (mouseX > xPosition && mouseY > yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            boolean oddWidth = HUDRegistry.screenWidth%2==1;
            int mouseXTmp = mouseX+(oddWidth ? 2 : 0);
            float posX = ((mouseXTmp) - (mouseXTmp)%8)*2 - xPosition%8;
            float posY = ((mouseY) - (mouseY)%8)*2 + yPosition%8;
            int color = 0xFF1f95ff;
            if (Mouse.isButtonDown(0)) 
                color = 0xFF1059f7;
            RenderAssist.drawUnfilledRect(posX + (oddWidth ? 0 : 0.75F), posY, posX + (oddWidth ? 15 : 16), posY + 15.75F, color);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        if (selectedIconX > 0 && selectedIconY > 0)
            RenderAssist.drawUnfilledRect(xPosition+selectedIconX - 0.375F, yPosition+selectedIconY - 0.5F, xPosition+selectedIconX + 16, yPosition+selectedIconY + 16F, 0xFFFFFFFF);
    }
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (mouseX > xPosition && mouseY > yPosition && mouseX < xPosition + width && mouseY < yPosition + height) {
            boolean oddWidth = HUDRegistry.screenWidth%2==1;
            int mouseXTmp = mouseX+(oddWidth ? 2 : 0);
            selectedIconX = ((mouseXTmp) - (mouseXTmp)%8) - xPosition - 3;
            selectedIconY = ((mouseY) - (mouseY)%8) - yPosition;
        }
        AdvancedHUD.log.info("Selected Icon: "+selectedIconX+","+selectedIconY);
        return super.mousePressed(mc, mouseX, mouseY);
    }

}

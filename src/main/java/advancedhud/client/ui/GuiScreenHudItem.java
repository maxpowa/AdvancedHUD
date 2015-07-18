package advancedhud.client.ui;

import advancedhud.AdvancedHUD;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.client.huditems.HudItemCrosshairs;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenHudItem extends GuiScreen {

    private HudItem hudItem;
    private GuiScreen parentScreen;

    public GuiScreenHudItem(GuiScreen parentScreen, HudItem hudItem) {
        this.hudItem = hudItem;
        this.parentScreen = parentScreen;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(-1, HUDRegistry.screenWidth - 30, 10, 20, 20, "X"));
        if (hudItem.canRotate()) {
            buttonList.add(new GuiButton(100, HUDRegistry.screenWidth / 2 - 50, HUDRegistry.screenHeight / 2 - 10, 100, 20, "Rotate?"));
        } 
        if (hudItem instanceof HudItemCrosshairs) {
            HudItemCrosshairs cross = (HudItemCrosshairs) hudItem;
            buttonList.add(new GuiButtonIconGrid(3320, HUDRegistry.screenWidth / 2 - 128, 40, cross, "Crosshair Selector"));
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();

        if (!HUDRegistry.checkForResize()) {
            initGui();
        }

        this.drawCenteredString(mc.fontRenderer, hudItem.getButtonLabel(), HUDRegistry.screenWidth / 2, 10, 0xFFFFFF);
        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode) {
        if (keyCode == 1) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == -1) {
            mc.displayGuiScreen(parentScreen);
        } else if (par1GuiButton.id == 100) {
            hudItem.rotate();
        }
        AdvancedHUD.log.info("Clicked button " + par1GuiButton.id);
        super.actionPerformed(par1GuiButton);
    }

}

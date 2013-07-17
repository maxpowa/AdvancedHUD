package advancedhud.client.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;

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
        buttonList.add(new GuiButton(-1, HUDRegistry.screenWidth - 30, 10, 20,
                20, "X"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();

        if (!HUDRegistry.checkForResize()) {
            initGui();
        }

        this.drawCenteredString(mc.fontRenderer, hudItem.getButtonLabel(),
                HUDRegistry.screenWidth / 2, 10, 0xFFFFFF);
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
        }
        super.actionPerformed(par1GuiButton);
    }

}

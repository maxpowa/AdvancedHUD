package advancedhud.client.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import advancedhud.SaveController;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;

public class GuiAdvancedHUDConfiguration extends GuiScreen {

    private static boolean asMount = false;

    @Override
    public void initGui() {
        super.initGui();
        addButtons();
    }

    @SuppressWarnings("unchecked")
    private void addButtons() {
        buttonList.clear();
        buttonList.add(new GuiButton(-1, HUDRegistry.screenWidth - 30, 10, 20,
                20, "X"));
        for (HudItem huditem : HUDRegistry.getHudItemList()) {
            if (asMount && huditem.shouldDrawOnMount()) {
                buttonList.add(new GuiHudItemButton(huditem.getDefaultID(),
                        huditem.posX, huditem.posY, huditem.getWidth(), huditem
                                .getHeight(), huditem.getButtonLabel()));
            } else if (!asMount && huditem.shouldDrawAsPlayer()) {
                buttonList.add(new GuiHudItemButton(huditem.getDefaultID(),
                        huditem.posX, huditem.posY, huditem.getWidth(), huditem
                                .getHeight(), huditem.getButtonLabel()));
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();

        if (!HUDRegistry.checkForResize()) {
            initGui();
        }

        drawCenteredString(mc.fontRenderer,
                "LEFT CLICK to reposition, RIGHT CLICK to change settings",
                width / 2, 17, 16777215);
        drawCenteredString(mc.fontRenderer, "ESCAPE to cancel, R to reset all",
                width / 2, 27, 16777215);
        drawCenteredString(mc.fontRenderer, "M to change to"
                + (asMount ? " player " : " mount ") + "HUD", width / 2, 37,
                16777215);

        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 19) {
            HUDRegistry.resetAllDefaults();
            initGui();
        }
        if (par2 == Keyboard.KEY_M) {
            asMount = !asMount;
            initGui();
        }
        SaveController.saveConfig("config");
        super.keyTyped(par1, par2);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == -1) {
            mc.displayGuiScreen(null);
        }
        if (par1GuiButton instanceof GuiHudItemButton) {
            HudItem hudItem = HUDRegistry.getHudItemByID(par1GuiButton.id);
            if (hudItem != null && hudItem.isMoveable()) {
                Minecraft.getMinecraft().displayGuiScreen(
                        new GuiScreenReposition(this, hudItem));
            }
        }
        super.actionPerformed(par1GuiButton);
    }

    @Override
    public void mouseClicked(int i, int j, int mouseButton) {
        if (mouseButton == 1) {
            for (int l = 0; l < buttonList.size(); ++l) {
                GuiButton guibutton = (GuiButton) buttonList.get(l);

                if (guibutton.mousePressed(mc, i, j)) {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    HudItem hudItem = HUDRegistry.getHudItemByID(guibutton.id);
                    if (hudItem != null) {
                        Minecraft.getMinecraft().displayGuiScreen(
                                hudItem.getConfigScreen());
                    }
                }
            }
        }
        super.mouseClicked(i, j, mouseButton);
    }

}

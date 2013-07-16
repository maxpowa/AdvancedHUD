package advancedhud.client.ui;

import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.SaveController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiAdvancedHUDConfiguration extends GuiScreen {

    @Override
    public void initGui() {
        super.initGui();
        addButtons();
    }
    
    @SuppressWarnings("unchecked")
    private void addButtons() {
        this.buttonList.clear();
        for (HudItem huditem : HUDRegistry.getActiveHudItemList()) {
            this.buttonList.add(new GuiHudItemButton(huditem.getDefaultID(), huditem.posX, huditem.posY, huditem.getWidth(), huditem.getHeight(), huditem.getButtonLabel()));
        }
    }
    
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = sr.getScaledWidth();

        String s = "AdvancedHUD Config";
        mc.fontRenderer.drawStringWithShadow(s, width / 2 - mc.fontRenderer.getStringWidth(s) / 2, 16, 16777215);
        
        super.drawScreen(par1, par2, par3);
    }
    
    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 19) {
            for (HudItem huditem : HUDRegistry.getHudItemList()) {
                huditem.alignment = huditem.getDefaultAlignment();
                huditem.posX = huditem.getDefaultPosX();
                huditem.posY = huditem.getDefaultPosY();
            }
        }
        SaveController.saveConfig("config");
        super.keyTyped(par1, par2);
    }
    
    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton instanceof GuiHudItemButton) {
            HudItem hudItem = HUDRegistry.getHudItemByID(par1GuiButton.id);
            if (hudItem != null) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiScreenReposition(this, hudItem));
            }
        }

        SaveController.saveConfig("config");
        super.actionPerformed(par1GuiButton);
    }
    
}

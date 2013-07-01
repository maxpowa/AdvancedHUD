package advancedhud.client.ui;

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
        this.buttonList.add(new GuiHudItemButton(1, width / 2 - 91, height - 22, 182, 22, "HOTBAR"));
        this.buttonList.add(new GuiHudItemButton(2, width / 2 - 91, height - 29, 182, 4, "EXP"));
        this.buttonList.add(new GuiHudItemButton(3, width / 2 - 91, height - 39, 81, 9, "HEALTH"));
        this.buttonList.add(new GuiHudItemButton(4, width / 2 + 10, height - 39, 81, 9, "FOOD"));
        this.buttonList.add(new GuiHudItemButton(5, width / 2 - 91, height - 49, 81, 9, "ARMOR"));
        this.buttonList.add(new GuiHudItemButton(6, width / 2 + 10, height - 49, 81, 9, "AIR"));
        this.buttonList.add(new GuiHudItemButton(7, width / 2 - 37, height - 59, 81, 9, "RECORD OVERLAY"));
        this.buttonList.add(new GuiHudItemButton(8, width / 2 - 91, 12, 182, 5, "BOSS BAR"));
        this.buttonList.add(new GuiHudItemButton(9, width - 65, height / 2 - 16, 65, 19, "SCOREBOARD"));
        this.buttonList.add(new GuiHudItemButton(10, 2, height - 20, 81, 18, "CHAT"));
        this.buttonList.add(new GuiHudItemButton(11, 2, 2, 81, 18, "DEBUG"));

        this.buttonList.add(new GuiHudItemButton(20, width / 2 - 5, height / 2 - 5, 10, 10, "CROSSHAIRS"));
    }
    
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        super.drawScreen(par1, par2, par3);

        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = sr.getScaledWidth();
        
        String s = "AdvancedHUD Config";
        mc.fontRenderer.drawStringWithShadow(s, width / 2 - mc.fontRenderer.getStringWidth(s) / 2, 2, 16777215);
    }
    
    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 1337) {
            addButtons();
        }
        
        super.actionPerformed(par1GuiButton);
    }
    
}

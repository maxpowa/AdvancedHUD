package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import advancedhud.client.ui.GuiScreenHudItem;
import advancedhud.client.ui.GuiScreenReposition;

public class HudItemTooltips extends HudItem {

    private String itemName;
    private String itemRarityColorCode;
    private int stringColor;

    @Override
    public String getButtonLabel() {
        return "TOOLTIP";
    }

    @Override
    public String getName() {
        return "itemtooltip";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return (HUDRegistry.screenWidth - getWidth()) / 2;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight - 59;
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public int getDefaultID() {
        return 10;
    }

    @Override
    public void render(float paramFloat) {
        Minecraft mc = Minecraft.getMinecraft();

        mc.mcProfiler.startSection("toolHighlight");

        if (mc.thePlayer != null) {
            ItemStack currentItem = mc.thePlayer.inventory.getCurrentItem();
            String currentName = currentItem == null ? "" : currentItem
                    .getDisplayName();

            itemName = currentName;

            if (currentItem != null) {
                itemRarityColorCode = "\u00A7"
                        + Integer
                                .toHexString(currentItem.getRarity().rarityColor);
                stringColor = 16777215;
            }
        }

        if (mc.currentScreen instanceof GuiAdvancedHUDConfiguration
                || mc.currentScreen instanceof GuiScreenReposition) {
            itemName = "TOOLTIP";
        }

        if (!itemName.isEmpty()) {
            FontRenderer fontrenderer = mc.fontRenderer;
            int posX = 0;
            if (Alignment.isLeft(alignment)) {
                posX = this.posX;
            } else if (Alignment.isHorizontalCenter(alignment)) {
                posX = this.posX
                        + (getWidth() - fontrenderer.getStringWidth(itemName))
                        / 2;
            } else {
                posX = this.posX + getWidth()
                        - fontrenderer.getStringWidth(itemName);
            }

            fontrenderer.drawStringWithShadow(itemRarityColorCode + itemName,
                    posX, posY, stringColor);
        }

        mc.mcProfiler.endSection();

    }

    @Override
    public boolean shouldDrawOnMount() {
        return true;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen,
                this);
    }

}

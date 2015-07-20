package advancedhud.client.huditems;

import advancedhud.AdvancedHUD;
import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class HudItemCrosshairs extends HudItem {

    private static final ResourceLocation CROSSHAIR_ICONS = new ResourceLocation(AdvancedHUD.MOD_ID, "textures/gui/crosshairs.png");

    private int selectedIconX = -1;
    private int selectedIconY = -1;

    @Override
    public String getName() {
        return "crosshair";
    }

    @Override
    public String getButtonLabel() {
        return "CROSSHAIRS";
    }

    @Override
    public Alignment getDefaultAlignment() {
        return Alignment.CENTERCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 - 8;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight / 2 - 8;
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public int getDefaultID() {
        return 11;
    }

    @Override
    public void render(float paramFloat) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(775, 769, 1, 0);
        if (this.selectedIconX >= 0 && this.selectedIconY >= 0) {
            RenderAssist.bindTexture(HudItemCrosshairs.CROSSHAIR_ICONS);
            RenderAssist.drawTexturedModalRect(posX, posY, this.selectedIconX, this.selectedIconY, 16, 16);
        } else {
            RenderAssist.bindTexture(Gui.icons);
            RenderAssist.drawTexturedModalRect(posX, posY, 0, 0, 16, 16);
        }
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    }

    public int getSelectedIconX() {
        return this.selectedIconX;
    }

    public int getSelectedIconY() {
        return this.selectedIconY;
    }

    public void setSelectedIconX(int selectedIconX) {
        this.selectedIconX = selectedIconX;
    }

    public void setSelectedIconY(int selectedIconY) {
        this.selectedIconY = selectedIconY;
    }

    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
        if (nbt.hasKey("selectedIconX")) {
            selectedIconX = nbt.getInteger("selectedIconX");
        } else {
            selectedIconX = -1;
        }
        if (nbt.hasKey("selectedIconY")) {
            selectedIconY = nbt.getInteger("selectedIconY");
        } else {
            selectedIconX = -1;
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound nbt) {
        nbt.setInteger("selectedIconX", selectedIconX);
        nbt.setInteger("selectedIconY", selectedIconY);
        super.saveToNBT(nbt);
    }

    @Override
    public boolean isMoveable() {
        return false;
    }

    @Override
    public boolean shouldDrawOnMount() {
        return true;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen, this);
    }
    
    @Override
    public boolean canRotate() {
        return false;
    }
}

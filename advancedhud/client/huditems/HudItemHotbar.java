package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;


public class HudItemHotbar extends HudItem {
    
    public HudItemHotbar() {
        super();
    }
    
    @Override
    public String getName() {
        return "hotbar";
    }

    @Override
    public String getButtonLabel() {
        return "HOTBAR";
    }

    @Override
    public Alignment getDefaultAlignment() {
        if (rotated)
            return Alignment.CENTERRIGHT;
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        if (rotated)
            return HUDRegistry.screenWidth - getWidth();
        return (HUDRegistry.screenWidth-getWidth())/2;
    }

    @Override
    public int getDefaultPosY() {
        if (rotated)
            return (HUDRegistry.screenHeight-getHeight())/2;
        return HUDRegistry.screenHeight - getHeight();
    }

    @Override
    public int getWidth() {
        if (rotated)
            return 22;
        return 182;
    }

    @Override
    public int getHeight() {
        if (rotated)
            return 182;
        return 22;
    }

    @Override
    public void render(float partialTicks) {
        Minecraft mc = HUDRegistry.getMinecraftInstance();
        mc.mcProfiler.startSection("actionBar");

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderAssist.bindTexture(new ResourceLocation(
                "textures/gui/widgets.png"));
        InventoryPlayer inv = mc.thePlayer.inventory;
        if (rotated)
            GL11.glRotatef(-90, posX, posY, 0);
        RenderAssist.drawTexturedModalRect(posX, posY, 0, 0, 182, 22);
        RenderAssist.drawTexturedModalRect(posX - 1 + inv.currentItem * 20,
                posY - 1, 0, 22, 24, 22);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 9; ++i) {
            int x = 0;
            int z = 0;
            if (rotated) {
                z = posY - 90 + i * 20 + 2;
                x = posX - 6 - 3;
            } else {
                x = posX - 90 + i * 20 + 2;
                z = posY - 6 - 3;
            }
            RenderAssist.renderInventorySlot(i, x, z, partialTicks, mc);
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        mc.mcProfiler.endSection();
    }

    @Override
    public int getDefaultID() {
        return 1;
    }

    @Override
    public boolean shouldDrawOnMount() {
        return true;
    }

    @Override
    public boolean shouldDrawAsPlayer() {
        return true;
    }

    @Override
    public GuiScreen getConfigScreen() {
        return new GuiScreenHudItem(Minecraft.getMinecraft().currentScreen,
                this);
    }
    
    @Override
    public void loadFromNBT(NBTTagCompound nbt) {
        super.loadFromNBT(nbt);
    }
    
    @Override
    public void saveToNBT(NBTTagCompound nbt) {
        super.saveToNBT(nbt);
    }
    
    public void rotate() {
        super.rotate();
        this.posX = getDefaultPosX();
        this.posY = getDefaultPosY();
    }

}

package advancedhud.client.huditems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import advancedhud.api.Alignment;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import advancedhud.client.ui.GuiScreenHudItem;

public class HudItemHotbar extends HudItem {

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
        return Alignment.BOTTOMCENTER;
    }

    @Override
    public int getDefaultPosX() {
        return HUDRegistry.screenWidth / 2 - 91;
    }

    @Override
    public int getDefaultPosY() {
        return HUDRegistry.screenHeight - 22;
    }

    @Override
    public int getWidth() {
        return 182;
    }

    @Override
    public int getHeight() {
        return 22;
    }

    @Override
    public void render(float partialTicks) {
        Minecraft mc = HUDRegistry.getMinecraftInstance();
        mc.mcProfiler.startSection("actionBar");

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(new ResourceLocation(
                "textures/gui/widgets.png"));

        InventoryPlayer inv = mc.thePlayer.inventory;
        RenderAssist.drawTexturedModalRect(posX, posY, 0, 0, 182, 22);
        RenderAssist.drawTexturedModalRect(posX - 1 + inv.currentItem * 20,
                posY - 1, 0, 22, 24, 22);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 9; ++i) {
            int x = posX - 90 + i * 20 + 2;
            int z = posY - 6 - 3;
            renderInventorySlot(i, x, z, partialTicks, mc);
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        mc.mcProfiler.endSection();
    }

    /**
     * Renders the specified item of the inventory slot at the specified
     * location. Args: slot, x, y, partialTick
     */
    protected void renderInventorySlot(int par1, int par2, int par3,
            float par4, Minecraft mc) {
        RenderItem itemRenderer = new RenderItem();
        ItemStack itemstack = mc.thePlayer.inventory.mainInventory[par1];
        par2 += 91;
        par3 += 12;

        if (itemstack != null) {
            float f1 = itemstack.animationsToGo - par4;

            if (f1 > 0.0F) {
                GL11.glPushMatrix();
                float f2 = 1.0F + f1 / 5.0F;
                GL11.glTranslatef(par2 + 8, par3 + 12, 0.0F);
                GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
                GL11.glTranslatef(-(par2 + 8), -(par3 + 12), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer,
                    mc.func_110434_K(), itemstack, par2, par3);

            if (f1 > 0.0F) {
                GL11.glPopMatrix();
            }

            itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer,
                    mc.func_110434_K(), itemstack, par2, par3);
        }
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

}

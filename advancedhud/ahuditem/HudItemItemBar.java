package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.FadeHelper;
import advancedhud.button.ButtonMultiState;
import advancedhud.button.ButtonToggle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class HudItemItemBar extends HudItem
{
  protected RenderItem itemRenderer = new RenderItem();
  protected ButtonMultiState buttonOrientation = new ButtonMultiState(1, "Orientation", new String[] { "horizontal", "vertical" });
  protected ButtonToggle buttonNumDmg = new ButtonToggle(2, "Numerical damage");
  protected FadeHelper fadeHelper = new FadeHelper(8);
  protected int currentItem = -1;

  public void tick()
  {
    fadeHelper.tick(currentItem != AHud.getMinecraftInstance().thePlayer.inventory.currentItem);
    currentItem = AHud.getMinecraftInstance().thePlayer.inventory.currentItem;
  }

  public void render(float f)
  {
    int currentItem = AHud.getMinecraftInstance().thePlayer.inventory.currentItem;

    advancedhud.RenderHelper.bindTexture("/gui/gui.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, fadeHelper.getAlpha());

    if (buttonOrientation.value == 0) {
      advancedhud.RenderHelper.drawSprite(posX, posY, 0, 0, 182, 22, -90);
      advancedhud.RenderHelper.drawSprite(posX + currentItem * 20 - 1, posY - 1, 0, 22, 24, 22, -90);
    } else {
      GL11.glPushMatrix();
      if (posX + 12 > AHud.screenWidth / 2) {
        GL11.glDisable(2884);
        GL11.glScalef(-1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(-posX * 2 - getWidth(), 0.0F, 0.0F);
      }

      GL11.glTranslatef(posX, posY, 0.0F);
      GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.0F, -22.0F, 0.0F);

      advancedhud.RenderHelper.drawSprite(0, 0, 0, 0, 182, 22, -90);
      advancedhud.RenderHelper.drawSprite(currentItem * 20 - 1, 0, 0, 22, 24, 22, -90);

      GL11.glEnable(2884);
      GL11.glPopMatrix();
    }

    GL11.glDisable(3042);
    GL11.glEnable(32826);
    net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();

    if (buttonOrientation.value == 0) {
      for (int i = 0; i < 9; i++)
        renderInventorySlot(i, i * 20 + 3, 3, 0.0F, fadeHelper.getAlpha());
    }
    else {
      for (int i = 0; i < 9; i++) {
        renderInventorySlot(i, 3, i * 20 + 3, 0.0F, fadeHelper.getAlpha());
      }
    }

    net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
    GL11.glDisable(32826);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
  }

  protected void renderInventorySlot(int itemId, int posX, int posY, float f, float alpha) {
    Minecraft mc = AHud.getMinecraftInstance();
    if ((alpha < 0.5F) && (itemId != mc.thePlayer.inventory.currentItem)) {
      return;
    }

    ItemStack itemstack = mc.thePlayer.inventory.getStackInSlot(itemId);
    if (itemstack == null) {
      return;
    }

    posX += this.posX;
    posY += this.posY;

    float animationsToGo = itemstack.animationsToGo - f;
    if (animationsToGo > 0.0F) {
      GL11.glPushMatrix();
      float distort = 1.0F + animationsToGo / 5.0F;
      GL11.glTranslatef(posX + 8, posY + 12, 0.0F);
      GL11.glScalef(1.0F / distort, (distort + 1.0F) / 2.0F, 1.0F);
      GL11.glTranslatef(-(posX + 8), -(posY + 12), 0.0F);
    }

    itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, posX, posY);

    if (animationsToGo > 0.0F) {
      GL11.glPopMatrix();
    }

    if (!buttonNumDmg.value)
      itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, posX, posY);
    else
      renderItemOverlayIntoGUI(itemstack, posX, posY, mc);
  }

  public void renderItemOverlayIntoGUI(ItemStack stack, int posX, int posY, Minecraft mc)
  {
    int color = 16777215;
    int amount = stack.stackSize;

    if ((stack.itemID == Item.bow.itemID) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) == 0)) {
      itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, stack, posX, posY);
      amount = 0;
      for (ItemStack inventoryStack : mc.thePlayer.inventory.mainInventory) {
        if ((inventoryStack != null) && (inventoryStack.itemID == Item.arrow.itemID)) {
          amount += inventoryStack.stackSize;
        }
      }
      posY -= 8;
    } else if (stack.isItemDamaged()) {
      amount = stack.getMaxDamage() - stack.getItemDamageForDisplay() + 1;
      int j = (int)Math.round(255.0D - stack.getItemDamageForDisplay() * 255.0D / stack.getMaxDamage());
      color = 255 - j << 16 | j << 8;
    } else if (stack.stackSize <= 1)
    {
      return;
    }
    String s;
    if (amount <= 999) {
      s = amount + "";
    } else {
      s = amount / 1000 + "k";
      if (amount % 1000 != 0) {
        s = s + "+";
      }
    }

    GL11.glDisable(2896);
    GL11.glDisable(2929);
    mc.fontRenderer.drawStringWithShadow(s, posX - mc.fontRenderer.getStringWidth(s) + 17, posY + 9, color);
    GL11.glEnable(2896);
    GL11.glEnable(2929);
  }

  public List getButtonList() {
    List buttonList = new ArrayList();

    buttonList.addAll(super.getButtonList());
    buttonList.add(buttonOrientation);
    buttonList.add(buttonNumDmg);
    buttonList.addAll(fadeHelper.getButtonList());

    return buttonList;
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    buttonOrientation.setNBT(nbt.getCompoundTag("buttonOrientation"));
    buttonNumDmg.setNBT(nbt.getCompoundTag("buttonNumDmg"));
    fadeHelper.setNBT(nbt.getCompoundTag("fadeHelper"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("buttonOrientation", buttonOrientation.getNBT());
    nbt.setCompoundTag("buttonNumDmg", buttonNumDmg.getNBT());
    nbt.setCompoundTag("fadeHelper", fadeHelper.getNBT());
    super.saveToNBT(nbt);
  }

  public String getName()
  {
    return "Item Bar";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMCENTER;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth / 2 - 91;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 22;
  }

  public int getWidth()
  {
    return buttonOrientation.value == 0 ? 182 : 22;
  }

  public int getHeight()
  {
    return buttonOrientation.value == 0 ? 22 : 182;
  }

  public void drawIcon(int posX, int posY)
  {
    advancedhud.RenderHelper.bindTexture("/gui/ahud_icons.png");
    advancedhud.RenderHelper.drawSprite(posX, posY, 80, 0, 16, 16);
  }
}
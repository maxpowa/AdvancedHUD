package advancedhud;

import advancedhud.ahuditem.DefaultHudItems;
import advancedhud.ahuditem.HudItemChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatClickData;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;

public class GuiNewChatWrapper extends GuiNewChat
{
  protected final Minecraft mc;

  public GuiNewChatWrapper(Minecraft mc)
  {
    super(mc);
    this.mc = mc;
  }

  public ChatClickData func_73766_a(int mouseX, int mouseY)
  {
    ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
    int scaleFactor = scaledResolution.getScaleFactor();
    mouseX -= DefaultHudItems.chat.posX * scaleFactor;
    int offsetY = (DefaultHudItems.chat.posY - DefaultHudItems.chat.getDefaultPosY()) * scaleFactor;
    mouseY -= mc.fontRenderer.FONT_HEIGHT + 3 - offsetY;
    return super.func_73766_a(mouseX, mouseY);
  }
}
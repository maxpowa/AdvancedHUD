package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.GuiNewChatWrapper;
import advancedhud.RenderHelper;
import net.minecraft.client.gui.GuiNewChat;
import org.lwjgl.opengl.GL11;

public class HudItemChat extends HudItem
{
  protected GuiNewChatWrapper guiChat;

  public HudItemChat()
  {
    guiChat = new GuiNewChatWrapper(AHud.getMinecraftInstance());
  }

  public void render(float f) {
    GL11.glPushMatrix();
    GL11.glTranslatef(posX - 3, posY + 82, 0.0F);
    guiChat.drawChat(AHud.updateCounter);
    GL11.glPopMatrix();
  }

  public GuiNewChat getChatGUI() {
    return guiChat;
  }

  public String getName()
  {
    return "Chat";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMLEFT;
  }

  public int getDefaultPosX()
  {
    return 3;
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - 138;
  }

  public void drawIcon(int posX, int posY) {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 0, 16, 16, 16);
  }

  public int getWidth()
  {
    return 324;
  }

  public int getHeight()
  {
    return 90;
  }
}
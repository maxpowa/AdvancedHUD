package advancedhud.button;

import advancedhud.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonLabel extends Button
{
	public ButtonLabel(int id, String displayString)
	{
		super(id, displayString);
	}

	public ButtonLabel(int id, int width, int height, String displayString) {
		super(id, width, height, displayString);
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		drawRect(xPosition, yPosition, xPosition + width, yPosition + height, 1711276032);
		int i = 0;
		RenderHelper.bindTexture("/font/default.png");
		for (String line : displayString.split("\n")) {
			drawCenteredString(mc.fontRenderer, line, xPosition + width / 2, yPosition + i + 6, -1);
			i += 10;
		}
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return false;
	}
}
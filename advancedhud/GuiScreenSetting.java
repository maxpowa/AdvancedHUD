package advancedhud;

import advancedhud.ahuditem.HudItem;
import advancedhud.button.Button;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenSetting extends GuiScreen
{
	protected HudItem hudItem;
	protected int listHeight;
	protected List controlList;

	public GuiScreenSetting(HudItem aHudItem)
	{
		hudItem = aHudItem;
	}

	public void initGui() {
		if (controlList != null && controlList.size() > 0)
			controlList.clear();
		if (buttonList != null && buttonList.size() > 0)
			buttonList.clear();

		this.controlList = hudItem.getButtonList();
		this.buttonList.addAll(this.controlList);

		this.buttonList.add(0, new GuiButton(0, width - 110, 9, 100, 20, "Back"));

		reformatButtons(0);
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (buttonList.contains(guibutton) && guibutton instanceof Button) {
			hudItem.onButtonClick((Button)guibutton, this);
			return;
		}

		if (guibutton.id == 0)
			mc.displayGuiScreen(new GuiScreenSettings());
	}

	protected void keyTyped(char c, int i)
	{
		if (i == 1)
			mc.displayGuiScreen(new GuiScreenSettings());
	}

	public void drawScreen(int mouseX, int mouseY, float f)
	{
		drawDefaultBackground();

		reformatButtons(0);

		super.drawScreen(mouseX, mouseY, f);

		RenderHelper.drawHeader("Settings for " + hudItem.getName(), hudItem);

		((GuiButton)buttonList.get(0)).drawButton(mc, mouseX, mouseY);

		for (Object button_ : buttonList)
			if (button_ instanceof Button) {
				Button button = (Button) button_;
				RenderHelper.drawToolTip(button, mouseX, mouseY);
			}
	}

	public void reformatButtons(int dScroll)
	{
		listHeight = (45 + dScroll);
		for (int i = 0; i < buttonList.size(); i++) {
			Object button_ = buttonList.get(i);
			if (button_ instanceof Button) {
				Button button = (Button) button_;
				button.xPosition = (width / 2 - button.getWidth() / 2);
				button.yPosition = listHeight;
				listHeight += button.getHeight() + 5;
			}
		}
	}
}
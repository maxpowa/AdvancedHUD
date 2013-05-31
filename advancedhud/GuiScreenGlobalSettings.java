package advancedhud;

import advancedhud.ahuditem.HudItem;
import advancedhud.button.Button;
import advancedhud.button.ButtonDelete;
import advancedhud.button.ButtonToggle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;

public class GuiScreenGlobalSettings extends GuiScreen
{
	protected Mode currentMode;
	protected GuiScrollBar scrollBar;
	protected GuiButton backButton;
	protected GuiTextField textfield;
	protected int listHeight;
	protected boolean modeSwitchMouseClickLock;

	public GuiScreenGlobalSettings()
	{
		currentMode = Mode.DEFAULT;
	}

	public void initGui()
	{
		initGui(null);
	}

	protected void initGui(Mode mode) {
		modeSwitchMouseClickLock = true;
		if (mode != null) {
			currentMode = mode;
		}

		buttonList.clear();
		buttonList.add(this.backButton = new GuiButton(0, width - 110, 9, 100, 20, "Back"));
		buttonList.add(this.scrollBar = new GuiScrollBar(width - 8, 40, 8, height - 40));

		buttonList = new ArrayList();

		if (currentMode == Mode.DEFAULT) {
			buttonList.add(new Button(0, "Enable / disable HUD items [" + AHud.getActiveHudItemList().size() + "/" + AHud.getHudItemList().size() + "]"));
			buttonList.add(new Button(1, "Reposition items"));
			buttonList.add(new Button(2, "Preset manager"));
		}
		else
		{
			int i;
			if (currentMode == Mode.TOGGLEITEMS) {
				i = 0;
				for (Object item_ : AHud.getHudItemList()) {
					HudItem item = (HudItem) item_;
					buttonList.add(new ButtonToggle(i++, item.getName(), AHud.isActiveHudItem(item)));
				}
			}
			else if (currentMode == Mode.REPOSITIONITEMS) {
				for (int ii = 0; ii < AHud.getActiveHudItemList().size(); ii++) {
					HudItem hudItem = (HudItem)AHud.getActiveHudItemList().get(ii);
					if (hudItem.isMoveable())
						buttonList.add(new Button(ii, hudItem.getName()));
				}
			}
			else if (currentMode == Mode.PRESETMANAGER) {
				buttonList.add(new GuiButton(1, width - 110, 45, 100, 20, "Save"));
				File[] fileList = SaveController.getConfigs();
				buttonList.add(new Button(3, "load Default"));
				if ((fileList != null) && (fileList.length > 0)) {
					buttonList.add(new GuiButton(2, width - 110, 70, 100, 20, "Delete"));
					for (int ii = 0; ii < fileList.length; ii++)
						buttonList.add(new Button(0, "load " + getFileName(fileList[ii])));
				}
			}
			else if (currentMode == Mode.SAVE) {
				buttonList.add(new GuiButton(0, width - 110, 45, 100, 20, "Save new"));
				File[] fileList = SaveController.getConfigs();
				if (fileList != null) {
					for (int ii = 0; ii < fileList.length; ii++)
						buttonList.add(new Button(1, getFileName(fileList[ii]) + " (overwrite)"));
				}
			}
			else if (currentMode == Mode.SAVEAS) {
				textfield = new GuiTextField(mc.fontRenderer, width / 2 - 100, 60, 200, 20);
				textfield.setMaxStringLength(32);
				textfield.setFocused(true);
				buttonList.add(new GuiButton(0, width / 2 - 100, 85, "Save"));
			} else if (currentMode == Mode.DELETE) {
				File[] fileList = SaveController.getConfigs();
				if (fileList != null) {
					for (int ii = 0; ii < fileList.length; ii++) {
						buttonList.add(new ButtonDelete(ii, getFileName(fileList[ii])));
					}
				}
			}
		}

		reformatButtons(0);
		scrollBar.contentHeight = (listHeight - 40);
	}

	protected String getFileName(File file) {
		return file.getName().substring(0, file.getName().lastIndexOf("."));
	}

	public void actionPerformed(GuiButton guibutton) {
		if ((guibutton.id == -1) || (modeSwitchMouseClickLock)) {
			return;
		}

		if (guibutton == backButton) {
			if (currentMode == Mode.DEFAULT) {
				mc.displayGuiScreen(new GuiScreenSettings());
				return;
			}if ((currentMode == Mode.SAVE) || (currentMode == Mode.DELETE)) {
				initGui(Mode.PRESETMANAGER);
				return;
			}if (currentMode == Mode.SAVEAS) {
				if (SaveController.getConfigs().length > 0)
					initGui(Mode.SAVE);
				else {
					initGui(Mode.PRESETMANAGER);
				}
				return;
			}
			initGui(Mode.DEFAULT);
			return;
		}

		if (currentMode == Mode.DEFAULT) {
			switch (guibutton.id) {
			case 0:
				initGui(Mode.TOGGLEITEMS);
				break;
			case 1:
				initGui(Mode.REPOSITIONITEMS);
				break;
			case 2:
				initGui(Mode.PRESETMANAGER);
			}
		}
		else if (currentMode == Mode.TOGGLEITEMS) {
			ButtonToggle button = (ButtonToggle)buttonList.get(guibutton.id);
			if (button.value)
				AHud.enableHudItem((HudItem)AHud.getHudItemList().get(guibutton.id));
			else
				AHud.disableHudItem((HudItem)AHud.getHudItemList().get(guibutton.id));
		}
		else if (currentMode == Mode.REPOSITIONITEMS) {
			mc.displayGuiScreen(new GuiScreenReposition(this, (HudItem)AHud.getHudItemList().get(guibutton.id)));
		} else if (currentMode == Mode.PRESETMANAGER) {
			if (guibutton.id == 0) {
				SaveController.loadConfig(guibutton.displayString.split("load ")[1]);
				initGui(Mode.DEFAULT);
			} else if (guibutton.id == 1) {
				if (SaveController.getConfigs().length > 0)
					initGui(Mode.SAVE);
				else
					initGui(Mode.SAVEAS);
			}
			else if (guibutton.id == 2) {
				initGui(Mode.DELETE);
			} else if (guibutton.id == 3) {
				String DS = File.separator;
				SaveController.loadConfig("default", "config" + DS + "AdvancedHud" + DS + "active");
				initGui(Mode.DEFAULT);
			}
		} else if (currentMode == Mode.SAVE) {
			if (guibutton.id == 0) {
				initGui(Mode.SAVEAS);
			} else {
				String name = guibutton.displayString.substring(0, guibutton.displayString.length() - 12);
				SaveController.saveConfig(name);
				initGui(Mode.PRESETMANAGER);
			}
		} else if (currentMode == Mode.SAVEAS) {
			if (guibutton.id == 0) {
				String fileName = textfield.getText().trim();
				if (MathHelper.stringNullOrLengthZero(fileName)) {
					return;
				}
				SaveController.saveConfig(fileName);
				initGui(Mode.PRESETMANAGER);
			}
		} else if (currentMode == Mode.DELETE) {
			File file = SaveController.getConfigs()[guibutton.id];
			file.delete();
			initGui();
		}
	}

	protected void mouseMovedOrUp(int mouseX, int mouseY, int eventCode) {
		super.mouseMovedOrUp(mouseX, mouseY, eventCode);
		if (eventCode == 0)
			modeSwitchMouseClickLock = false;
	}

	public void drawScreen(int mouseX, int mouseY, float f)
	{
		drawDefaultBackground();

		reformatButtons(scrollBar.getScrollDistance());

		if (currentMode == Mode.SAVEAS) {
			textfield.drawTextBox();
		}

		super.drawScreen(mouseX, mouseY, f);
		RenderHelper.drawHeader("Global Settings", null);

		if (currentMode == Mode.DELETE)
			drawCenteredString(fontRenderer, "warning: once you press that button, there's no going back.", width / 2, 30, 16711680);
		else if (currentMode == Mode.SAVEAS) {
			textfield.drawTextBox();
		}

		((GuiButton)buttonList.get(0)).drawButton(mc, mouseX, mouseY);
	}

	protected void keyTyped(char c, int i) {
		if (i == 1) {
			if (currentMode == Mode.DEFAULT) {
				mc.displayGuiScreen(new GuiScreenSettings());
				return;
			}
			initGui(Mode.DEFAULT);
		}
		else if (currentMode == Mode.SAVEAS) {
			for (char a : ChatAllowedCharacters.allowedCharactersArray) {
				if (a == c) {
					return;
				}
			}
			textfield.textboxKeyTyped(c, i);
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

	public static enum Mode
	{
		DEFAULT, TOGGLEITEMS, REPOSITIONITEMS, PRESETMANAGER, SAVE, SAVEAS, DELETE;
	}
}
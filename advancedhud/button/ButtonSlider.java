package advancedhud.button;

import advancedhud.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class ButtonSlider extends Button
{
	public float value;
	public int lowRange;
	public int highRange;
	public String onLow;
	public String onHigh;
	public String unit;
	public boolean isDragging;

	public ButtonSlider(int id, int lowRange, int highRange, String text)
	{
		this(id, 200, 20, lowRange, highRange, text);
	}

	public ButtonSlider(int id, int width, int height, int lowRange, int highRange, String text) {
		this(id, width, height, lowRange, highRange, text, null, null);
	}

	public ButtonSlider(int id, int width, int height, int lowRange, int highRange, String text, String onLow, String onHigh) {
		this(id, width, height, lowRange, highRange, text, onLow, onHigh, null);
	}

	public ButtonSlider(int id, int width, int height, int lowRange, int highRange, String text, String onLow, String onHigh, String unit) {
		this(id, width, height, lowRange, highRange, text, onLow, onHigh, unit, 0.5F);
	}

	public ButtonSlider(int id, int lowRange, int highRange, String text, String onLow, String onHigh, float defaultValue) {
		this(id, 200, 20, lowRange, highRange, text, onLow, onHigh, null, defaultValue);
	}

	public ButtonSlider(int id, int lowRange, int highRange, String text, String onLow, String onHigh, String unit, float defaultValue) {
		this(id, 200, 20, lowRange, highRange, text, onLow, onHigh, unit, defaultValue);
	}

	public ButtonSlider(int id, int width, int height, int lowRange, int highRange, String text, String onLow, String onHigh, String unit, float defaultValue) {
		super(id, width, height, text);
		this.lowRange = lowRange;
		this.highRange = highRange;
		this.onLow = onLow;
		this.onHigh = onHigh;
		this.unit = unit;
		value = clamp(defaultValue);
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if (!drawButton) {
			return;
		}

		RenderHelper.bindTexture("/gui/gui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		drawTexturedModalRect(xPosition, yPosition, 0, 46, width / 2, height);
		drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46, width / 2, height);

		if (enabled) {
			drawTexturedModalRect(xPosition + (int)(value * (width - 8)), yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(xPosition + (int)(value * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
		}

		if (isDragging) {
			value = clamp((mouseX - (xPosition + 4)) / (width - 8));
		}

		boolean mouseOver = (mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height);

		int textColor = 14737632;
		if (!enabled)
			textColor = -6250336;
		else if (mouseOver) {
			textColor = 16777120;
		}

		RenderHelper.bindTexture("/font/default.png");
		drawCenteredString(mc.fontRenderer, getButtonText(), xPosition + width / 2, yPosition + (height - 8) / 2, textColor);
	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		isDragging = super.mousePressed(mc, mouseX, mouseY);
		return isDragging;
	}

	public void mouseReleased(int mouseX, int mouseY)
	{
		isDragging = false;
	}

	public int getIntValue() {
		return (int)(value * 100.0F * (highRange - lowRange) / 100.0F + lowRange);
	}

	public void setIntValue(int newValue) {
		newValue = (int)clamp(newValue, lowRange, highRange);
		value = (1.0F / (highRange - lowRange) * (newValue - lowRange));
	}

	protected String getButtonText() {
		String text = "";
		int val = getIntValue();

		if (displayString != null) {
			text = text + displayString + ": ";
		}

		if ((val == highRange) && (onHigh != null)) {
			text = text + onHigh;
		} else if ((val == lowRange) && (onLow != null)) {
			text = text + onLow;
		} else {
			text = text + val;
			if (unit != null) {
				text = text + unit;
			}
		}

		return text;
	}

	protected float clamp(float val) {
		return clamp(val, 0.0F, 1.0F);
	}

	protected float clamp(float val, float min, float max) {
		return Math.min(max, Math.max(min, val));
	}

	public void setNBT(NBTTagCompound nbt)
	{
		value = nbt.getFloat("value");
	}

	public NBTTagCompound getNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("value", value);
		return nbt;
	}
}

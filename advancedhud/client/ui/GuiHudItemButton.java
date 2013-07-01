package advancedhud.client.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class GuiHudItemButton extends GuiButton {

    public GuiHudItemButton(int id, int xPosition, int yPosition, int width,
            int height, String label) {
        super(id, xPosition, yPosition, width, height, label);
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        displayString = label;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (drawButton) {
            GL11.glPushMatrix();
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.renderEngine.bindTexture("/gui/gui.png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hoverState = mouseX >= xPosition && mouseY >= yPosition
                    && mouseX < xPosition + width
                    && mouseY < yPosition + height;
            if (displayString.equalsIgnoreCase("hotbar")) {
                hotbar(mc);
            } else if (displayString.equalsIgnoreCase("health")) {
                health(mc);
            } else if (displayString.equalsIgnoreCase("armor")) {
                armor(mc);
            } else if (displayString.equalsIgnoreCase("food")) {
                food(mc);
            } else if (displayString.equalsIgnoreCase("exp")) {
                exp(mc);
            } else if (displayString.equalsIgnoreCase("air")) {
                air(mc);
            } else if (displayString.equalsIgnoreCase("crosshairs")) {
                crosshairs(mc);
            } else if (displayString.equalsIgnoreCase("record overlay")) {
                recordoverlay(mc);
            } else if (displayString.equalsIgnoreCase("boss bar")) {
                bossHealth(mc);
            } else if (displayString.equalsIgnoreCase("scoreboard")) {
                scoreboard(mc);
            } else if (displayString.equalsIgnoreCase("chat")) {
                chat(mc);
            } else if (displayString.equalsIgnoreCase("debug")) {
                debug(mc);
            } else {

            }
            GL11.glPopMatrix();

            if (hoverState) {
                this.drawCenteredString(fontrenderer, displayString, xPosition
                        + width / 2, yPosition + (height - 8) / 2 + 1, 0xFFFFFF);
            }

        }
    }

    private void bossHealth(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        int left = xPosition;

        short short1 = 182;
        int k2 = yPosition;
        this.drawTexturedModalRect(left, k2, 0, 74, short1, 5);

        this.drawTexturedModalRect(left, k2, 0, 79, short1, 5);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/gui/icons.png");
    }

    private void recordoverlay(Minecraft mc) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.fontRenderer.drawString(displayString, xPosition - 1, yPosition +1,
                0xA3A3A3);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void crosshairs(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR,
                GL11.GL_ONE_MINUS_SRC_COLOR);
        drawTexturedModalRect(xPosition-2, yPosition-2, 0, 0, 16, 16);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void armor(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        int left = xPosition;
        int top = yPosition;

        int level = 20;
        for (int i = 1; level > 0 && i < 20; i += 2) {
            if (i < level) {
                drawTexturedModalRect(left, top, 34, 9, 9, 9);
            } else if (i == level) {
                drawTexturedModalRect(left, top, 25, 9, 9, 9);
            } else if (i > level) {
                drawTexturedModalRect(left, top, 16, 9, 9, 9);
            }
            left += 8;
        }
    }

    private void exp(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        int left = xPosition;

        short short1 = 182;
        int k2 = yPosition;
        this.drawTexturedModalRect(left, k2, 0, 64, short1, 5);

        this.drawTexturedModalRect(left, k2, 0, 69, short1, 5);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/gui/icons.png");
    }

    private void hotbar(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/gui.png");
        drawTexturedModalRect(xPosition, yPosition, 0, 0, width, height);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void health(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        int health = 20;
        int left = xPosition;
        int top = yPosition;

        for (int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            int iconX = 16;

            int x = left + i * 8;
            int y = top;

            byte iconY = 0;

            drawTexturedModalRect(x, y, 16, 9 * iconY, 9, 9);

            if (idx < health) {
                drawTexturedModalRect(x, y, iconX + 36, 9 * iconY, 9, 9);
            } else if (idx == health) {
                drawTexturedModalRect(x, y, iconX + 45, 9 * iconY, 9, 9);
            }
        }
    }

    private void food(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        int food = 20;
        int left = xPosition;
        int top = yPosition;

        for (int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            int x = left - i * 8 - 9 + 81;
            int y = top;
            int icon = 16;

            this.drawTexturedModalRect(x, y, 16, 27, 9, 9);

            if (idx < food) {
                drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
            }

            if (idx == food) {
                drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
            }
        }
    }

    private void air(Minecraft mc) {
        mc.renderEngine.bindTexture("/gui/icons.png");
        int left = xPosition;
        int top = yPosition;

        int air = 300;
        int full = MathHelper.ceiling_double_int((air - 2) * 10.0D / 300.0D);
        int partial = MathHelper.ceiling_double_int(air * 10.0D / 300.0D)
                - full;

        for (int i = 0; i < full + partial; ++i) {
            drawTexturedModalRect(left - i * 8 - 9 + 81, top, i < full ? 16
                    : 25, 18, 9, 9);
        }
    }

    private void scoreboard(Minecraft mc) {
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height,
                1610612736);
        this.drawCenteredString(mc.fontRenderer, displayString, xPosition
                + width / 2, yPosition + (height - 8) / 2 +1, 0xA3A3A3);
    }

    private void chat(Minecraft mc) {
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height,
                1610612736);
        this.drawCenteredString(mc.fontRenderer, displayString, xPosition
                + width / 2, yPosition + (height - 8) / 2 +1, 0xA3A3A3);
    }

    private void debug(Minecraft mc) {
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height,
                1610612736);
        this.drawCenteredString(mc.fontRenderer, displayString, xPosition
                + width / 2, yPosition + (height - 8) / 2 +1, 0xA3A3A3);
    }

}

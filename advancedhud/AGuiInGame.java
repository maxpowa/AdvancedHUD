package advancedhud;

import advancedhud.ahuditem.DefaultHudItems;
import advancedhud.ahuditem.HudItem;
import advancedhud.ahuditem.HudItemChat;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

public class AGuiInGame extends GuiIngame
{
	protected Minecraft mc;
	float a;

	public AGuiInGame(Minecraft mc)
	{
		super(mc);

		prevVignetteBrightness = 1.0F;

		this.mc = mc;
	}

	public void renderGameOverlay(float f, boolean isActive, int mouseX, int mouseY)
	{
		Profiler profiler = mc.mcProfiler;
		profiler.startSection("Advanced Hud");

		AHud.checkForResize();

		mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(3042);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(770, 771);

		if (!mc.thePlayer.isPotionActive(Potion.confusion)) {
			float time = mc.thePlayer.prevTimeInPortal + (mc.thePlayer.timeInPortal - mc.thePlayer.prevTimeInPortal) * f;
			if (time > 0.0F) {
				renderPortalOverlay(time, AHud.screenWidth, AHud.screenHeight);
			}
		}

		renderPumpkinBlur(AHud.screenWidth, AHud.screenHeight);
		renderVignette(mc.thePlayer.getBrightness(f), AHud.screenWidth, AHud.screenHeight);
		renderSleepOverlay();

		boolean isSurvival = mc.playerController.shouldDrawHUD();
		for (Object item_ : AHud.getActiveHudItemList()) {
			HudItem item = (HudItem) item_;
			if ((isSurvival) || (item.isRenderedInCreative())) {
				profiler.startSection(item.getName());
				item.render(f);
				profiler.endSection();
			}
		}

		renderPlayerList();

		profiler.endSection();
	}

	public void updateTick()
	{
		Profiler profiler = mc.mcProfiler;
		profiler.startSection("Advanced Hud");

		AHud.updateCounter += 1;

		if (mc.currentScreen == null) {
			while (AHud.keyBinding.isPressed()) mc.displayGuiScreen(new GuiScreenSettings());
		}

		boolean isSurvival = mc.playerController.shouldDrawHUD();
		if (mc.theWorld != null) {
			for (Object item_ : AHud.getActiveHudItemList()) {
				HudItem item = (HudItem) item_;
				if ((isSurvival) || (item.isRenderedInCreative())) {
					profiler.startSection(item.getName());
					item.tick();
					profiler.endSection();
				}
			}
		}

		super.updateTick();

		profiler.endSection();
	}

	protected void renderPumpkinBlur(int par1, int par2) {
		ItemStack itemstack = mc.thePlayer.inventory.armorItemInSlot(3);

		if ((mc.gameSettings.thirdPersonView == 0) && (itemstack != null) && (itemstack.itemID == Block.pumpkin.blockID)) {
			GL11.glDisable(2929);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3008);
			GL11.glBindTexture(3553, mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, par2, -90.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(par1, par2, -90.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(par1, 0.0D, -90.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
			tessellator.draw();
			GL11.glDepthMask(true);
			GL11.glEnable(2929);
			GL11.glEnable(3008);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	protected void renderVignette(float par1, int par2, int par3)
	{
		if (!Minecraft.isFancyGraphicsEnabled()) {
			return;
		}

		par1 = 1.0F - par1;

		if (par1 < 0.0F)
		{
			par1 = 0.0F;
		}

		if (par1 > 1.0F)
		{
			par1 = 1.0F;
		}

		prevVignetteBrightness = ((float)(prevVignetteBrightness + (par1 - prevVignetteBrightness) * 0.01D));
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(0, 769);
		GL11.glColor4f(prevVignetteBrightness, prevVignetteBrightness, prevVignetteBrightness, 1.0F);
		GL11.glBindTexture(3553, mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, par3, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(par2, par3, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(par2, 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(770, 771);
	}

	protected void renderPortalOverlay(float par1, int par2, int par3)
	{
		if (par1 < 1.0F)
		{
			par1 *= par1;
			par1 *= par1;
			par1 = par1 * 0.8F + 0.2F;
		}

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
		this.mc.renderEngine.bindTexture("/terrain.png");
		Icon icon = Block.portal.getBlockTextureFromSide(1);
		float f1 = icon.getMinU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxU();
		float f4 = icon.getMaxV();
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)f1, (double)f4);
		tessellator.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)f3, (double)f4);
		tessellator.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)f3, (double)f2);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)f1, (double)f2);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void renderSleepOverlay() {
		if (mc.thePlayer.getSleepTimer() > 0) {
			GL11.glDisable(2929);
			GL11.glDisable(3008);
			int k = mc.thePlayer.getSleepTimer();
			float f1 = k / 100.0F;

			if (f1 > 1.0F) {
				f1 = 1.0F - (k - 100) / 10.0F;
			}

			int j1 = (int)(220.0F * f1) << 24 | 0x101020;
			drawRect(0, 0, AHud.screenWidth, AHud.screenHeight, j1);
			GL11.glEnable(3008);
			GL11.glEnable(2929);
		}
	}

	protected void renderPlayerList() {
		if ((mc.gameSettings.keyBindPlayerList.pressed) && ((!mc.isIntegratedServerRunning()) || (mc.thePlayer.sendQueue.playerInfoList.size() > 1))) {
			NetClientHandler netClientHandler = mc.thePlayer.sendQueue;
			List playerList = netClientHandler.playerInfoList;
			int maxPlayers = netClientHandler.currentServerMaxPlayers;

			int j3 = maxPlayers;
			int i4 = 1;

			for (; j3 > 20; j3 = (maxPlayers + i4 - 1) / i4) {
				i4++;
			}

			int k4 = 300 / i4;

			if (k4 > 150) {
				k4 = 150;
			}

			int i5 = (AHud.screenWidth - i4 * k4) / 2;
			byte byte0 = 10;
			drawRect(i5 - 1, byte0 - 1, i5 + k4 * i4, byte0 + 9 * j3, -2147483648);

			for (int k6 = 0; k6 < maxPlayers; k6++) {
				int j7 = i5 + k6 % i4 * k4;
				int i8 = byte0 + k6 / i4 * 9;
				drawRect(j7, i8, j7 + k4 - 1, i8 + 8, 553648127);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(3008);

				if (k6 < playerList.size())
				{
					GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo)playerList.get(k6);
					mc.fontRenderer.drawStringWithShadow(guiplayerinfo.name, j7, i8, 16777215);
					mc.renderEngine.bindTexture("/gui/icons.png");
					int l9 = 0;
					byte byte1 = 0;

					if (guiplayerinfo.responseTime < 0)
						byte1 = 5;
					else if (guiplayerinfo.responseTime < 150)
						byte1 = 0;
					else if (guiplayerinfo.responseTime < 300)
						byte1 = 1;
					else if (guiplayerinfo.responseTime < 600)
						byte1 = 2;
					else if (guiplayerinfo.responseTime < 1000)
						byte1 = 3;
					else {
						byte1 = 4;
					}

					zLevel += 100.0F;
					drawTexturedModalRect(j7 + k4 - 12, i8, 0 + l9 * 10, 176 + byte1 * 8, 10, 8);
					zLevel -= 100.0F;
				}
			}
		}
	}

	public GuiNewChat getChatGUI() { return DefaultHudItems.chat.getChatGUI(); }


	public int getUpdateCounter()
	{
		return AHud.updateCounter;
	}
}
package advancedhud.client;

import advancedhud.AdvancedHUD;
import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import advancedhud.api.RenderAssist;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiAdvancedHUD extends GuiIngameForge {

    public static float partialTicks;
    private long lastTick = System.currentTimeMillis();

    private ScaledResolution res = null;
    public String recordPlaying;
    public boolean recordIsPlaying;
    public int recordPlayingUpFor = 0;

    public GuiAdvancedHUD(Minecraft mc) {
        super(mc);
    }

    @Override
    public void renderGameOverlay(float partialTicks, boolean hasScreen, int mouseX, int mouseY) {
        mc.mcProfiler.startSection("Advanced Hud");
        
        GuiAdvancedHUD.partialTicks = partialTicks;

        HUDRegistry.checkForResize();

        mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);

        res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        float delta = (System.currentTimeMillis() - lastTick) / 1000F;
        lastTick = System.currentTimeMillis();

        mc.mcProfiler.startSection("modules");
        for (HudItem huditem : HUDRegistry.getHudItemList()) {
            mc.mcProfiler.startSection(huditem.getName());

            if (huditem.needsTween()) {
                mc.mcProfiler.startSection("tween");
                huditem.update(delta);
                mc.mcProfiler.endSection();
            }

            if (mc.playerController.isInCreativeMode() && !huditem.isRenderedInCreative()) {
                mc.mcProfiler.endSection();
                continue;
            }

            GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
//            GL11.glDisable(GL11.GL_LIGHTING);
//            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            if (mc.thePlayer.ridingEntity instanceof EntityLivingBase) {
                if (huditem.shouldDrawOnMount()) {
                    huditem.fixBounds();
                    huditem.render(partialTicks);
                }
            } else {
                if (huditem.shouldDrawAsPlayer()) {
                    huditem.fixBounds();
                    huditem.render(partialTicks);
                }
            }
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            mc.mcProfiler.endSection();
        }
        mc.mcProfiler.endSection();
        
        renderHUDText(HUDRegistry.screenWidth, HUDRegistry.screenHeight);

        int width = HUDRegistry.screenWidth;
        int height = HUDRegistry.screenHeight;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        this.renderChat(width, height);

        this.renderPlayerList(width, height);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        mc.mcProfiler.endSection();
    }

    @Override
    protected void renderHUDText(int width, int height) {
        mc.mcProfiler.startSection("forgeHudText");
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        ArrayList<String> left = new ArrayList<String>();
        ArrayList<String> right = new ArrayList<String>();

        if (mc.isDemo()) {
            long time = mc.theWorld.getTotalWorldTime();
            if (time >= 120500L) {
                right.add(I18n.format("demo.demoExpired"));
            } else {
                right.add(I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int) (120500L - time))));
            }
        }

        if (mc.gameSettings.showDebugInfo) {
            GL11.glPushMatrix();
            left.add("Minecraft " + AdvancedHUD.MC_VERSION + " (" + mc.debug + ")");
            left.add(mc.debugInfoRenders());
            left.add(mc.getEntityDebug());
            left.add(mc.debugInfoEntities());
            left.add(mc.getWorldProviderName());
            left.add(null); // Spacer

            long max = Runtime.getRuntime().maxMemory();
            long total = Runtime.getRuntime().totalMemory();
            long free = Runtime.getRuntime().freeMemory();
            long used = total - free;

            right.add("Used memory: " + used * 100L / max + "% (" + used / 1024L / 1024L + "MB) of " + max / 1024L / 1024L + "MB");
            right.add("Allocated memory: " + total * 100L / max + "% (" + total / 1024L / 1024L + "MB)");

            int x = MathHelper.floor_double(mc.thePlayer.posX);
            int y = MathHelper.floor_double(mc.thePlayer.posY);
            int z = MathHelper.floor_double(mc.thePlayer.posZ);
            float yaw = mc.thePlayer.rotationYaw;
            int heading = MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

            left.add(String.format("x: %.5f (%d) // c: %d (%d)", mc.thePlayer.posX, x, x >> 4, x & 15));
            left.add(String.format("y: %.3f (feet pos, %.3f eyes pos)", mc.thePlayer.boundingBox.minY, mc.thePlayer.posY));
            left.add(String.format("z: %.5f (%d) // c: %d (%d)", mc.thePlayer.posZ, z, z >> 4, z & 15));
            left.add(String.format("f: %d (%s) / %f", heading, Direction.directions[heading], MathHelper.wrapAngleTo180_float(yaw)));

            if (mc.theWorld != null && mc.theWorld.blockExists(x, y, z)) {
                Chunk chunk = mc.theWorld.getChunkFromBlockCoords(x, z);
                left.add(String.format("lc: %d b: %s bl: %d sl: %d rl: %d", chunk.getTopFilledSegment() + 15, chunk.getBiomeGenForWorldCoords(x & 15, z & 15, mc.theWorld.getWorldChunkManager()).biomeName, chunk.getSavedLightValue(EnumSkyBlock.Block, x & 15, y, z & 15), chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15), chunk.getBlockLightValue(x & 15, y, z & 15, 0)));
            } else {
                left.add(null);
            }

            left.add(String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", mc.thePlayer.capabilities.getWalkSpeed(), mc.thePlayer.capabilities.getFlySpeed(), mc.thePlayer.onGround, mc.theWorld.getHeightValue(x, z)));
            if (mc.entityRenderer != null && mc.entityRenderer.isShaderActive()) {
                left.add(String.format("shader: %s", mc.entityRenderer.getShaderGroup().getShaderGroupName()));
            }

            right.add(null);
            for (String brand : FMLCommonHandler.instance().getBrandings(false)) {
                right.add(brand);
            }
            GL11.glPopMatrix();
        }

        for (int x = 0; x < left.size(); x++) {
            String msg = left.get(x);
            if (msg == null) {
                continue;
            }
            mc.fontRenderer.drawStringWithShadow(msg, 2, 2 + x * 10, 0xFFFFFF);
        }

        for (int x = 0; x < right.size(); x++) {
            String msg = right.get(x);
            if (msg == null) {
                continue;
            }
            int w = mc.fontRenderer.getStringWidth(msg);
            mc.fontRenderer.drawStringWithShadow(msg, width - w - 10, 2 + x * 10, 0xFFFFFF);
        }

        mc.mcProfiler.endSection();
    }

    @Override
    protected void renderPlayerList(int width, int height) {
        mc.mcProfiler.startSection("playerList");
        ScoreObjective scoreobjective = mc.theWorld.getScoreboard().func_96539_a(0);
        NetHandlerPlayClient handler = mc.thePlayer.sendQueue;

        if (mc.gameSettings.keyBindPlayerList.isPressed() && (!mc.isIntegratedServerRunning() || handler.playerInfoList.size() > 1 || scoreobjective != null)) {
            List<?> players = handler.playerInfoList;
            int maxPlayers = handler.currentServerMaxPlayers;
            int rows = maxPlayers;
            int columns;

            for (columns = 1; rows > 20; rows = (maxPlayers + columns - 1) / columns) {
                columns++;
            }

            int columnWidth = 300 / columns;

            if (columnWidth > 150) {
                columnWidth = 150;
            }

            int left = (width - columns * columnWidth) / 2;
            byte border = 10;
            drawRect(left - 1, border - 1, left + columnWidth * columns, border + 9 * rows, Integer.MIN_VALUE);

            for (int i = 0; i < maxPlayers; i++) {
                int xPos = left + i % columns * columnWidth;
                int yPos = border + i / columns * 9;
                drawRect(xPos, yPos, xPos + columnWidth - 1, yPos + 8, 553648127);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                if (i < players.size()) {
                    GuiPlayerInfo player = (GuiPlayerInfo) players.get(i);
                    ScorePlayerTeam team = mc.theWorld.getScoreboard().getPlayersTeam(player.name);
                    String displayName = ScorePlayerTeam.formatPlayerName(team, player.name);
                    mc.fontRenderer.drawStringWithShadow(displayName, xPos, yPos, 16777215);

                    if (scoreobjective != null) {
                        int endX = xPos + mc.fontRenderer.getStringWidth(displayName) + 5;
                        int maxX = xPos + columnWidth - 12 - 5;

                        if (maxX - endX > 5) {
                            Score score = scoreobjective.getScoreboard().func_96529_a(player.name, scoreobjective);
                            String scoreDisplay = EnumChatFormatting.YELLOW + "" + score.getScorePoints();
                            mc.fontRenderer.drawStringWithShadow(scoreDisplay, maxX - mc.fontRenderer.getStringWidth(scoreDisplay), yPos, 16777215);
                        }
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    RenderAssist.bindTexture(Gui.icons);
                    int pingIndex = 4;
                    int ping = player.responseTime;
                    if (ping < 0) {
                        pingIndex = 5;
                    } else if (ping < 150) {
                        pingIndex = 0;
                    } else if (ping < 300) {
                        pingIndex = 1;
                    } else if (ping < 600) {
                        pingIndex = 2;
                    } else if (ping < 1000) {
                        pingIndex = 3;
                    }

                    zLevel += 100.0F;
                    drawTexturedModalRect(xPos + columnWidth - 12, yPos, 0, 176 + pingIndex * 8, 10, 8);
                    zLevel -= 100.0F;
                }
            }
        }
        mc.mcProfiler.endSection();
    }

    @Override
    protected void renderChat(int width, int height) {
        mc.mcProfiler.startSection("chat");

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, mc.displayHeight / 2 - 40F, 0.0F);
        persistantChatGUI.drawChat(updateCounter);
        GL11.glPopMatrix();

        mc.mcProfiler.endSection();
    }

    @Override
    public ScaledResolution getResolution() {
        return res;
    }

    @Override
    public void updateTick() {
        mc.mcProfiler.startSection("Advanced HUD - UpdateTick");

        if (mc.theWorld != null) {
            for (HudItem huditem : HUDRegistry.getHudItemList()) {
                mc.mcProfiler.startSection(huditem.getName());
                if (mc.playerController.isInCreativeMode() && !huditem.isRenderedInCreative()) {
                    mc.mcProfiler.endSection();
                    continue;
                }
                if (huditem.needsTick()) {
                    huditem.tick();
                }
                mc.mcProfiler.endSection();
            }
        }

        updateCounter++;
        HUDRegistry.updateCounter = updateCounter;

        mc.mcProfiler.endSection();
    }

    // setRecordPlaying
    @Override
    public void func_110326_a(String recordName, boolean isPlaying)
    {
        this.recordPlaying = recordName;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = isPlaying;
    }
}

package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import advancedhud.button.Button;
import advancedhud.button.ButtonToggle;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class HudItemDebug extends HudItem
{
  protected int cursorLeft;
  protected int cursorRight;
  protected ButtonToggle buttonFPS = new ButtonToggle(0, "show updates", true);
  protected ButtonToggle buttonChunk = new ButtonToggle(1, "show chunk info", true);
  protected ButtonToggle buttonEntity = new ButtonToggle(2, "show entity info", true);
  protected ButtonToggle buttonParticle = new ButtonToggle(3, "show particle info", true);
  protected ButtonToggle buttonWorld = new ButtonToggle(4, "show world info", true);
  protected ButtonToggle buttonPosition = new ButtonToggle(5, "show position info", true);
  protected ButtonToggle buttonLight = new ButtonToggle(6, "show light info", true);
  protected ButtonToggle buttonSpeed = new ButtonToggle(7, "show speed info", true);
  protected ButtonToggle buttonMemory = new ButtonToggle(8, "show memory info", true);
  protected ButtonToggle buttonColor = new ButtonToggle(9, "use colors", false);

  public void render(float f) {
    Minecraft mc = AHud.getMinecraftInstance();
    if (mc.gameSettings.showDebugInfo) {
      cursorLeft = (this.cursorRight = 0);

      String s = "Minecraft " + AHud.getMinecraftVersion();

      if (buttonFPS.value) {
        s = s + " (" + mc.debug + ")";
      }
      drawString(s, true);

      if (buttonChunk.value) {
        drawString(mc.debugInfoRenders(), true);
      }

      if (buttonEntity.value) {
        drawString(mc.getEntityDebug(), true);
      }

      if (buttonParticle.value) {
        drawString(mc.debugInfoEntities(), true);
      }

      if (buttonWorld.value) {
        drawString(mc.getWorldProviderName(), true);
      }

      cursorLeft += 1;

      if (buttonPosition.value) {
        int posX = MathHelper.floor_double(mc.thePlayer.posX);
        int posZ = MathHelper.floor_double(mc.thePlayer.posZ);
        drawString(String.format("x: \u00A76%.5f\u00A7r (\u00A76%d\u00A7r) // c: \u00A76%d\u00A7r (\u00A76%d\u00A7r)", new Object[] { Double.valueOf(mc.thePlayer.posX), Integer.valueOf(posX), Integer.valueOf(posX >> 4), Integer.valueOf(posX & 0xF) }), false);
        drawString(String.format("y: \u00A76%.3f\u00A7r (feet pos, \u00A76%.3f\u00A7r eyes pos)", new Object[] { Double.valueOf(mc.thePlayer.boundingBox.minY), Double.valueOf(mc.thePlayer.posY) }), false);
        drawString(String.format("z: \u00A76%.5f\u00A7r (\u00A76%d\u00A7r) // c: \u00A76%d\u00A7r (\u00A76%d\u00A7r)", new Object[] { Double.valueOf(mc.thePlayer.posZ), Integer.valueOf(posZ), Integer.valueOf(posZ >> 4), Integer.valueOf(posZ & 0xF) }), false);

        int facing = MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
        drawString("f: \u00A76" + facing + "\u00A7r (" + net.minecraft.util.Direction.directions[facing] + ") / \u00A76" + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), false);
      }

      int blockX = MathHelper.floor_double(mc.thePlayer.posX);
      int blockY = MathHelper.floor_double(mc.thePlayer.posY);
      int blockZ = MathHelper.floor_double(mc.thePlayer.posZ);
      int chunkX = blockX & 0xF;
      int chunkZ = blockZ & 0xF;

      cursorLeft += 1;

      if ((mc.theWorld != null) && (mc.theWorld.blockExists(blockX, blockY, blockZ))) {
        Chunk chunk = mc.theWorld.getChunkFromBlockCoords(blockX, blockZ);

        if (buttonWorld.value) {
          String lc = "chunkHeight: \u00A76" + (chunk.getTopFilledSegment() + 15) + "\u00A7r";
          String b = "biome: \u00A76" + chunk.getBiomeGenForWorldCoords(chunkX, chunkZ & 0xF, mc.theWorld.getWorldChunkManager()).biomeName;
          drawString(lc + " " + b, false);
        }

        if (buttonLight.value) {
          String bl = "block light: \u00A76" + chunk.getSavedLightValue(EnumSkyBlock.Block, chunkX, blockY, chunkZ) + "\u00A7r";
          String sl = "sky light: \u00A76" + chunk.getSavedLightValue(EnumSkyBlock.Sky, chunkX, blockY, chunkZ) + "\u00A7r";
          String rl = "raw light: \u00A76" + chunk.getBlockLightValue(chunkX, blockY, chunkZ, 0) + "\u00A7r";
          drawString(bl + " " + sl + " " + rl, false);
        }

        if (buttonSpeed.value) {
          String ws = "walking speed: " + String.format("\u00A76%.3f\u00A7r ", new Object[] { Float.valueOf(mc.thePlayer.capabilities.getWalkSpeed()) });
          String fs = "flying speed: " + String.format("\u00A76%.3f\u00A7r", new Object[] { Float.valueOf(mc.thePlayer.capabilities.getFlySpeed()) });
          String g = "ground: " + String.format("\u00A76%b\u00A7r", new Object[] { Boolean.valueOf(mc.thePlayer.onGround) });
          drawString(ws + fs, false);
          drawString(g, false);
        }
      }

      if (buttonMemory.value)
      {
        long memMax = Runtime.getRuntime().maxMemory();
        long memTot = Runtime.getRuntime().totalMemory();
        long memDif = memTot - Runtime.getRuntime().freeMemory();

        String memoryInfo = "Used memory: \u00A76" + memDif * 100L / memMax + "%\u00A7r (\u00A76" + memDif / 1048576L + "\u00A7rMB) of \u00A76" + memMax / 1048576L + "\u00A7rMB";
        drawStringRight(memoryInfo, false);

        memoryInfo = "Allocated memory: \u00A76" + memTot * 100L / memMax + "\u00A7r% (\u00A76" + memTot / 1048576L + "\u00A7rMB)";
        drawStringRight(memoryInfo, false);
      }
    }
  }

  protected void drawString(String msg, boolean flag) {
    if (msg == null) {
      return;
    }

    if (!buttonColor.value) {
      msg = StringUtils.stripControlCodes(msg);
    }

    if (flag)
      AHud.getMinecraftInstance().fontRenderer.drawStringWithShadow(msg, 2, cursorLeft * 10 + 2, 16777215);
    else {
      AHud.getMinecraftInstance().fontRenderer.drawStringWithShadow(msg, 2, cursorLeft * 10 + 2, 14737632);
    }

    cursorLeft += 1;
  }

  protected void drawStringRight(String msg, boolean hasShadow) {
    if (msg == null) {
      return;
    }

    if (!buttonColor.value) {
      msg = StringUtils.stripControlCodes(msg);
    }

    int posX = AHud.screenWidth - AHud.getMinecraftInstance().fontRenderer.getStringWidth(msg) - 2;

    if (hasShadow)
      AHud.getMinecraftInstance().fontRenderer.drawStringWithShadow(msg, posX, cursorRight * 10 + 2, 16777215);
    else {
      AHud.getMinecraftInstance().fontRenderer.drawStringWithShadow(msg, posX, cursorRight * 10 + 2, 14737632);
    }

    cursorRight += 1;
  }

  public List getButtonList()
  {
    return Arrays.asList(new Button[] { buttonFPS, buttonChunk, buttonEntity, buttonParticle, buttonWorld, buttonPosition, buttonLight, buttonSpeed, buttonMemory, buttonColor });
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    buttonFPS.setNBT(nbt.getCompoundTag("buttonFPS"));
    buttonChunk.setNBT(nbt.getCompoundTag("buttonChunk"));
    buttonEntity.setNBT(nbt.getCompoundTag("buttonEntity"));
    buttonParticle.setNBT(nbt.getCompoundTag("buttonParticle"));
    buttonWorld.setNBT(nbt.getCompoundTag("buttonWorld"));
    buttonPosition.setNBT(nbt.getCompoundTag("buttonPosition"));
    buttonLight.setNBT(nbt.getCompoundTag("buttonLight"));
    buttonSpeed.setNBT(nbt.getCompoundTag("buttonSpeed"));
    buttonMemory.setNBT(nbt.getCompoundTag("buttonMemory"));
    buttonColor.setNBT(nbt.getCompoundTag("buttonColor"));
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setCompoundTag("buttonFPS", buttonFPS.getNBT());
    nbt.setCompoundTag("buttonChunk", buttonChunk.getNBT());
    nbt.setCompoundTag("buttonEntity", buttonEntity.getNBT());
    nbt.setCompoundTag("buttonParticle", buttonParticle.getNBT());
    nbt.setCompoundTag("buttonWorld", buttonWorld.getNBT());
    nbt.setCompoundTag("buttonPosition", buttonPosition.getNBT());
    nbt.setCompoundTag("buttonLight", buttonLight.getNBT());
    nbt.setCompoundTag("buttonSpeed", buttonSpeed.getNBT());
    nbt.setCompoundTag("buttonMemory", buttonMemory.getNBT());
    nbt.setCompoundTag("buttonColor", buttonColor.getNBT());
    super.saveToNBT(nbt);
  }

  public String getName()
  {
    return "Debug Screen";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.TOPLEFT;
  }

  public int getDefaultPosX()
  {
    return 0;
  }

  public int getDefaultPosY()
  {
    return 0;
  }

  public int getWidth()
  {
    return 0;
  }

  public int getHeight()
  {
    return 0;
  }

  public boolean isMoveable()
  {
    return false;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 16, 16, 16, 16);
  }
}
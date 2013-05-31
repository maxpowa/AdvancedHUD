package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import java.awt.image.BufferedImage;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

public class HudItemMap extends HudItem
{
  protected int mapTexture;
  protected int[] buffer;
  protected final int mapSize = 128;
  protected final int center = 64;
  protected int colorOffset;
  protected int prevheight = 0;
  protected int heightDiff;

  public HudItemMap()
  {
    mapTexture = AHud.getMinecraftInstance().renderEngine.allocateAndSetupTexture(new BufferedImage(128, 128, 2));
    buffer = new int[16384];
  }

  public void tick()
  {
    int playerX = (int)AHud.getMinecraftInstance().thePlayer.posX - 64;
    int playerZ = (int)AHud.getMinecraftInstance().thePlayer.posZ - 64;

    for (int x = 8; x < 120; x++) {
      for (int y = 8; y < 120; y++) {
        int blockWorldX = playerX + x;
        int blockWorldZ = playerZ + y;

        buffer[(x + y * 128)] = (0xFF000000 | getMapColorXZ(blockWorldX, blockWorldZ));
      }
    }
    buffer[8256] = -1;
  }

  public void render(float f)
  {
    int width = getWidth();
    int height = getHeight();

    AHud.getMinecraftInstance().renderEngine.createTextureFromBytes(buffer, 128, 128, mapTexture);

    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    RenderHelper.bindTexture("/misc/mapbg.png");

    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV(posX, posY + height, 0.0D, 0.0D, 1.0D);
    tessellator.addVertexWithUV(posX + width, posY + height, 0.0D, 1.0D, 1.0D);
    tessellator.addVertexWithUV(posX + width, posY, 0.0D, 1.0D, 0.0D);
    tessellator.addVertexWithUV(posX, posY, 0.0D, 0.0D, 0.0D);
    tessellator.draw();

    GL11.glBindTexture(3553, mapTexture);

    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV(posX, posY + height, 0.0D, 0.0D, 1.0D);
    tessellator.addVertexWithUV(posX + width, posY + height, 0.0D, 1.0D, 1.0D);
    tessellator.addVertexWithUV(posX + width, posY, 0.0D, 1.0D, 0.0D);
    tessellator.addVertexWithUV(posX, posY, 0.0D, 0.0D, 0.0D);
    tessellator.draw();
  }

  protected int getMapColorXZ(int blockWorldX, int blockWorldZ) {
    Chunk chunk = AHud.getMinecraftInstance().theWorld.getChunkFromBlockCoords(blockWorldX, blockWorldZ);

    int blockChunkX = wrapValue(blockWorldX);
    int blockChunkZ = wrapValue(blockWorldZ);

    int blockY = chunk.getHeightValue(blockChunkX % 16, blockChunkZ % 16) + 1;
    if (blockY > 0) {
      int blockId = chunk.getBlockID(blockChunkX, blockY, blockChunkZ);
      boolean validBlock;
      do {
        validBlock = true;

        if (blockId == 0)
          validBlock = false;
        else if ((blockY > 0) && (blockId > 0) && (Block.blocksList[blockId].blockMaterial.materialMapColor == MapColor.airColor)) {
          validBlock = false;
        }

        if (!validBlock) {
          blockY--;
          blockId = chunk.getBlockID(blockChunkX, blockY, blockChunkZ);
        }
      }
      while ((blockY > 0) && (!validBlock));

      if ((blockY > 0) && (Block.blocksList[blockId].blockMaterial.isLiquid())) {
        while ((blockY > 0) && (blockId > 0) && (Block.blocksList[blockId].blockMaterial.isLiquid())) {
          blockY--;
          blockId = chunk.getBlockID(blockChunkX, blockY, blockChunkZ);
        }
        blockY++;
        blockId = chunk.getBlockID(blockChunkX, blockY, blockChunkZ);
      }

      int color = MapColor.airColor.colorValue;
      Block block = Block.blocksList[blockId];
      if (block == null) {
        return 0;
      }

      color = block.blockMaterial.materialMapColor.colorValue;

      if (block.blockMaterial.isLiquid()) {
        heightDiff = ((blockY - 64) / 5 + 1);
      } else {
        heightDiff = (blockY - prevheight);
        prevheight = blockY;
      }

      colorOffset = 220;
      if (heightDiff > 0)
        colorOffset = 255;
      else if (heightDiff < 0) {
        colorOffset = 185;
      }

      int R = (color >> 16 & 0xFF) * colorOffset / 255;
      int G = (color >> 8 & 0xFF) * colorOffset / 255;
      int B = (color & 0xFF) * colorOffset / 255;

      return R << 16 | G << 8 | B;
    }
    return 0;
  }

  protected int wrapValue(int val)
  {
    val %= 16;
    if (val < 0) {
      val = 16 + val;
    }
    return val;
  }

  public String getName()
  {
    return "Map (beta)";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.TOPRIGHT;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth - 138;
  }

  public int getDefaultPosY()
  {
    return 10;
  }

  public int getWidth()
  {
    return 128;
  }

  public int getHeight()
  {
    return 128;
  }

  public void drawIcon(int posX, int posY) {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 64, 16, 16, 16);
  }

  public boolean isEnabledByDefault() {
    return false;
  }
}
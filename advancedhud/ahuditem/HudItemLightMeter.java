package advancedhud.ahuditem;

import advancedhud.AHud;
import advancedhud.Alignment;
import advancedhud.RenderHelper;
import advancedhud.button.ButtonToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

public class HudItemLightMeter extends HudItem
{
  ButtonToggle btnBatsSpawn = new ButtonToggle(0, "bats can spawn", true);
  ButtonToggle btnMobsSpawn = new ButtonToggle(1, "monsters can spawn", true);
  ButtonToggle btnBlazeSpawn = new ButtonToggle(2, "plants can grow", true);
  ButtonToggle btnPlantsGrow = new ButtonToggle(3, "blaze can spawn", true);

  protected int lightValue = 0;
  protected int sliderOffset = 0;
  protected boolean canBatsSpawn;
  protected boolean canMobsSpawn;
  protected boolean canBlazeSpawn;
  protected boolean canPlantsGrow;

  public void render(float f)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    int x1 = posX + 8 > AHud.screenWidth / 2 ? 8 : 0;
    int x2 = 8 - x1;

    RenderHelper.drawSprite(posX + x1, posY, 8, 64, 8, 56);

    RenderHelper.drawSprite(posX + x1, posY + sliderOffset, 0, 64 + sliderOffset, 8, 56 - sliderOffset);

    float colorOffset = lightValue / 15.0F * 0.4F + 0.6F;
    GL11.glColor4f(colorOffset, colorOffset, colorOffset, 1.0F);
    RenderHelper.drawSprite(posX + x1, posY + sliderOffset - 4, 0, 56, 8, 8);

    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int y = 4;

    if (canMobsSpawn) {
      RenderHelper.bindTexture("/mob/zombie.png");
      RenderHelper.drawSprite(posX + x2, posY + 48 - y, 8, 8, 8, 8, 64, 64, 0);
      y += 9;
    }

    if (canBlazeSpawn) {
      RenderHelper.bindTexture("/mob/fire.png");
      RenderHelper.drawSprite(posX + x2, posY + 48 - y, 8, 8, 8, 8, 64, 32, 0);
      y += 9;
    }

    if (canPlantsGrow) {
      RenderHelper.bindTexture("/gui/items.png");
      RenderHelper.drawSprite(posX + x2, posY + 48 - y, 72, 8, 8, 8, 128, 128, 0);
      y += 9;
    }

    if (canBatsSpawn) {
      RenderHelper.bindTexture("/mob/bat.png");
      RenderHelper.drawSprite(posX + x2, posY + 48 - y, 25, 2, 3, 3, 64, 64, 0);
      RenderHelper.drawSprite(posX + x2 + 5, posY + 48 - y, 25, 2, 3, 3, 64, 64, 0);
      RenderHelper.drawSprite(posX + x2 + 1, posY + 48 - y + 2, 6, 6, 6, 6, 64, 64, 0);
      y += 9;
    }
  }

  public void tick()
  {
    Minecraft mc = AHud.getMinecraftInstance();
    int blockX = MathHelper.floor_double(mc.thePlayer.posX);
    int blockY = MathHelper.floor_double(mc.thePlayer.boundingBox.minY);
    int blockZ = MathHelper.floor_double(mc.thePlayer.posZ);

    if ((mc.theWorld != null) && (mc.theWorld.blockExists(blockX, blockY, blockZ))) {
      lightValue = mc.theWorld.getChunkFromBlockCoords(blockX, blockZ).getBlockLightValue(blockX & 0xF, blockY, blockZ & 0xF, mc.theWorld.calculateSkylightSubtracted(1.0F));
      sliderOffset = ((int)((15 - lightValue) * 3.5D));

      canBatsSpawn = ((btnBatsSpawn.value) && (lightValue <= 10) && (!mc.theWorld.provider.isHellWorld));
      canMobsSpawn = ((btnMobsSpawn.value) && (lightValue <= 7) && (!mc.theWorld.provider.isHellWorld));
      canBlazeSpawn = ((btnBlazeSpawn.value) && (lightValue <= 12) && (mc.theWorld.provider.isHellWorld));
      canPlantsGrow = ((btnPlantsGrow.value) && (lightValue >= 9) && (!mc.theWorld.provider.isHellWorld));
    }
  }

  public String getName()
  {
    return "Light level meter";
  }

  public Alignment getDefaultAlignment()
  {
    return Alignment.BOTTOMRIGHT;
  }

  public int getDefaultPosX()
  {
    return AHud.screenWidth - getWidth();
  }

  public int getDefaultPosY()
  {
    return AHud.screenHeight - getHeight();
  }

  public int getWidth()
  {
    return 16;
  }

  public int getHeight()
  {
    return 56;
  }

  public boolean isEnabledByDefault()
  {
    return false;
  }

  public void drawIcon(int posX, int posY)
  {
    RenderHelper.bindTexture("/gui/ahud_icons.png");
    RenderHelper.drawSprite(posX, posY, 80, 16, 16, 16);
  }
}
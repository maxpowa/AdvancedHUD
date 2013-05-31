package advancedhud;

import advancedhud.ahuditem.HudItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class SaveController
{
  protected static final String dirName = Minecraft.getMinecraftDir() + File.separator + "config" + File.separator + "AdvancedHud";
  protected static File dir = new File(dirName);

  public static void loadConfig(String name) {
    loadConfig(name, null);
  }

  public static void loadConfig(String name, String dirName) {
    System.out.print("[AdvancedHUD] loading from file: ");

    if (dirName != null) {
      AHud.getMinecraftInstance(); dir = new File(Minecraft.getMinecraftDir() + File.separator + dirName);
    }

    String fileName = name + ".CFG";
    File file = new File(dir, fileName);

    if (!file.exists()) {
      System.out.println(file.getPath() + " (canceled, file does not exist)");
      return;
    }
    System.out.println(file.getPath());
    try
    {
      NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));

      AHud.readFromNBT(nbt.getCompoundTag("global"));

      for (Object item_ : AHud.getHudItemList()) {
		 HudItem item = (HudItem) item_;
        NBTTagCompound itemNBT = nbt.getCompoundTag(item.getName());
        item.loadFromNBT(itemNBT);
      }
    }
    catch (IOException e)
    {
      NBTTagCompound nbt;
      e.printStackTrace();
    }
  }

  public static void saveConfig(String name) {
    saveConfig(name, null);
  }

  public static void saveConfig(String name, String dirName) {
    System.out.print("[AdvancedHUD] saving to file: ");

    if (dirName != null) {
      AHud.getMinecraftInstance(); dir = new File(Minecraft.getMinecraftDir() + File.separator + dirName);
    }

    if ((!dir.exists()) && 
      (!dir.mkdirs())) {
      throw new ReportedException(new CrashReport("Unable to create the configuration directories", new Throwable()));
    }

    String fileName = name + ".CFG";
    File file = new File(dir, fileName);

    System.out.println(file.getPath());
    try
    {
      NBTTagCompound nbt = new NBTTagCompound();
      FileOutputStream fileOutputStream = new FileOutputStream(file);

      NBTTagCompound globalNBT = new NBTTagCompound();
      AHud.writeToNBT(globalNBT);
      nbt.setCompoundTag("global", globalNBT);

      for (Object item_ : AHud.getHudItemList()) {
    	  HudItem item = (HudItem) item_;
        NBTTagCompound itemNBT = new NBTTagCompound();
        item.saveToNBT(itemNBT);
        nbt.setCompoundTag(item.getName(), itemNBT);
      }

      CompressedStreamTools.writeCompressed(nbt, fileOutputStream);
      fileOutputStream.close();
    } catch (IOException e) {
      throw new ReportedException(new CrashReport("An error occured while saving", new Throwable()));
    }
  }

  public static File[] getConfigs() {
    return dir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String filename) {
        return filename.endsWith(".CFG");
      }
    });
  }

  static
  {
    AHud.getMinecraftInstance();
  }
}
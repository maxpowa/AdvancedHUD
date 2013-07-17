package advancedhud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import advancedhud.api.HUDRegistry;
import advancedhud.api.HudItem;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class SaveController {
    protected static final String dirName = Minecraft.getMinecraft().mcDataDir
            + File.separator + "config" + File.separator + "AdvancedHud";
    protected static File dir = new File(dirName);

    public static boolean loadConfig(String name) {
        return loadConfig(name, null);
    }

    public static boolean loadConfig(String name, String dirName) {
        System.out.print("[AdvancedHUD] Loading");

        if (dirName != null) {
            HUDRegistry.getMinecraftInstance();
            dir = new File(Minecraft.getMinecraft().mcDataDir + File.separator
                    + dirName);
        }

        String fileName = name + ".dat";
        File file = new File(dir, fileName);

        if (!file.exists()) {
            System.out.println(" canceled, file does not exist.");
            return false;
        } else {
            System.out.println(" successful.");
        }
        try {
            NBTTagCompound nbt = CompressedStreamTools
                    .readCompressed(new FileInputStream(file));

            HUDRegistry.readFromNBT(nbt.getCompoundTag("global"));

            for (HudItem item : HUDRegistry.getHudItemList()) {
                NBTTagCompound itemNBT = nbt.getCompoundTag(item.getName());
                item.loadFromNBT(itemNBT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void saveConfig(String name) {
        saveConfig(name, null);
    }

    public static void saveConfig(String name, String dirName) {
        System.out.println("[AdvancedHUD] Saving...");

        if (dirName != null) {
            HUDRegistry.getMinecraftInstance();
            dir = new File(Minecraft.getMinecraft().mcDataDir + File.separator
                    + dirName);
        }

        if (!dir.exists() && !dir.mkdirs())
            throw new ReportedException(new CrashReport(
                    "Unable to create the configuration directories",
                    new Throwable()));

        String fileName = name + ".dat";
        File file = new File(dir, fileName);
        
        try {
            NBTTagCompound nbt = new NBTTagCompound();
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            NBTTagCompound globalNBT = new NBTTagCompound();
            HUDRegistry.writeToNBT(globalNBT);
            nbt.setCompoundTag("global", globalNBT);

            for (HudItem item : HUDRegistry.getHudItemList()) {
                NBTTagCompound itemNBT = new NBTTagCompound();
                item.saveToNBT(itemNBT);
                nbt.setCompoundTag(item.getName(), itemNBT);
            }

            CompressedStreamTools.writeCompressed(nbt, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new ReportedException(new CrashReport(
                    "An error occured while saving", new Throwable()));
        }
    }

    public static File[] getConfigs() {
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".dat");
            }
        });
    }

    static {
        HUDRegistry.getMinecraftInstance();
    }
}
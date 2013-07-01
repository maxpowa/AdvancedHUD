package advancedhud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "AdvancedHUD", name = "AdvancedHUD", version = "Version [@VERSION@] for @MCVERSION@")
public class AdvancedHUD {

    public static String MC_VERSION = "@MCVERSION@";
    public static String TK_VERSION = "@VERSION@";
    
    public static File configFile = null;
    
    @Init
    public void onInit(FMLInitializationEvent event) {
        configFile = getConfigFile();
        System.out.println("Loaded config file: " + configFile.getAbsolutePath());
        KeyBindingRegistry.registerKeyBinding(new KeyRegister());
        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
    }
    
    public static File getConfigFile() {
        return createAndGetNBTFile(createAndGetFile(new File(new File(Minecraft.getMinecraft().mcDataDir, "AdvancedHUD"), "config.dat")));
    }
    
    public static File createAndGetNBTFile(File f) {
        try {
            CompressedStreamTools.readCompressed(new FileInputStream(f));
        } catch (Exception e) {
            NBTTagCompound cmp = new NBTTagCompound();
            try {
                CompressedStreamTools.writeCompressed(cmp,
                        new FileOutputStream(f));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return f;
    }
    
    public static File createAndGetFile(File f) {
        if (!f.exists()) {
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return f;
    }
    
}

package advancedhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyRegister {

    public static KeyBinding config = new KeyBinding("\u00a7aAdvancedHUD Config", Keyboard.KEY_H, "key.categories.misc");

    static {
        ClientRegistry.registerKeyBinding(config);
    }

    @SubscribeEvent
    public void KeyInputEvent(KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (config.isPressed() && mc.currentScreen == null) {
            mc.displayGuiScreen(new GuiAdvancedHUDConfiguration());
        }
    }
}

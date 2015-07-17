package advancedhud;

import advancedhud.client.ui.GuiAdvancedHUDConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyRegister {

    public static KeyBinding config = new KeyBinding("\u00a7aAdvancedHUD Config", Keyboard.KEY_H, "key.categories.misc");

    static {
        ClientRegistry.registerKeyBinding(config);
    }

    @SubscribeEvent
    public void KeyInputEvent(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (config.isPressed() && mc.currentScreen == null) {
            mc.displayGuiScreen(new GuiAdvancedHUDConfiguration());
        }
    }
}

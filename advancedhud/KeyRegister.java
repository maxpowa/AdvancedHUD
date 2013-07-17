package advancedhud;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import advancedhud.client.ui.GuiAdvancedHUDConfiguration;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyRegister extends KeyHandler {

    public static KeyBinding config = new KeyBinding("\u00a7aAdvancedHUD Config", Keyboard.KEY_H);

    public KeyRegister() {
        super(new KeyBinding[] { config }, new boolean[] { false });
    }

    @Override
    public String getLabel() {
        return "AdvancedHUD";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb,
            boolean tickEnd, boolean isRepeat) {
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
        Minecraft mc = Minecraft.getMinecraft();
        if (kb.keyCode == config.keyCode && mc.currentScreen == null) {
            mc.displayGuiScreen(new GuiAdvancedHUDConfiguration());
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.allOf(TickType.class);
    }

}

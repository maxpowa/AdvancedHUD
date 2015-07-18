package advancedhud.api;

import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * Extend this to create your own elements which render on the GUI.<br>
 * Don't worry about saves, they are all done by the non-api part of the mod.
 * 
 * @author maxpowa
 * 
 */
public abstract class HudItem {
    public Alignment alignment;
    public int posX;
    public int posY;
    private int id;
    private float opacity = 1.0f;
    public boolean rotated = false;
    protected TweenManager manager = null;

    public HudItem() {
        alignment = getDefaultAlignment();
        posX = getDefaultPosX();
        posY = getDefaultPosY();
        id = getDefaultID();
        manager = new TweenManager();
    }

    /**
     * Unique name for the HudItem, only used for NBT saving/loading
     * 
     * @return String value for unique identifier of the {@link HudItem}
     */
    public abstract String getName();

    /**
     * Display name for the HudItem in config screen
     * 
     * @return String value for display name of the {@link HudItem}
     */
    public abstract String getButtonLabel();

    /**
     * Default {@link Alignment} of the HudItem instance.
     * <p>
     * For resolution-based movement, alignment values allow shifting along the
     * alignment axis.
     * 
     * @return {@link Alignment}
     */
    public abstract Alignment getDefaultAlignment();

    public abstract int getDefaultPosX();

    public abstract int getDefaultPosY();

    public abstract int getWidth();

    public abstract int getHeight();

    /**
     * Button ID for configuration screen, 0-25 are reserved for Vanilla use.
     */
    public abstract int getDefaultID();

    /**
     * Define custom GuiScreen instances for your own configuration screen.
     */
    public abstract GuiScreen getConfigScreen();

    public abstract void render(float partialTicks);

    /**
     * 
     * If you don't want any rotation, you can <br>simply make this method return.
     *
     */
    public void rotate() {
        rotated = !rotated;
    }

    /**
     * Called upon .updateTick(). If you use this, make sure you set<br>
     * {@link HudItem}.needsTick() to true.
     */
    public void tick() {

    }

    /**
     *
     * @param delta Delta time (in seconds) since this method was last called
     */
    public void update(float delta) {
        manager.update(delta); // Tween manager expects delta to be in seconds.
    }

    public boolean needsTween() { return false; }

    /**
     * Set this to true if you require the {@link HudItem}.tick() method to run<br>
     */
    public boolean needsTick() {
        return false;
    }

    public boolean isMoveable() {
        return true;
    }

    public boolean isEnabledByDefault() {
        return true;
    }

    public boolean isRenderedInCreative() {
        return true;
    }

    /**
     * Ensures that the HudItem will never be off the screen
     */
    public void fixBounds() {
        posX = Math.max(0, Math.min(HUDRegistry.screenWidth - getWidth(), posX));
        posY = Math.max(0, Math.min(HUDRegistry.screenHeight - getHeight(), posY));
    }

    public void loadFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("posX")) {
            posX = nbt.getInteger("posX");
        } else {
            posX = getDefaultPosX();
        }
        if (nbt.hasKey("posY")) {
            posY = nbt.getInteger("posY");
        } else {
            posY = getDefaultPosY();
        }
        if (nbt.hasKey("alignment")) {
            alignment = Alignment.fromString(nbt.getString("alignment"));
        } else {
            alignment = getDefaultAlignment();
        }
        if (nbt.hasKey("id")) {
            id = nbt.getInteger("id");
        } else {
            id = getDefaultID();
        }
        if (nbt.hasKey("rotated")) {
            rotated = nbt.getBoolean("rotated");
        } else {
            rotated = false;
        }
    }

    public void saveToNBT(NBTTagCompound nbt) {
        nbt.setInteger("posX", posX);
        nbt.setInteger("posY", posY);
        nbt.setString("alignment", alignment.toString());
        nbt.setInteger("id", id);
        nbt.setBoolean("rotated", rotated);
    }

    public boolean shouldDrawOnMount() {
        return false;
    }

    public boolean shouldDrawAsPlayer() {
        return true;
    }

    public boolean canRotate() {
        return true;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public interface TweenEngine<T extends HudItem> extends TweenAccessor<T> {}
}
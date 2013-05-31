package advancedhud.ahuditem;

import advancedhud.button.Button;
import advancedhud.button.ButtonMultiState;
import advancedhud.renderer.Renderer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;

public abstract class HudItemDynamic extends HudItem
{
  protected List rendererList = new ArrayList();
  protected int currentRendererId;
  public NBTTagCompound properties = new NBTTagCompound();
  protected ButtonMultiState buttonStyle;

  public void render(float f)
  {
    getCurrentRenderer().render(f);
  }

  public void tick()
  {
    getCurrentRenderer().tick(properties);
    tick(properties);
  }

  public abstract void tick(NBTTagCompound paramNBTTagCompound);

  public int getWidth() {
    return getCurrentRenderer().getWidth();
  }

  public int getHeight() {
    return getCurrentRenderer().getHeight();
  }

  public boolean isMoveable()
  {
    return getCurrentRenderer().isMoveable();
  }

  protected void addRenderer(Renderer renderer)
  {
    if (!rendererList.contains(renderer))
      rendererList.add(renderer);
  }

  protected void setCurrentRenderer(Renderer renderer)
  {
    if (rendererList.contains(renderer))
      currentRendererId = rendererList.indexOf(renderer);
  }

  public Renderer getCurrentRenderer()
  {
    return (Renderer)rendererList.get(currentRendererId);
  }

  public List getButtonList()
  {
    List buttonList = new ArrayList();

    if (rendererList.size() > 1) {
      buttonList.add(getButtonStyle(false));
    }

    buttonList.addAll(super.getButtonList());

    buttonList.addAll(getCurrentRenderer().getButtonList());

    return buttonList;
  }

  public void onButtonClick(Button button, GuiScreen parent)
  {
    if (button == buttonStyle) {
      currentRendererId = ((ButtonMultiState)button).value;
    }

    super.onButtonClick(button, parent);

    if (getCurrentRenderer().getButtonList().contains(button)) {
      getCurrentRenderer().onButtonClick(button, parent);
      return;
    }
  }

  protected ButtonMultiState getButtonStyle(boolean newInstance)
  {
    if ((buttonStyle == null) || (newInstance))
    {
      List names = new ArrayList();
      for (Object renderer_ : rendererList) {
    	  Renderer renderer = (Renderer) renderer_;
        names.add(renderer.getName());
      }

      buttonStyle = new ButtonMultiState(0, "Style", (String[])names.toArray(new String[names.size()]));
      buttonStyle.value = currentRendererId;
    }
    return buttonStyle;
  }

  public void loadFromNBT(NBTTagCompound nbt)
  {
    currentRendererId = nbt.getInteger("currentRenderer");

    for (Object renderer_ : rendererList) {
  	  Renderer renderer = (Renderer) renderer_;
      renderer.readFromNBT(nbt.getCompoundTag(renderer.getName()));
    }
    super.loadFromNBT(nbt);
  }

  public void saveToNBT(NBTTagCompound nbt)
  {
    nbt.setInteger("currentRenderer", currentRendererId);

    for (Object renderer_ : rendererList) {
  	  Renderer renderer = (Renderer) renderer_;
      NBTTagCompound rendererNBT = new NBTTagCompound();
      renderer.writeToNBT(rendererNBT);
      nbt.setCompoundTag(renderer.getName(), rendererNBT);
    }
    super.saveToNBT(nbt);
  }
}
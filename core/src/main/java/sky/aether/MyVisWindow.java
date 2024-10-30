package sky.aether;

import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisWindow;

public class MyVisWindow extends VisWindow {
  public MyVisWindow(String title) {
    super(title);
  }
  protected void blankRow() {
    add(new VisLabel("")).row();
  }
}

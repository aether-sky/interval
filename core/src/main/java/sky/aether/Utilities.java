package sky.aether;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class Utilities {
  public static void addClickListener(VisTextButton button, TriConsumer<InputEvent,Float,Float> handler) {
    button.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        handler.accept(event, x, y);
      }
    });
  }
}

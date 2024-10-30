package sky.aether;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.VisUI.SkinScale;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class IntervalForm extends ApplicationAdapter {
  private Stage stage;

  @Override
  public void create() {
    VisUI.setSkipGdxVersionCheck(true);
    VisUI.load(SkinScale.X2);
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);

    switchToConfig();
  }

  private void switchToTimer(IntervalData data) {
    stage.clear();
    stage.addActor(new TimerWindow(this::switchToConfig, data));
  }

  private void switchToCredits() {
    stage.clear();
    stage.addActor(new CreditsWindow(this::switchToConfig));
  }

  private void switchToConfig() {
    stage.clear();
    stage.addActor(new ConfigWindow(this::switchToTimer, this::switchToCredits));
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void render() {
    ScreenUtils.clear(0f, 0f, 0f, 1f);
    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
    stage.draw();
  }

  @Override
  public void dispose() {
    VisUI.dispose();
    stage.dispose();
  }

}

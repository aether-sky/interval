package sky.aether;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

public class TimerWindow extends VisWindow {
  VisLabel intervalLabel;
  VisLabel stageLabel;
  public TimerWindow(Runnable stageSwitcher, IntervalData data) {
    super("");
    TableUtils.setSpacingDefaults(this);
    columnDefaults(0).left();

    Skin skin = VisUI.getSkin();

    this.intervalLabel = new VisLabel("0/");
    this.stageLabel = makeStyledLabel(skin, "deja_32.fnt", "largeFont");
    stageLabel.setText("Warmup");

    IntervalTimer timer = new IntervalTimer(data, this::setIntervalLabel, this::setStageLabel);

    VisLabel timerClock = makeStyledLabel(skin, "deja_128.fnt", "extraLargeFont");

    VisLabel totalClock = new VisLabel();
    VisLabel totalLabel = new VisLabel("Total Remaining:");
    VisLabel elapsedClock = new VisLabel();
    VisLabel elapsedLabel = new VisLabel("Total Elapsed:");
    timerClock.addAction(new Action() {
      @Override
      public boolean act(float delta) {
        timer.update(delta);
        timerClock.setText(formatMinSecs(timer.getStageTimeLeft()));
        totalClock.setText(formatMinSecs(timer.getTotalTimeLeft()));
        elapsedClock.setText(formatMinSecs(timer.getTotalElapsed()));

        return false;
      }
    });

    blankRow();
    add(stageLabel).colspan(2).center().row();
    add(timerClock).colspan(3).row();
    blankRow();
    add(totalLabel);
    add(totalClock).row();
    add(elapsedLabel);
    add(elapsedClock).row();
    add(new VisLabel("Intervals:"));
    add(intervalLabel).row();
    blankRow();

    VisTextButton returnButton = new VisTextButton("Return");
    Utilities.addClickListener(returnButton, (event, x, y) -> {
      stageSwitcher.run();
    });
    VisTextButton playButton = new VisTextButton("Play");
    Utilities.addClickListener(playButton, (event, x, y) -> {
      if (!timer.isPlaying()) {
        timer.play();
        playButton.setText("Pause");
      } else {
        timer.pause();
        playButton.setText("Play");
      }
      });
    VisTextButton resetButton = new VisTextButton("Reset");
    Utilities.addClickListener(resetButton, (event, x, y) -> {
        timer.reset();
        playButton.setText("Play");
      });

    add(playButton).width(150);
    add(resetButton).width(150).row();
    blankRow();
    blankRow();
    blankRow();
    add(returnButton).width(100).colspan(3).align(Align.bottomRight).row();
    pack();
    setSize(500,500);
    centerWindow();
  }

  private VisLabel makeStyledLabel(Skin skin, String fontName, String styleName) {
    BitmapFont newFont = new BitmapFont(Gdx.files.internal(fontName));
    Label.LabelStyle elLabelStyle = new Label.LabelStyle();
    elLabelStyle.font = newFont;
    elLabelStyle.fontColor = skin.getColor("white");
    skin.add(styleName, elLabelStyle, Label.LabelStyle.class);
    return new VisLabel("", styleName);

  }

  private void setIntervalLabel(String text) {
    this.intervalLabel.setText(text);
  }

  private void setStageLabel(String text) {
    this.stageLabel.setText(text);
  }

  private void blankRow() {
    add(new VisLabel("")).row();
  }

  private String formatTime(int t) {
    if (t < 10) return "0" + t;
    return t + "";
  }

  private String formatMinSecs(double s) {
    Pair<Integer,Integer> minsec = getMinsSeconds(s);
    String mins = formatTime(minsec.first);
    String secs = formatTime(minsec.second);
    return mins + ":" + secs;
    //gwt doesn't implement this
    //return String.format("%02d:%02d", minsec.first, minsec.second);
  }

  private Pair<Integer,Integer> getMinsSeconds(double t) {
    t = t+0.99;
    int mins = (int)(t/60);
    int secs = (int)(t - mins*60);
    return new Pair<>(mins, secs);
  }
}

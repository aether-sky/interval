package sky.aether;

import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.Arrays;


public class CreditsWindow extends VisWindow {
  public CreditsWindow(Runnable stageSwitcher) {
    super("");
    TableUtils.setSpacingDefaults(this);
    columnDefaults(0).left();

    String[] credits = new String[] {
      "Credits: ",
      "",
      "Sounds taken from freesound.org",
      "3-beep-b (softbeep) by user jobro",
      "beeps3 (trill) by user steveygos93",
      "Vandierdonck_Joan_2015_2016_alarm_beep (alarm) by user univ_lyon3",
      "morning-bird-chirping (chirp) by user fudgealtoid"
    };
    String label = Arrays.stream(credits)
                         .reduce((a, b) -> a + "\n" + b)
                         .orElse("");
    VisLabel creditsLabel = new VisLabel(label);
    add(creditsLabel).row();

    VisTextButton returnButton = new VisTextButton("Return");
    Utilities.addClickListener(returnButton, (event, x, y) -> {
      stageSwitcher.run();
    });
    add(returnButton).align(Align.bottomRight).row();
    pack();
    centerWindow();
  }
}

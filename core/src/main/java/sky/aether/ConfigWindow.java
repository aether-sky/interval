package sky.aether;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.*;

import java.util.ArrayList;
import java.util.function.Consumer;


public class ConfigWindow extends MyVisWindow {

  ArrayList<VisTextField> inputFields = new ArrayList<>();
  public ConfigWindow(Consumer<IntervalData> stageSwitcher, Runnable creditsSwitcher) {
    super("");
    int winWidth = Gdx.graphics.getWidth();
    int winHeight = Gdx.graphics.getHeight();
    TableUtils.setSpacingDefaults(this);
    columnDefaults(0).left();
    SoundManager.init();

    VisTextField nameField = makeSimpleField("Name2:", false, false);
    VisTextField setsField = makeSimpleField("Num sets:", true, true);
    VisTextField hiField = makeSimpleField("High intensity:", true, false);
    VisTextField liField = makeSimpleField("Low intensity:", true, true);
    VisTextField cooldownField = makeSimpleField("Cooldown:", true, false);
    VisTextField warmupField = makeSimpleField("Warmup:", true, true);

    Skin skin = VisUI.getSkin();//new Skin(Gdx.files.internal("uiskin.json"));
    VisLabel sfxLabel = new VisLabel("Sound Effect:");
    sfxLabel.setAlignment(Align.right);
    SelectBox<String> sfxPicker = new SelectBox<>(skin);
    String[] options = {SoundManager.defaultSfx, SoundManager.alarmSfx, SoundManager.softbeepSfx, SoundManager.trillSfx};
    sfxPicker.setItems(options);
    sfxPicker.addListener(
      new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
          String selected = sfxPicker.getSelected();
          SoundManager.play(selected);
        }
      });

    VisTable sfxTable = new VisTable(true);
    sfxTable.add(sfxLabel).top().left().width(100);
    sfxTable.add(sfxPicker).width(150);
    add(sfxTable).align(Align.center);//.row();

    VisTable createTable = new VisTable(true);

    VisTextButton createButton = new VisTextButton("create");

    createTable.add(createButton).colspan(2).width(250);
    add(createTable).align(Align.center).row();

    blankRow();

    IntervalList timerList = new IntervalList(skin);
    ScrollPane scrollPane = new ScrollPane(timerList, skin);
    add(scrollPane).height(200).width(winWidth - 100).colspan(2).row();
    nameField.setText(timerList.GetNextName());

    blankRow();

    VisTable buttonTable = new VisTable(true);
    VisTextButton runButton = new VisTextButton("Run Timer");
    buttonTable.add(runButton).width(250).align(Align.center);
    Utilities.addClickListener(runButton, (event, x, y)-> {
      if (timerList.isEmpty()) return;
      stageSwitcher.accept(timerList.getSelected());

    });
    buttonTable.add(new VisLabel("                        "));
    VisTextButton deleteButton = new VisTextButton("Delete");
    buttonTable.add(deleteButton).width(250).align(Align.center);
    Utilities.addClickListener(deleteButton, (event, x, y) -> {
      timerList.deleteSelected();
    });

    Utilities.addClickListener(createButton, (event, x, y) -> {
      try {
        IntervalData data = new IntervalData(nameField.getText(),
          Integer.parseInt(setsField.getText()),
          Integer.parseInt(hiField.getText()),
          Integer.parseInt(liField.getText()),
          Integer.parseInt(warmupField.getText()),
          Integer.parseInt(cooldownField.getText()),
          sfxPicker.getSelected()
        );
        clearFields();
        timerList.Insert(data);
        nameField.setText(timerList.GetNextName());

      } catch (NumberFormatException nfe) {

      }
    });


    add(buttonTable).colspan(4).align(Align.center).row();
    VisTextButton creditsButton = new VisTextButton("Credits");
    Utilities.addClickListener(creditsButton, (event, x, y) -> {
      creditsSwitcher.run();
    });
    blankRow();
    add(creditsButton).width(150).colspan(4).align(Align.bottomRight).row();
    pack();
    setSize(winWidth, winHeight);
    centerWindow();
  }

  private void clearFields() {
    for (VisTextField field : inputFields) {
      field.setText("");
    }
  }

  private VisTextField makeSimpleField(String labelText, boolean isNumber, boolean row) {
    VisLabel label = new VisLabel(labelText);
    label.setAlignment(Align.right);
    VisTextField field;
    if (isNumber) {
      field = makeNumField();
    } else {
      field = new VisTextField();
    }
    inputFields.add(field);
    VisTable table = new VisTable(true);
    table.add(label).top().left().width(100);
    table.add(field).width(175);
    add(table).align(Align.center);
    if (row) {
      row();
    }

    return field;
  }

  private VisTextField makeNumField() {
    VisTextField result = new VisTextField();
    result.setTextFieldFilter((textField, c) -> "0123456789".contains(c + ""));
    return result;
  }

}

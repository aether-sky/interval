package sky.aether;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class IntervalList extends List<IntervalData> {
  private static SaveData saveData;

  private ArrayList<IntervalData> intervals;

  public IntervalList(Skin skin) {
    super(skin);
    Array<IntervalData> array = new Array<>();
    SaveData save = new SaveData();
    ArrayList<IntervalData> intervals = new ArrayList<>();
    if (save.isEmpty()) {
      intervals.add(IntervalData.Default);
    } else {
      intervals = save.getSavedIntervals();
    }
    save.saveIntervals(intervals);
    saveData = save;
    for (IntervalData data : intervals) {
      array.add(data);
    }
    this.setItems(array);

  }

  public boolean isEmpty() { return getItems().isEmpty(); }

  public void deleteSelected() {
    Array<IntervalData> items = this.getItems();
    if (items.isEmpty()) return;
    IntervalData selected = this.getSelected();
    Array<IntervalData> newItems = new Array<>();
    for (IntervalData i : items) {
      if (i.equals(selected)) {
        continue;
      }
      newItems.add(i);
    }
    setItems(newItems);
    saveData.saveIntervalsArray(newItems);
  }

  public void Insert(IntervalData data) {
    Array<IntervalData> items = this.getItems();
    items.add(data);
    saveData.saveIntervalsArray(items);
    this.setItems(items);
  }

  public String GetNextName() {
    int i = 0;
    boolean found = false;
    String baseName = "New Timer";
    String testName;
    while (true) {
      testName = baseName + i;
      for (IntervalData data : this.getItems()) {
        if (data.name.equals(testName)) {
          i += 1;
          found = true;
        }
      }
      if (!found) break;
      found = false;
    }
    return testName;
  }
}

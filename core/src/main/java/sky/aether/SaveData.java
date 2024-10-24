package sky.aether;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class SaveData {
  private final Preferences preferences;
  public SaveData() {
    preferences = Gdx.app.getPreferences("sky.aether.interval.Preferences");
  }
  public boolean isEmpty() {
    boolean hasIntervals = preferences.getBoolean("HasIntervals");
    System.out.println("Has intervals? " + hasIntervals);
    return !hasIntervals;
  }

  private String getNameString(int i) { return "IntervalName" + i; }
  private String getSetsString(int i) { return "IntervalSets" + i; }
  private String getHighString(int i) { return "IntervalHigh" + i; }
  private String getLowString(int i) { return "IntervalLow" + i; }
  private String getWarmupString(int i) { return "IntervalWarmup" + i;}
  private String getCooldownString(int i) { return "IntervalCooldown" + i; }
  private String getSfxString(int i) { return "IntervalSfx" + i; }

  public ArrayList<IntervalData> getSavedIntervals() {
    ArrayList<IntervalData> datas = new ArrayList<>();
    for (int i = 0; i < 20; i += 1) {
      String name = preferences.getString(getNameString(i));
      if (name.isEmpty()) {
        continue;
      }
      System.out.println("Loading interval " + name + " " + i);
      int sets = preferences.getInteger(getSetsString(i));
      int high = preferences.getInteger(getHighString(i));
      int low = preferences.getInteger(getLowString(i));
      int cooldown = preferences.getInteger(getCooldownString(i));
      int warmup = preferences.getInteger(getWarmupString(i));
      String sfx = preferences.getString(getSfxString(i));
      IntervalData data = new IntervalData(name, sets, high, low, warmup, cooldown, sfx);
      datas.add(data);

    }
    return datas;
  }

  public void saveIntervalsArray(Array<IntervalData> datas) {
    ArrayList<IntervalData> al = new ArrayList<>();
    for( IntervalData id : datas) {
      al.add(id);
    }
    saveIntervals(al);
  }

  public void saveIntervals(ArrayList<IntervalData> datas) {
    System.out.println("Clearing save data");
    preferences.clear();
    preferences.putBoolean("HasIntervals", true);
    int i = 0;
    for (IntervalData data : datas) {
      System.out.println("Saving interval " + i + " " + data.toString());
      preferences.putString(getNameString(i), data.name);
      preferences.putInteger(getSetsString(i), data.sets);
      preferences.putInteger(getHighString(i), data.high);
      preferences.putInteger(getLowString(i), data.low);
      preferences.putInteger(getCooldownString(i), data.cooldown);
      preferences.putInteger(getWarmupString(i), data.warmup);
      preferences.putString(getSfxString(i), data.sfx);
      i += 1;
    }
    preferences.flush();
  }
}

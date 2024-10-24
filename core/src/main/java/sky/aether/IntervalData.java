package sky.aether;

public class IntervalData {
  public final String name;
  public final int sets;
  public final int high;
  public final int low;
  public final int cooldown;
  public final int warmup;
  public final String sfx;
  public IntervalData(String name,
                      int sets,
                      int high,
                      int low,
                      int warmup,
                      int cooldown,
                      String sfx) {
    this.name = name;
    this.sets = sets;
    this.low = low;
    this.high = high;
    this.warmup = warmup;
    this.cooldown = cooldown;
    this.sfx = sfx;
  }
  public int getTotalSeconds() {
    return sets*high+sets*low+warmup+cooldown;
  }
  public static IntervalData Default = new IntervalData("Default", 2, 90, 90, 12, 0, "chirp");
  @Override
  public String toString() {
    return name + ": " + sets + " sets (" + high + "/" + low + ") with " + warmup + "s warmup and " + cooldown + "s cooldown";
    //gwt doesn't support
    //return String.format("%s: %d sets (%d/%d) with %ds warmup and %ds cooldown", name, sets, high, low, warmup, cooldown);
  }

}

package sky.aether;

import java.util.function.Consumer;

enum IntervalStage {
  Warmup("Warmup"),
  HighIntensity("High Intensity"),
  LowIntensity("Low Intensity"),
  Cooldown("Cooldown"),
  Finished("End of Timer");
  private final String label;
  IntervalStage(String label) { this.label = label; }

  @Override
  public String toString() { return this.label; }
}

public class IntervalTimer {
  private final IntervalData data;
  private boolean paused = true;
  private double elapsed = 0d;
  private double totalElapsed = 0d;
  private double totalTimeLeft = 0d;
  private int stagePtr = 0;

  private Pair<IntervalStage,Float>[] stages;
  private final Consumer<String> setIntervalLabel;
  private final Consumer<String> setStageLabel;
  public IntervalTimer(IntervalData data,
                       Consumer<String> setIntervalLabel,
                       Consumer<String> setStageLabel) {
    this.data = data;
    this.setIntervalLabel = setIntervalLabel;
    this.setStageLabel = setStageLabel;
    this.totalTimeLeft = data.getTotalSeconds();
    int numStages = data.sets*2 + 3;
    stages = new Pair[numStages];
    stages[0] = makeStagePair(IntervalStage.Warmup, data.warmup);
    for (int i = 0; i < data.sets; i += 1) {
      int index = 2*i + 1;
      stages[index] = makeStagePair(IntervalStage.HighIntensity, data.high);
      stages[index + 1] = makeStagePair(IntervalStage.LowIntensity, data.low);
    }
    stages[data.sets*2 + 1] = makeStagePair(IntervalStage.Cooldown, data.cooldown);
    stages[data.sets*2 + 2] = new Pair<>(IntervalStage.Finished, 0f);
  }

  //add 0.99s so the first second lasts longer on the timer
  private Pair<IntervalStage,Float> makeStagePair(IntervalStage stage, int seconds) {
    return new Pair<>(stage, seconds + 0f);
  }

  public void update(float delta) {
    setIntervalLabel.accept((Math.min(stagePtr + 1, stages.length-1)) + "/" + (stages.length-1));
    if (paused) return;
    totalElapsed += delta;
    if (stagePtr == stages.length - 1) return;
    elapsed += delta;
    totalTimeLeft -= delta;
    Pair<IntervalStage,Float> stage = stages[stagePtr];
    setStageLabel.accept(stage.first.toString());
    if (elapsed > stage.second) {
      elapsed = 0;
      if (stage.second > 0) {
        playSfx();
      } else {
        System.out.println("skipping sfx");
      }
      stagePtr += 1;

    }
    stagePtr = Math.min(stagePtr, stages.length-1);
  }

  public double getStageTimeLeft() {
    Pair<IntervalStage,Float> stage = stages[stagePtr];
    //System.out.printf("%d, %d\n", stagePtr, stages.length);
    return stage.second - elapsed;
  }

  public double getTotalElapsed() {
    return totalElapsed;
  }

  public double getTotalTimeLeft() {
    return totalTimeLeft;
  }

  public boolean isFinished() {
    return stagePtr == stages.length - 1;
  }

  public void pause() {
    paused = true;
  }

  private void playSfx() {
    SoundManager.play(data.sfx);
  }

  public boolean isPlaying() { return !paused; }

  public void play() {
    if (isFinished()) {
      reset();
    }
    paused = false;
    playSfx();
  }

  public void reset() {
    elapsed = 0;
    totalElapsed = 0;
    totalTimeLeft = data.getTotalSeconds();
    stagePtr = 0;
    paused = true;
  }
}

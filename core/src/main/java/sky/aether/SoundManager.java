package sky.aether;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;



public class SoundManager {
  static Map<String, Sound> soundEffects;
  static final String defaultSfx = "chirp";
  static final String alarmSfx = "alarm";
  static final String trillSfx = "trill";
  static final String softbeepSfx = "softbeep";
  static void init() {
    soundEffects = new HashMap<>();
    soundEffects.put(defaultSfx, load("698626__fudgealtoid__morning-bird-chirping-2.mp3"));
    soundEffects.put(alarmSfx, load("324415__univ_lyon3__vandierdonck_joan_2015_2016_alarm_beep.mp3"));
    soundEffects.put(softbeepSfx, load("33782__jobro__3-beep-b.mp3"));
    soundEffects.put(trillSfx, load("103588__steveygos93__beeps3.mp3"));
  }

  static Sound load(String fname) {
    return Gdx.audio.newSound(Gdx.files.internal(fname));
  }

  static void play(String name) {
    System.out.println("Playing " + name);
    Sound sfx = soundEffects.get(name);
    if (sfx != null) {
      sfx.play();
    } else {
      soundEffects.get(defaultSfx).play();
    }
  }
}

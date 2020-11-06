package ic20b106.game.manager;

import ic20b106.Options;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Stack;

public class AudioManager {

    private AudioManager() {
        backgroundMediaPlayer = new MediaPlayer(new Media(
          getClass().getResource("/sounds/music/Jeremy_Blake-Powerup!.mp3").toString()));
        backgroundMediaPlayer.setVolume(Options.musicVolume);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public static AudioManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new AudioManager();
        }
        return singleInstance;
    }

    public void playClip(AudioClip audioClip) {
        clips.push(audioClip);

        if (clipThread == null) {
            final Task<?> clipTask = new Task<>() {
                @Override
                protected Object call() {
                    while (!clips.empty()) {
                        clips.pop().play(0.5);
                    }
                    clipThread = null;
                    return null;
                }
            };

            this.clipThread = new Thread(clipTask);
            clipThread.start();
        }
    }

    public MediaPlayer getBackgroundMediaPlayer() {
        return backgroundMediaPlayer;
    }

    private static AudioManager singleInstance;
    public static AudioClip TEST;
    private final MediaPlayer backgroundMediaPlayer;
    private Thread clipThread;
    private final Stack<AudioClip> clips = new Stack<>();
}

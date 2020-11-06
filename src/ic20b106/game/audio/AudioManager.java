package ic20b106.game.audio;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Stack;

public class AudioManager {

    private AudioManager() {
        backgroundMediaPlayer = new MediaPlayer(new Media(
          getClass().getResource("/sounds/music/Jeremy_Blake-Powerup!.mp3").toString()));
        backgroundMediaPlayer.setVolume(0.5);
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public static AudioManager getInstance() {
        if (single_instance == null) {
            single_instance = new AudioManager();
        }
        return single_instance;
    }

    public void playBackgroundMusic() {
        if (backgroundMediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            backgroundMediaPlayer.play();
        }
    }

    public void playClip(AudioClip audioClip) {
        synchronized (clips) {
            clips.push(audioClip);
        }
        if (clipThread == null) {
            final Task<?> clipTask = new Task<>() {
                @Override
                protected Object call() {
                    synchronized (clips) {
                        while (!clips.empty()) {
                            clips.pop().play(0.5);
                        }
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

    private static AudioManager single_instance;
    public static AudioClip TEST;
    private final MediaPlayer backgroundMediaPlayer;
    private Thread clipThread;
    private final Stack<AudioClip> clips = new Stack<>();
}

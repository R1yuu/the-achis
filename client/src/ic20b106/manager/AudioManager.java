package ic20b106.manager;

import ic20b106.Options;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Stack;

public class AudioManager {

    private AudioManager() {
        backgroundMediaPlayer = new MediaPlayer(new Media(
          getClass().getResource("/sounds/music/Jeremy_Blake-Powerup!.mp3").toString()));
        backgroundMediaPlayer.setVolume(Options.getMusicVolume());
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        Thread clipThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (clips) {
                        clips.wait();
                        if (Options.getSfxEnabled()) {
                            clips.pop().play((double) Options.getSfxVolume() / 100);
                        } else {
                            clips.pop();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        clipThread.start();
    }

    public static AudioManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new AudioManager();
        }
        return singleInstance;
    }

    public void playClip(AudioClip audioClip) {
        synchronized (clips) {
            clips.push(audioClip);
            clips.notify();
        }
    }

    public MediaPlayer getBackgroundMediaPlayer() {
        return backgroundMediaPlayer;
    }

    private static AudioManager singleInstance;
    public static AudioClip BUTTON_CLICK = new AudioClip(AudioManager.class.getResource("/sounds/sfx/button-click.mp3").toString());

    private final MediaPlayer backgroundMediaPlayer;
    private final Stack<AudioClip> clips = new Stack<>();
}

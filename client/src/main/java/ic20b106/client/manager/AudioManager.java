package ic20b106.client.manager;

import ic20b106.client.Options;
import ic20b106.client.util.audio.BigClip;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Stack;

public class AudioManager {

    private final BigClip backgroundBigClip;
    private final Stack<Clip> clips = new Stack<>();
    private static AudioManager singleInstance;
    //public static AudioClip BUTTON_CLICK = new AudioClip(AudioManager.class.getResource("/sounds/sfx/button-click.mp3").toString());
    public static AudioInputStream BUTTON_CLICK;

    static {
        try {
            BUTTON_CLICK = AudioSystem.getAudioInputStream(AudioManager.class.getResource("/sounds/sfx/button-click.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }


    private AudioManager() {
        /*
        backgroundMediaPlayer = new MediaPlayer(new Media(
          getClass().getResource("/sounds/music/Jeremy_Blake-Powerup!.mp3").toString()));

        backgroundMediaPlayer.setVolume(Options.getMusicVolume());
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        */
        this.backgroundBigClip = new BigClip();

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
              getClass().getResource("/sounds/music/Jeremy_Blake-Powerup!.wav"));
            this.backgroundBigClip.open(inputStream);
            this.backgroundBigClip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }




        Thread clipThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (clips) {
                        clips.wait();
                        Clip clip = clips.pop();
                        if (Options.getSfxEnabled()) {
                            clip.setFramePosition(0);
                            clip.start();
                            //clips.pop().play((double) Options.getSfxVolume() / 100);
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

    public void playClip(AudioInputStream inputStream) {
        synchronized (clips) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clips.push(clip);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            //clips.push(audioClip);
            clips.notify();
        }
    }

    public BigClip getBackgroundBigClip() {
        return backgroundBigClip;
    }
}

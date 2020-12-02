package ic20b106.client.manager;

import ic20b106.client.Options;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Stack;

public class AudioManager {

    private final Clip backgroundBigClip;
    private final Stack<Clip> clips = new Stack<>();
    private static AudioManager singleInstance;
    public static AudioInputStream BUTTON_CLICK;

    static {
        try {
            BUTTON_CLICK = AudioSystem.getAudioInputStream(AudioManager.class.getResource("/sounds/sfx/button-click.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }


    private AudioManager() throws LineUnavailableException {

        this.backgroundBigClip = AudioSystem.getClip();

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
            try {
                singleInstance = new AudioManager();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
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
            clips.notify();
        }
    }

    public Clip getBackgroundBigClip() {
        return backgroundBigClip;
    }
}

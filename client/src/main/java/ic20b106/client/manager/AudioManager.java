package ic20b106.client.manager;

import ic20b106.client.Options;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Stack;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Manages all Audio/Sound related Features
 */
public class AudioManager {

    private final Clip backgroundClip;
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

    /**
     * Constructor
     *
     * @throws LineUnavailableException Thrown when the Audio File couldn't be found
     */
    private AudioManager() throws LineUnavailableException {

        this.backgroundClip = AudioSystem.getClip();

        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
              getClass().getResource("/sounds/music/Jeremy_Blake-Powerup!.wav"));
            this.backgroundClip.open(inputStream);
            this.backgroundClip.start();
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

    /**
     * Singleton Instanciation
     *
     * @return Single Instance of AudioManger
     */
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

    /**
     * Plays a given AudioClip
     *
     * @param inputStream InputStream of the AudioClip
     */
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

    /**
     * Getter
     *
     * @return Running BackgroundClip
     */
    public Clip getBackgroundClip() {
        return backgroundClip;
    }
}

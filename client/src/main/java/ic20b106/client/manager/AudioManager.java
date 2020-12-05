package ic20b106.client.manager;

import ic20b106.client.Options;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Manages all Audio/Sound related Features
 */
public class AudioManager implements Closeable {

    private Clip backgroundClip = null;
    private final Queue<Clip> clips = new LinkedList<>();
    private static AudioManager singleInstance;
    private static final String sfxPath = "/sounds/sfx";
    private static final String musicPath = "/sounds/music";
    public static final URL MUSIC_1 =
      AudioManager.class.getResource(musicPath + "/Jeremy_Blake-Powerup!.wav");
    public static final URL BUTTON_CLICK =
      AudioManager.class.getResource(sfxPath + "/button-click.wav");

    /**
     * Constructor
     */
    private AudioManager() {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(MUSIC_1);
            this.backgroundClip = AudioSystem.getClip();
            this.backgroundClip.open(inputStream);
            this.backgroundClip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        Thread clipThread = new Thread(this::clipListener);
        clipThread.start();
    }

    /**
     * Threaded Method.
     * Waits for Clips to be pushed to the Queue
     */
    private void clipListener() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (clips) {
                    clips.wait();
                    Clip clip = clips.poll();
                    if (Options.getSfxEnabled() && clip != null) {
                        clip.addLineListener(event -> {
                            if (event.getType() == LineEvent.Type.STOP) {
                                event.getLine().close();
                            }
                        });
                        clip.setFramePosition(0);
                        clip.start();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Singleton Instanciation
     *
     * @return Single Instance of AudioManger
     */
    public static AudioManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new AudioManager();
        }
        return singleInstance;
    }

    /**
     * Closes the Audio Manager and sets its Memory to be free
     */
    public void close() {
        if (singleInstance != null) {
            singleInstance = null;
        }
    }

    /**
     * Plays a given AudioClip
     *
     * @param audioResourceUrl Resource URL of Audio Clip
     */
    public void playClip(URL audioResourceUrl) {
        synchronized (clips) {
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioResourceUrl);
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clips.offer(clip);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
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

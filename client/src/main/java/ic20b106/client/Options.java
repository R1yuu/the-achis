package ic20b106.client;

import ic20b106.client.manager.AudioManager;
import ic20b106.client.manager.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Options implements Initializable, Serializable {

    @FXML
    private CheckBox musicEnabledCheckBox;
    @FXML
    private Slider musicVolumeSlider;
    @FXML
    private Label musicVolumeLabel;
    @FXML
    private CheckBox sfxEnabledCheckBox;
    @FXML
    private Slider sfxVolumeSlider;
    @FXML
    private Label sfxVolumeLabel;
    private static final AtomicBoolean musicEnabled = new AtomicBoolean(true);
    private static final AtomicInteger musicVolume = new AtomicInteger(100);
    private static final AtomicBoolean sfxEnabled = new AtomicBoolean(true);
    private static final AtomicInteger sfxVolume = new AtomicInteger(100);

    /**
     * FXML initialize Method
     *
     * @param location FXML file location
     * @param resources FXML Node Resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicEnabledCheckBox.setSelected(Options.musicEnabled.get());
        synchronized (Options.musicVolume) {
            musicVolumeSlider.setValue(Options.musicVolume.get());
            musicVolumeLabel.setText(Options.musicVolume.get() + "%");
        }


        sfxEnabledCheckBox.setSelected(Options.sfxEnabled.get());
        synchronized (Options.sfxVolume) {
            sfxVolumeSlider.setValue(Options.sfxVolume.get());
            sfxVolumeLabel.setText(Options.sfxVolume.get() + "%");
        }

        musicEnabledCheckBox.selectedProperty().addListener((obs, oldval, newVal) -> Options.setMusicEnabled(newVal));
        sfxEnabledCheckBox.selectedProperty().addListener((obs, oldval, newVal) -> Options.setSfxEnabled(newVal));

        musicVolumeSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            musicVolumeSlider.setValue(newVal.intValue());
            musicVolumeLabel.setText(newVal.intValue() + "%");
            Options.setMusicVolume(newVal.intValue());
        });

        sfxVolumeSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            sfxVolumeSlider.setValue(newVal.intValue());
            sfxVolumeLabel.setText(newVal.intValue() + "%");
            Options.setSfxVolume(newVal.intValue());
        });
    }

    /**
     * Setter
     *
     * @param musicEnabled Boolean to set Music to on/off
     */
    public static void setMusicEnabled(boolean musicEnabled) {
        Options.musicEnabled.set(musicEnabled);
        if (musicEnabled) {
            AudioManager.getInstance().getBackgroundClip().start();
        } else {
            AudioManager.getInstance().getBackgroundClip().stop();
        }
    }

    /**
     * Getter
     *
     * @return If Music on/off
     */
    public static boolean getMusicEnabled() {
        return Options.musicEnabled.get();
    }

    /**
     * Setter
     *
     * @param musicVolume Music Volume (in Percent)
     */
    public static void setMusicVolume(int musicVolume) {
        if (musicVolume >= 0 && musicVolume <= 100) {
            Options.musicVolume.set(musicVolume);

            FloatControl gainControl = (FloatControl) AudioManager.getInstance().getBackgroundClip()
              .getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10((float) (musicVolume) / 100));
        }
    }

    /**
     * Getter
     *
     * @return Music Volume (in Percent)
     */
    public static int getMusicVolume() {
        return Options.musicVolume.get();
    }

    /**
     * Setter
     *
     * @param sfxEnabled Sets if Sound Effects are on/off
     */
    public static void setSfxEnabled(boolean sfxEnabled) {
        Options.sfxEnabled.set(sfxEnabled);
    }

    /**
     * Getter
     *
     * @return If Sound Effects are on/off
     */
    public static boolean getSfxEnabled() {
        return Options.sfxEnabled.get();
    }

    /**
     * Setter
     *
     * @param sfxVolume SFX Volume (in Percent)
     */
    public static void setSfxVolume(int sfxVolume) {
        if (sfxVolume >= 0 && sfxVolume <= 100) {
            Options.sfxVolume.set(sfxVolume);
        }
    }

    /**
     * Getter
     *
     * @return SFX Volume (in Percent)
     */
    public static int getSfxVolume() {
        return Options.sfxVolume.get();
    }

    /**
     * Back Button to Main Menu
     */
    @FXML
    private void backToMainMenu() {
        FileManager.getInstance().readOptions();
        Game.resetGame();
    }

    /**
     * Save Options Button Event
     */
    @FXML
    private void saveOptions() {
        FileManager.getInstance().writeOptions();
        Game.resetGame();
    }
}

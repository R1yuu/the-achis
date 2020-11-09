package ic20b106;

import ic20b106.game.manager.AudioManager;
import ic20b106.game.manager.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Options implements Initializable, Serializable {

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

    public static void setMusicEnabled(boolean musicEnabled) {
        Options.musicEnabled.set(musicEnabled);
        if (musicEnabled) {
            AudioManager.getInstance().getBackgroundMediaPlayer().play();
        } else {
            AudioManager.getInstance().getBackgroundMediaPlayer().stop();
        }
    }

    public static boolean getMusicEnabled() {
        return Options.musicEnabled.get();
    }

    public static void setMusicVolume(int musicVolume) {
        if (musicVolume >= 0 && musicVolume <= 100) {
            Options.musicVolume.set(musicVolume);
            AudioManager.getInstance().getBackgroundMediaPlayer().setVolume(musicVolume);
        }
    }

    public static int getMusicVolume() {
        return Options.musicVolume.get();
    }

    public static void setSfxEnabled(boolean sfxEnabled) {
        Options.sfxEnabled.set(sfxEnabled);
    }

    public static boolean getSfxEnabled() {
        return Options.sfxEnabled.get();
    }

    public static void setSfxVolume(int sfxVolume) {
        if (sfxVolume >= 0 && sfxVolume <= 100) {
            Options.sfxVolume.set(sfxVolume);
        }
    }

    public static int getSfxVolume() {
        return Options.sfxVolume.get();
    }

    @FXML
    private void backToMainMenu() {
        FileManager.getInstance().readOptions();
        Game.resetGame();
    }

    @FXML
    private void saveOptions() {
        FileManager.getInstance().writeOptions();
        Game.resetGame();
    }

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
}

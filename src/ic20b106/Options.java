package ic20b106;

import ic20b106.game.manager.AudioManager;
import ic20b106.game.manager.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Options implements Initializable, Serializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicEnabledCheckBox.setSelected(musicEnabled);
        musicVolumeSlider.setValue(musicVolume * 100);
        musicVolumeLabel.setText((int)(musicVolume * 100) + "%");

        musicEnabledCheckBox.selectedProperty().addListener((obs, oldval, newVal) -> Options.setMusicEnabled(newVal));

        musicVolumeSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            musicVolumeSlider.setValue(newVal.intValue());
            musicVolumeLabel.setText(newVal.intValue() + "%");
            Options.setMusicVolume(Math.floor(newVal.doubleValue()) / 100);
        });
    }

    public static void setMusicEnabled(boolean musicEnabled) {
        Options.musicEnabled = musicEnabled;
        if (musicEnabled) {
            AudioManager.getInstance().getBackgroundMediaPlayer().play();
        } else {
            AudioManager.getInstance().getBackgroundMediaPlayer().stop();
        }
    }

    public static boolean getMusicEnabled() {
        return Options.musicEnabled;
    }

    public static void setMusicVolume(double musicVolume) {
        if (musicVolume >= 0 || musicVolume <= 1) {
            Options.musicVolume = musicVolume;
            AudioManager.getInstance().getBackgroundMediaPlayer().setVolume(Options.musicVolume);
        }
    }

    public static double getMusicVolume() {
        return Options.musicVolume;
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

    public static Boolean musicEnabled = true;
    public static double musicVolume = 1;
    public static boolean sfxEnabled = true;
    public static double sfxVolume = 1;
}

package ic20b106.client;

import ic20b106.client.manager.AudioManager;
import ic20b106.client.manager.FileManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javax.sound.sampled.FloatControl;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

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

    private static boolean musicEnabled = true;
    private static int musicVolume = 100;
    private static boolean sfxEnabled = true;
    private static int sfxVolume = 100;
    private static String host = "achirealm.com";
    private static int socketPort = 2910;
    private static int rmiPort = 1509;

    /**
     * FXML initialize Method
     *
     * @param location FXML file location
     * @param resources FXML Node Resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        musicEnabledCheckBox.setSelected(Options.getMusicEnabled());
        int musicVolume = Options.getMusicVolume();
        musicVolumeSlider.setValue(musicVolume);
        musicVolumeLabel.setText(musicVolume + "%");

        sfxEnabledCheckBox.setSelected(Options.getSfxEnabled());
        int sfxVolume = Options.getSfxVolume();
        sfxVolumeSlider.setValue(sfxVolume);
        sfxVolumeLabel.setText(sfxVolume + "%");

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
        synchronized (Options.class) {
            Options.musicEnabled = musicEnabled;
        }
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
        synchronized (Options.class) {
            return Options.musicEnabled;
        }
    }

    /**
     * Setter
     *
     * @param musicVolume Music Volume (in Percent)
     */
    public static void setMusicVolume(int musicVolume) {
        if (musicVolume >= 0 && musicVolume <= 100) {
            synchronized (Options.class) {
                Options.musicVolume = musicVolume;
            }

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
        synchronized (Options.class) {
            return Options.musicVolume;
        }
    }

    /**
     * Setter
     *
     * @param sfxEnabled Sets if Sound Effects are on/off
     */
    public static void setSfxEnabled(boolean sfxEnabled) {
        synchronized (Options.class) {
            Options.sfxEnabled = sfxEnabled;
        }
    }

    /**
     * Getter
     *
     * @return If Sound Effects are on/off
     */
    public static boolean getSfxEnabled() {
        synchronized (Options.class) {
            return Options.sfxEnabled;
        }
    }

    /**
     * Setter
     *
     * @param sfxVolume SFX Volume (in Percent)
     */
    public static void setSfxVolume(int sfxVolume) {
        if (sfxVolume >= 0 && sfxVolume <= 100) {
            synchronized (Options.class) {
                Options.sfxVolume = sfxVolume;
            }
        }
    }

    /**
     * Getter
     *
     * @return SFX Volume (in Percent)
     */
    public static int getSfxVolume() {
        synchronized (Options.class) {
            return Options.sfxVolume;
        }
    }

    /**
     * Setter
     *
     * @param socketPort Port for Socket Connection Handshake
     */
    public static void setSocketPort(int socketPort) {
        synchronized (Options.class) {
            Options.socketPort = socketPort;
        }
    }

    /**
     * Getter
     *
     * @return Port for Socket Connection Handshake
     */
    public static int getSocketPort() {
        synchronized (Options.class) {
            return Options.socketPort;
        }
    }

    /**
     * Setter
     *
     * @param host Host Addresse to connect to
     */
    public static void setHost(String host) {
        synchronized (Options.class) {
            Options.host = host;
        }
    }

    /**
     * Getter
     *
     * @return Host Addresse to connect to
     */
    public static String getHost() {
        synchronized (Options.class) {
            return Options.host;
        }
    }

    /**
     * Setter
     *
     * @param rmiPort Port for RMI Connection
     */
    public static void setRmiPort(int rmiPort) {
        synchronized (Options.class) {
            Options.rmiPort = rmiPort;
        }
    }

    /**
     * Getter
     *
     * @return Port for RMI Connection
     */
    public static int getRmiPort() {
        synchronized (Options.class) {
            return Options.rmiPort;
        }
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

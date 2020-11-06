package ic20b106.game.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ic20b106.Options;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class FileManager {

    static class OptionsDump {

        OptionsDump() {}

        void mapToOptions() {
            Options.setMusicEnabled(this.musicEnabled);
            Options.setMusicVolume(this.musicVolume);
        }

        final boolean musicEnabled = Options.musicEnabled;
        final double musicVolume = Options.musicVolume;
    }

    private FileManager() {}

    public static FileManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new FileManager();
        }
        return singleInstance;
    }

    public void writeOptions() {
        try {
            File optionsFile = new File(getClass().getResource("/options.json").toURI());
            OptionsDump optionsDump = new OptionsDump();
            if (optionsFile.exists()) {
                FileWriter optionsFileWriter = new FileWriter(optionsFile);
                optionsFileWriter.write(this.gson.toJson(optionsDump));
                optionsFileWriter.close();
            } else {
                throw new IOException("\"/options.json\" doesn't exists. Maybe deleted?");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void readOptions() {
        try {
            File optionsFile = new File(getClass().getResource("/options.json").toURI());

            if (optionsFile.exists()) {
                FileReader optionsFileScanner = new FileReader(optionsFile);
                gson.fromJson(optionsFileScanner, OptionsDump.class).mapToOptions();
            } else {
                throw new IOException("\"/options.json\" doesn't exists. Maybe deleted?");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static FileManager singleInstance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
}

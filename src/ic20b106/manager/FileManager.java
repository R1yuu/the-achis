package ic20b106.manager;

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
            Options.setSfxEnabled(this.sfxEnabled);
            Options.setSfxVolume(this.sfxVolume);
        }

        final boolean musicEnabled = Options.getMusicEnabled();
        final int musicVolume = Options.getMusicVolume();
        final boolean sfxEnabled = Options.getSfxEnabled();
        final int sfxVolume = Options.getSfxVolume();
    }

    private FileManager() {
        try {
            this.optionsFile = new File(getClass().getResource("/options.json").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static FileManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new FileManager();
        }
        return singleInstance;
    }

    public void writeOptions() {
        try {
            this.optionsFile.createNewFile();
            OptionsDump optionsDump = new OptionsDump();
            FileWriter optionsFileWriter = new FileWriter(this.optionsFile);
            optionsFileWriter.write(this.gson.toJson(optionsDump));
            optionsFileWriter.close();
            //} else {
            //    throw new IOException("\"/options.json\" doesn't exists. Maybe deleted?");
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readOptions() {
        try {
            if (this.optionsFile.exists()) {
                FileReader optionsFileReader = new FileReader(this.optionsFile);
                OptionsDump optionsDump = gson.fromJson(optionsFileReader, OptionsDump.class);
                if (optionsDump == null) {
                    optionsDump = new OptionsDump();
                }
                optionsDump.mapToOptions();

                optionsFileReader.close();
            } else {
                throw new IOException("\"/options.json\" doesn't exists. Maybe deleted?");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileManager singleInstance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File optionsFile = null;
}

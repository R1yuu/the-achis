package ic20b106.client.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ic20b106.client.Options;
import ic20b106.client.util.ByteUtils;
import javafx.scene.image.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        this.optionsFile = new File(System.getProperty("user.home").replace("\\", "/") +
          "/.the_achis/options.json");
        this.hashidFile = new File(System.getProperty("user.home").replace("\\", "/") +
          "/.the_achis/account.achiid");

        File externalSafeFolder = this.optionsFile.getParentFile();
        if (!externalSafeFolder.exists() && !externalSafeFolder.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + externalSafeFolder);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readOptions() {
        try {
            if (!this.optionsFile.createNewFile()) {
                FileReader optionsFileReader = new FileReader(this.optionsFile);
                OptionsDump optionsDump = gson.fromJson(optionsFileReader, OptionsDump.class);
                if (optionsDump == null) {
                    optionsDump = new OptionsDump();
                }
                optionsDump.mapToOptions();
                optionsFileReader.close();
            } else {
                writeOptions();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readHashid() {
        String hashid = "";
        try {
            if (this.hashidFile.exists()) {
                FileInputStream inputStream = new FileInputStream(this.hashidFile);
                byte[] hashidBytes = inputStream.readAllBytes();

                hashid = new String(hashidBytes, StandardCharsets.UTF_8);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashid;
    }

    public void writeHashid(String hashid) {
        try {
            this.hashidFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(this.hashidFile);
            outputStream.write(hashid.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileManager singleInstance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File optionsFile;
    // TODO: Hash in OptionsFile
    private final File hashidFile;
}

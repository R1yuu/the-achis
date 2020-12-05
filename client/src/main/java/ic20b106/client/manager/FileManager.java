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
import java.io.Closeable;
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
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Manages all File/Save related Features
 */
public class FileManager implements Closeable {

    private static FileManager singleInstance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String savePath = System.getProperty("user.home") + "/.the_achis";
    private static final String optionsFilePath = savePath + "/options.json";
    private static final String hashidFilePath = savePath + "/account.achiid";

    /**
     * Class used by Gson to Map the Json File too
     */
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

    /**
     * Constructor
     */
    private FileManager() {
        File safeFolder = new File(savePath);
        if (!safeFolder.exists() && !safeFolder.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + safeFolder);
        }
        File optionsFile = new File(optionsFilePath);
        File hashidFile = new File(hashidFilePath);
        try {
            if (optionsFile.createNewFile()) {
                System.out.println("File Created");
                this.writeOptions();
            } else {
                System.out.println("File exists");
            }
            if (hashidFile.createNewFile()) {
                System.out.println("File Created");
            } else {
                System.out.println("File exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Singleton Instanciation
     *
     * @return Single Instance of FileManager
     */
    public static FileManager getInstance() {
        if (singleInstance == null) {
            singleInstance = new FileManager();
        }
        return singleInstance;
    }

    /**
     * Closes the File Manager and sets its Memory to be free
     */
    public void close() {
        if (singleInstance != null) {
            singleInstance = null;
        }
    }

    /**
     * Writes Options to the options File
     */
    public void writeOptions() {
        try (FileWriter fileWriter = new FileWriter(optionsFilePath)) {
            OptionsDump optionsDump = new OptionsDump();
            fileWriter.write(this.gson.toJson(optionsDump));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads Options from the options File
     */
    public void readOptions() {
        try (FileReader fileReader = new FileReader(optionsFilePath)) {
            OptionsDump optionsDump;
            if ((optionsDump = gson.fromJson(fileReader, OptionsDump.class)) == null) {
                optionsDump = new OptionsDump();
            }
            optionsDump.mapToOptions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the Players Hashid from the account.achiid file
     *
     * @return Hashid String
     */
    public String readHashid() {
        String hashid = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(hashidFilePath))) {
            hashid = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashid;
    }

    /**
     * Writes Players Hashid to the account.achiid file
     *
     * @param hashid Player Hashid to write to the file
     */
    public void writeHashid(String hashid) {
        try (FileWriter fileWriter = new FileWriter(hashidFilePath)) {
            fileWriter.write(hashid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

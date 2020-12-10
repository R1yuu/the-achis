package ic20b106.client.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ic20b106.client.Options;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Manages all File/Save related Features
 */
public class FileManager implements Closeable {

    private static FileManager singleInstance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String savePath =
      System.getProperty("user.home").replace("\\", "/") + "/.the_achis";
    private static final String optionsFilePath = savePath + "/options.json";
    private static final String hashidFilePath = savePath + "/account.achiid";
    private static final Logger fileMngLogger = Logger.getLogger(FileManager.class.getName());

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
            Options.setHost(this.host);
            Options.setSocketPort(this.socketPort);
            Options.setRmiPort(this.rmiPort);
        }

        final boolean musicEnabled = Options.getMusicEnabled();
        final int musicVolume = Options.getMusicVolume();
        final boolean sfxEnabled = Options.getSfxEnabled();
        final int sfxVolume = Options.getSfxVolume();
        final String host = Options.getHost();
        final int socketPort = Options.getSocketPort();
        final int rmiPort = Options.getRmiPort();
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
                fileMngLogger.info("'" + optionsFilePath + "' created.");
                this.writeOptions();
            } else {
                fileMngLogger.info("'" + optionsFilePath + "' already exists.");
            }
            if (hashidFile.createNewFile()) {
                fileMngLogger.info("'" + hashidFilePath + "' created.");
            } else {
                fileMngLogger.info("'" + hashidFilePath + "' already exists.");
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
            Logger.getGlobal().info("Creating FileManager.");
            singleInstance = new FileManager();
        }
        return singleInstance;
    }

    /**
     * Closes the File Manager and sets its Memory to be free
     */
    public void close() {
        fileMngLogger.info("Closing FileManager.");
        if (singleInstance != null) {
            singleInstance = null;
        }
    }

    /**
     * Writes Options to the options File
     */
    public void writeOptions() {
        fileMngLogger.info("Writing Options.");
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

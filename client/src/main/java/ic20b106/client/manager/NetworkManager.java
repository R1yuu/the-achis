package ic20b106.client.manager;

import ic20b106.client.Game;
import ic20b106.client.Lobby;
import ic20b106.client.exceptions.HashException;
import ic20b106.client.exceptions.HashTakenException;
import ic20b106.client.util.HashUtils;
import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.RemoteCommands;
import ic20b106.shared.NetworkConstants;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Manages all Network/RMI related Stuff related Features
 */
public class NetworkManager extends UnicastRemoteObject
  implements ClientCommands, Serializable, Closeable, AutoCloseable {

    private static NetworkManager singleInstance;
    public String playerHash;
    public RemoteCommands serverStub;
    private boolean serverInvocedClose = false;

    /**
     * Constructor
     *
     * @throws IOException Thrown is the Socket connection gets cut
     * @throws NotBoundException Thrown if the RMI Address has no Bound
     * @throws HashTakenException Thrown if the player hash is already taken on the server
     */
    private NetworkManager() throws IOException, NotBoundException, HashTakenException {

        String hash = FileManager.getInstance().readHashid();
        if (!HashUtils.checkHashValidity(hash)) {
            hash = HashUtils.generateHash();
            FileManager.getInstance().writeHashid(hash);
            System.out.println("Generated!");
        }

        this.playerHash = hash;


        Socket socket = new Socket(NetworkConstants.HOST, NetworkConstants.PORT);

        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream printer = new PrintStream(socket.getOutputStream(), true);
        printer.println(this.playerHash);

        if (!buffer.readLine().equals("OK")) {
            throw new HashTakenException();
        }

        if (!buffer.readLine().equals("OPEN")) {
            throw new ConnectException("Connection Error!");
        }

        buffer.close();
        socket.close();

        this.serverStub = (RemoteCommands)Naming.lookup(NetworkConstants.RMI_HOST + ":" +
          NetworkConstants.RMI_PORT + "/" + this.playerHash);



        this.serverStub.sendClientStub(this);
    }

    /**
     * Updates a Building on a Cell
     *
     * @param buildable Building
     */
    @Override
    public void updateBuilding(Buildable buildable) {

    }

    /**
     * Writes out a sent Message
     *
     * @param message Message to be Written
     */
    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
    }

    /**
     * Disconnects the Client from the Server
     */
    @Override
    public void disconnect() {
        serverInvocedClose = true;
        close();
    }

    /**
     * Singleton Instanciation
     *
     * @return Single Instance of NetworkManager
     */
    public static NetworkManager getInstance() {
        if (singleInstance == null) {
            Task<Void> connectTask = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        singleInstance = new NetworkManager();
                    } catch (ConnectException connectException) {
                        Platform.runLater(() -> {
                            Alert connectionErrorAlert = new Alert(Alert.AlertType.ERROR);
                            connectionErrorAlert.setTitle("Connection Error");
                            connectionErrorAlert.setHeaderText("Connection refused.");
                            connectionErrorAlert.setContentText("Check your Internet connection.");
                            connectionErrorAlert.showAndWait();
                        });
                    } catch (IOException | NotBoundException | HashException e) {
                        e.printStackTrace();
                    }
                    updateProgress(1, 1);
                    return null;
                }
            };
            connectTask.setOnSucceeded(workerStateEvent -> Game.primaryPane.getChildren().remove(Game.loadingBox));
            Game.openLoadingScreen(connectTask.progressProperty());
            new Thread(connectTask).start();
        }
        return singleInstance;
    }

    /**
     * Closes the Connection to the Server and removes the Instance
     */
    public void close() {
        if (singleInstance != null) {
            if (!serverInvocedClose) {
                try {
                    singleInstance.serverStub.quitRoom();
                } catch (RemoteException remoteException) {
                    //TODO: Handle If Server is gone
                    remoteException.printStackTrace();
                }
            }
            singleInstance = null;
        }
    }

    /**
     * Closes the Instance if there is one
     */
    public static void closeIfExists() {
        if (singleInstance != null) {
            singleInstance.close();
        }
    }

    /**
     * Updates the Colors in the Lobby Color Choose
     *
     * @param freeColor Color that got freed
     * @param takenColor Color that got chosen
     */
    @Override
    public void updateColors(PlayerColor freeColor, PlayerColor takenColor) {
        Lobby.colorComboBox.getItems().remove(takenColor.toString());
        Lobby.colorComboBox.getItems().add(freeColor.toString());
    }

    /**
     * Forces the Client to reload the Lobby Info from the Server
     */
    @Override
    public void updateLobby() {
        Lobby.updateTable();
    }
}

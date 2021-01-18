package ic20b106.client.manager;

import ic20b106.client.Game;
import ic20b106.client.Lobby;
import ic20b106.client.Options;
import ic20b106.client.exceptions.HashException;
import ic20b106.client.exceptions.HashTakenException;
import ic20b106.client.game.board.Cell;
import ic20b106.client.util.HashUtils;
import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.shared.RemoteCommands;
import ic20b106.shared.utils.IntPair;
import javafx.application.Platform;
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
import java.util.logging.Logger;

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
            Logger.getGlobal().severe("Hash invalid.");
            hash = HashUtils.generateHash();
            Logger.getGlobal().severe("Hash Generated.");
            FileManager.getInstance().writeHashid(hash);
            Logger.getGlobal().severe("Hash Generation finished.");
        }

        this.playerHash = hash;

        Socket socket = new Socket(Options.getHost(), Options.getSocketPort());

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

        this.serverStub = (RemoteCommands)Naming.lookup("rmi://" + Options.getHost() + ":" +
          Options.getRmiPort() + "/" + this.playerHash);



        this.serverStub.sendClientStub(this);
    }

    /**
     * Updates a Building on a Cell
     *
     * @param buildable Building
     */
    @Override
    public void updateBuilding(Buildable buildable) {
        IntPair buildingPosition = buildable.getPosition();
        Cell targetCell = Game.gameBoard.getCell(buildingPosition);
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
        Game.resetGame();
    }

    /**
     * Singleton Instanciation
     *
     * @return Single Instance of NetworkManager
     */
    public static NetworkManager getInstance() {
        if (singleInstance == null) {
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
                    Logger.getGlobal().info("Sending Quit Message.");
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
     * Forces the Client to reload the Lobby Info from the Server
     */
    @Override
    public void updateLobby() {
        Lobby.updateTable();
    }

    @Override
    public void startGame(PlayerColor playerColor, PlayerStartPosition playerStartPosition) {
        Lobby.loadGame(playerColor, playerStartPosition);
    }
}

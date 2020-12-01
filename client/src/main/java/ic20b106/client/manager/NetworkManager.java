package ic20b106.client.manager;

import ic20b106.client.Lobby;
import ic20b106.client.exceptions.HashException;
import ic20b106.client.exceptions.HashTakenException;
import ic20b106.client.util.HashUtils;
import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.RemoteCommands;
import ic20b106.shared.NetworkConstants;

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

public class NetworkManager extends UnicastRemoteObject
  implements ClientCommands, Serializable, Closeable, AutoCloseable {

    private static NetworkManager singleInstance;
    public String playerHash;
    public RemoteCommands serverStub;
    private boolean serverInvocedClose = false;

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

    @Override
    public void updateBuilding(Buildable buildable) {

    }

    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
    }

    @Override
    public void disconnect() {
        serverInvocedClose = true;
        close();
    }

    public static NetworkManager getInstance() {
        if (singleInstance == null) {
            try {
                singleInstance = new NetworkManager();
            } catch (IOException | NotBoundException | HashException e) {
                e.printStackTrace();
            }
        }
        return singleInstance;
    }

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

    @Override
    public void updateColors(PlayerColor freeColor, PlayerColor takenColor) {
        Lobby.colorComboBox.getItems().remove(takenColor.toString());
        Lobby.colorComboBox.getItems().add(freeColor.toString());
    }

    @Override
    public void updateLobby() {
        Lobby.updateTable();
    }
}

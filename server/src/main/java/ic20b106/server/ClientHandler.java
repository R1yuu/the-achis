package ic20b106.server;

import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.NetworkConstants;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.shared.RemoteCommands;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class ClientHandler extends UnicastRemoteObject implements RemoteCommands, Closeable, AutoCloseable {

    private final String playerHash;
    private final String playerId;
    public ClientCommands clientStub;
    private Room room;
    private PlayerColor playerColor;
    private PlayerStartPosition playerStartPosition;
    private Boolean isReady = false;

    public ClientHandler(Socket socket) throws IOException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream printer = new PrintStream(socket.getOutputStream(), true);
        this.playerHash = buffer.readLine();

        if (!GameServer.contains(this)) {
            printer.println("OK");
            GameServer.addClient(this);
        } else {
            printer.println("TAKEN");
        }

        Naming.rebind(GameServer.RMI_HOST + ":" + GameServer.RMI_PORT + "/" + this.playerHash,
          this);

        printer.println("OPEN");

        printer.close();
        socket.close();

        ClientDatabase.getInstance().addClient(this.playerHash);
        this.playerId = ClientDatabase.getInstance().getId(this.playerHash);
    }

    @Override
    public void sendClientStub(ClientCommands clientStub) {
        this.clientStub = clientStub;
    }

    @Override
    public String quitRoom() {
        if (this.room.getRoomOwner() == this) {
            this.room.closeRoom();
        } else {
            this.room.removeClient(this);
        }
        return null;
    }

    @Override
    public String createRoom() throws RemoteException {
        this.room =
          new Room(this, "");
        if (clientStub != null) {
            clientStub.sendMessage("Room Created");
        }

        return null;
    }

    @Override
    public List<LinkedHashMap<String, String>> listRooms() {
        return Room.getRoomList();
    }

    @Override
    public String joinRoom(UUID roomUUID) {
        this.room = Room.addClient(roomUUID, this);

        try {
            clientStub.updateLobby();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PlayerProfile> showRoom() {
        return this.room.getRoomContent();
    }

    @Override
    public String startRoom() {
        return null;
    }

    @Override
    public void placeBuilding(Buildable building) {

    }

    @Override
    public void updateColor(PlayerColor playerColor) {
        PlayerColor oldColor = this.playerColor;
        this.playerColor = playerColor;

        try {
            clientStub.updateLobby();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerHash() {
        return this.playerHash;
    }

    public String getPlayerId() { return this.playerId; }

    public PlayerColor getPlayerColor() {
        return this.playerColor;
    }

    public PlayerStartPosition getPlayerStartPosition() {
        return this.playerStartPosition;
    }

    public Boolean isReady() {
        return this.isReady;
    }

    public void close() {
        try {
            this.clientStub.disconnect();
            Naming.unbind(GameServer.RMI_HOST + ":" + GameServer.RMI_PORT + "/" + this.playerHash);
            GameServer.removeClient(this);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

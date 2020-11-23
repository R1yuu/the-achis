package ic20b106.server;

import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.NetworkConstants;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.shared.RemoteCommands;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientHandler extends UnicastRemoteObject implements RemoteCommands {

    private final UUID playerUUID;
    public ClientCommands clientStub;
    private Room room;
    private PlayerColor playerColor;
    private PlayerStartPosition playerStartPosition;
    private Boolean isReady = false;

    public ClientHandler(Socket socket) throws IOException {
        this.playerUUID = UUID.randomUUID();

        Naming.rebind(NetworkConstants.RMI_HOST + ":" + NetworkConstants.RMI_PORT + "/" + this.playerUUID.toString(),
          this);

        PrintStream printer = new PrintStream(socket.getOutputStream(), true);

        printer.println(this.playerUUID.toString());

        printer.close();
        socket.close();
    }

    @Override
    public void sendClientStub(ClientCommands clientStub) {
        this.clientStub = clientStub;
    }

    @Override
    public String quitRoom() {
        if (this.playerUUID == this.room.getId()) {
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
        Room.addClient(roomUUID, this);
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

    }

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
            this.clientStub.close();
            Naming.unbind(NetworkConstants.RMI_HOST + ":" + NetworkConstants.RMI_PORT + "/" + this.playerUUID.toString());
            GameServer.removeClient(this);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

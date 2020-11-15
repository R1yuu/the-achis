package ic20b106;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class ClientHandler extends UnicastRemoteObject implements RemoteCommands {

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
        this.room.removeClient(this);
        return null;
    }

    @Override
    public String createRoom(String roomName, PlayerColor playerColor,
                             PlayerStartPosition playerStartPosition) throws RemoteException {
        this.playerColor = playerColor;
        this.startPosition = playerStartPosition;
        this.room =
          new Room(this, roomName);
        if (clientStub != null) {
            clientStub.sendMessage("Room Created");
        }

        return null;
    }

    @Override
    public String listRooms() {
        return Room.getRoomList();
    }

    @Override
    public String joinRoom() {
        return null;
    }

    @Override
    public String showRoom() {
        return null;
    }

    @Override
    public String startRoom() {
        return null;
    }

    @Override
    public void placeBuilding(Buildable building) {

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

    private final UUID playerUUID;
    public ClientCommands clientStub;
    private Room room;
    private PlayerColor playerColor;
    private PlayerStartPosition startPosition;
}

package ic20b106;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteCommands extends Remote {
    void sendClientStub(ClientCommands clientStub) throws RemoteException;
    String quitRoom() throws RemoteException;
    String createRoom(String roomName, PlayerColor playerColor,
                      PlayerStartPosition playerStartPosition) throws RemoteException;
    String listRooms() throws RemoteException;
    String joinRoom() throws RemoteException;
    String showRoom() throws RemoteException;
    String startRoom() throws RemoteException;
    void placeBuilding(Buildable building) throws RemoteException;
}
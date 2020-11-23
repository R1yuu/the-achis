package ic20b106.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public interface RemoteCommands extends Remote {
    void sendClientStub(ClientCommands clientStub) throws RemoteException;
    String quitRoom() throws RemoteException;
    String createRoom() throws RemoteException;
    List<LinkedHashMap<String, String>> listRooms() throws RemoteException;
    String joinRoom(UUID roomUUID) throws RemoteException;
    List<PlayerProfile> showRoom() throws RemoteException;
    String startRoom() throws RemoteException;
    void placeBuilding(Buildable building) throws RemoteException;
    void updateColor(PlayerColor playerColor) throws RemoteException;
}

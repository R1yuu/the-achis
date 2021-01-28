package ic20b106.shared;

import ic20b106.shared.utils.IntPair;
import ic20b106.shared.utils.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public interface RemoteCommands extends Remote {
    void sendClientStub(ClientCommands clientStub) throws RemoteException;
    void quitRoom() throws RemoteException;
    String createRoom() throws RemoteException;
    List<LinkedHashMap<String, String>> listRooms() throws RemoteException;
    boolean joinRoom(UUID roomUUID) throws RemoteException;
    Pair<RoomProfile, List<PlayerProfile>> showRoom() throws RemoteException;
    String startRoom() throws RemoteException;
    void updateColor(PlayerColor playerColor) throws RemoteException;
    void updatePosition(PlayerStartPosition playerColor) throws RemoteException;
    void updateReady(boolean isReady) throws RemoteException;
    void updateRoomName(String roomName) throws RemoteException;
}

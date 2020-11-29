package ic20b106.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCommands extends Remote {
    void updateBuilding(Buildable buildable) throws RemoteException;
    void sendMessage(Object message) throws RemoteException;
    void disconnect() throws RemoteException;
    void updateColors(PlayerColor freeColor, PlayerColor takenColor) throws RemoteException;
    void updateLobby() throws RemoteException;
}

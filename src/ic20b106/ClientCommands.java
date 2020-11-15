package ic20b106;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCommands extends Remote {
    void updateBuilding(Buildable buildable) throws RemoteException;
    void sendMessage(Object message) throws RemoteException;
    void close() throws RemoteException;
}

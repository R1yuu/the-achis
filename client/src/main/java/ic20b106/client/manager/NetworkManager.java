package ic20b106.client.manager;

import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.RemoteCommands;
import ic20b106.shared.NetworkConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;

public class NetworkManager extends UnicastRemoteObject implements ClientCommands, Serializable {

    private NetworkManager() throws IOException, NotBoundException {
        Socket socket = new Socket(NetworkConstants.HOST, NetworkConstants.PORT);

        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String uuidString = buffer.readLine();

        buffer.close();
        socket.close();

        this.serverStub = (RemoteCommands)Naming.lookup(NetworkConstants.RMI_HOST + ":" +
          NetworkConstants.RMI_PORT + "/" + uuidString);

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
    public void close() {
        singleInstance = null;
    }

    public static NetworkManager getInstance() {
        if (singleInstance == null) {
            try {
                singleInstance = new NetworkManager();
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }
        return singleInstance;
    }

    private static NetworkManager singleInstance;
    public RemoteCommands serverStub;
}

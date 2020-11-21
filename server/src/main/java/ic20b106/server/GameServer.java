package ic20b106.server;

import ic20b106.shared.NetworkConstants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 * @author t
 * Link: https://stackoverflow.com/a/10131449
 */
public class GameServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            LocateRegistry.createRegistry(NetworkConstants.RMI_PORT);
            serverSocket = new ServerSocket(NetworkConstants.PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();

                // new thread for a client
                addClient(new ClientHandler(socket));
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                e.printStackTrace();
            }
        }
    }

    public static void addClient(ClientHandler clientHandler) {
        synchronized (clientHandlers) {
            clientHandlers.add(clientHandler);
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        synchronized (clientHandlers) {
            clientHandlers.remove(clientHandler);
        }
    }

    public static ClientHandler getClient(int idx) {
        synchronized (clientHandlers) {
            return clientHandlers.get(idx);
        }
    }

    private static final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
}

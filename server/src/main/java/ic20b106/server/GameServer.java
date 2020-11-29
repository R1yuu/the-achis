package ic20b106.server;

import ic20b106.shared.NetworkConstants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.ArrayList;

/**
 * @author t
 * Link: https://stackoverflow.com/a/10131449
 */
public class GameServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket;

        ClientDatabase.getInstance();

        int rmiPort = NetworkConstants.RMI_PORT;
        do {
            try {
                LocateRegistry.createRegistry(rmiPort);
                serverSocket = new ServerSocket(NetworkConstants.PORT);
                break;
            } catch (ExportException exportException) {
                System.err.println("The Port '" + rmiPort + "' is already taken. Please choose a new free one.");
                String userInput = "";
                do {
                    try {
                        System.err.print("> ");
                        userInput = System.console().readLine();
                        rmiPort = Integer.parseInt(userInput);
                        if (rmiPort < 1025 || rmiPort > 65535) {
                            throw new NumberFormatException();
                        }
                        break;
                    } catch (NumberFormatException numberFormatException) {
                        System.err.println("Invalid input: '" + userInput + "'.");
                        System.err.println("Please input an integer from 1025 to 65535");
                    }
                } while (true);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while (true);


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

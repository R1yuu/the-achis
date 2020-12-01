package ic20b106.server;

import ic20b106.shared.NetworkConstants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author t
 * Link: https://stackoverflow.com/a/10131449
 */
public class GameServer {

    public static void main(String[] args) {
        ServerSocket serverSocket;
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
                socket = serverSocket.accept();

                new ClientHandler(socket);
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                e.printStackTrace();
            }
        }
    }

    public static void addClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.putIfAbsent(clientHandler.getPlayerHash(), clientHandler);
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.remove(clientHandler.getPlayerHash());
        }
    }

    public static boolean contains(ClientHandler clientHandler) {
        synchronized (clients) {
            return clients.containsKey(clientHandler.getPlayerHash());
        }
    }

    private static final HashMap<String, ClientHandler> clients = new HashMap<>();
}

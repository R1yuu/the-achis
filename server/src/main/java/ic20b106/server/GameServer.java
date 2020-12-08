package ic20b106.server;

import ic20b106.shared.NetworkConstants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Achi Game Server implementation
 */
public class GameServer {

    private static final HashMap<String, ClientHandler> clients = new HashMap<>();

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
                String portError = "The Port '" + rmiPort + "' was already taken.";
                Logger.getGlobal().severe(portError);
                System.err.println(portError);
                String userInput = "";
                do {
                    try {
                        System.err.print("> ");
                        userInput = System.console().readLine();
                        Logger.getGlobal().severe("New Input: " + userInput);
                        rmiPort = Integer.parseInt(userInput);
                        if (rmiPort < 1025 || rmiPort > 65535) {
                            throw new NumberFormatException();
                        }
                        break;
                    } catch (NumberFormatException numberFormatException) {
                        String errorMsg = "Invalid input: '" + userInput + "'.";
                        Logger.getGlobal().severe(errorMsg);
                        System.err.println(errorMsg);

                        errorMsg = "Please input an integer from 1025 to 65535";
                        Logger.getGlobal().severe(errorMsg);
                        System.err.println(errorMsg);
                    }
                } while (true);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while (true);

        while (true) {
            try {
                socket = serverSocket.accept();

                new ClientHandler(socket);
            } catch (IOException e) {
                Logger.getGlobal().severe("I/O error: " + e);
                e.printStackTrace();
            }
        }
    }

    public static HashMap<String, ClientHandler> getClients() {
        synchronized (clients) {
            return clients;
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
}

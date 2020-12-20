package ic20b106.server;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
    public static int SOCKET_PORT = 2910;
    public static String RMI_HOST = "rmi://achirealm.com";
    public static int RMI_PORT = 1509;
    public static boolean VERBOSE = false;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        Options options = new Options();
        options.addOption(new Option("p", "port", true,
          "Use specified Socket Port. (Default: " + SOCKET_PORT + ")"));
        options.addOption(new Option("h", "host", true,
          "Use specified RMI Host. (Default: '" + RMI_HOST + "')"));
        options.addOption(new Option("r", "rmiport", true,
          "Use specified RMI Port. (Default: " + RMI_PORT + ")"));
        options.addOption(new Option("v", "verbose", false,
          "Outputs Logs to the Terminal."));

        parseArgs(options, args);

        ClientDatabase.getInstance();

        try {
            LocateRegistry.createRegistry(GameServer.RMI_PORT);
            serverSocket = new ServerSocket(GameServer.SOCKET_PORT);
        } catch (ExportException exportException) {
            Logger.getGlobal().severe("The Port '" + GameServer.RMI_PORT + "' was already taken.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket);
            } catch (IOException e) {
                Logger.getGlobal().severe("I/O error: " + e);
                e.printStackTrace();
            }
        }
    }

    public static void parseArgs(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException parseException) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("server-0.1.0", options);
            System.exit(1);
        }

        if (cmd.hasOption("p")) {
            SOCKET_PORT = Integer.parseInt(cmd.getOptionValue("p"));
        }
        if (cmd.hasOption("h")) {
            RMI_HOST = cmd.getOptionValue("h");
        }
        if (cmd.hasOption("r")) {
            RMI_PORT = Integer.parseInt(cmd.getOptionValue("r"));
        }
        VERBOSE = cmd.hasOption("v");
    }

    public static HashMap<String, ClientHandler> getClients() {
        synchronized (clients) {
            return clients;
        }
    }

    public static void addClient(ClientHandler clientHandler) {
        synchronized (clients) {
            if (clients.putIfAbsent(clientHandler.getPlayerHash(), clientHandler) == null) {
                CommandLineManager.out.logInfo("Client '" + clientHandler.getPlayerHash() + "' joined the Server.");
            }
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            if (clients.remove(clientHandler.getPlayerHash()) != null) {
                CommandLineManager.out.logInfo("Client '" + clientHandler.getPlayerHash() + "' left the Server.");
            }
        }
    }

    public static boolean contains(ClientHandler clientHandler) {
        synchronized (clients) {
            return clients.containsKey(clientHandler.getPlayerHash());
        }
    }
}

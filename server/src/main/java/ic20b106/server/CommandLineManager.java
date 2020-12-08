package ic20b106.server;

import java.io.PrintStream;
import java.util.HashSet;

public class CommandLineManager {

    private final PrintStream printer;
    public static final CommandLineManager out = new CommandLineManager(System.out);
    public static final CommandLineManager err = new CommandLineManager(System.err);
    private static final String commandPrompt = "> ";
    static {
        new Thread(CommandLineManager::commandListener).start();
    }

    private CommandLineManager(PrintStream printStream) {
        this.printer = printStream;
    }


    private static void commandListener() {
        while (true) {
            String command = System.console().readLine().toLowerCase().strip();
            String[] arguments = command.split(" ");
            switch (arguments[0]) {
                case "list" -> {
                    if (arguments.length >= 2) {
                        switch (arguments[1]) {
                            case "clients" -> {
                                GameServer.getClients().forEach((hash, client) ->
                                  out.format("ID: %-5s | Hash: %s", client.getPlayerId(), hash));
                                out.print("Number of online clients: " + GameServer.getClients().size());
                            }
                            case "rooms" -> {
                                out.print(new String(new char[42]).replace("\0", "\u2500"));
                                Room.getRoomList().forEach(room -> {
                                    out.print("UUID: " + room.get("id"));
                                    out.format("Name: %-16s | Players: %s",
                                      room.get("roomName"), room.get("playerCount"));
                                });
                                out.print("Number of open rooms: " + Room.getRoomList().size());
                            }
                            default -> printError(command);
                        }
                    } else {
                        String branchPipe = " \u251C\u2500";
                        String endPipe = " \u2514\u2500";

                        out.print(new String(new char[42]).replace("\0", "\u2500"));
                        Room.getRooms().forEach(room -> {
                            out.print("Room UUID: " + room.getId());
                            int cnt = 0;
                            for (ClientHandler client : room.getClients()) {
                                if (cnt == room.getClients().size() - 1) {
                                    out.format(endPipe + " ID: %-5s | Hash: %s", client.getPlayerId(), client.getPlayerHash());
                                } else {
                                    out.format(branchPipe + " ID: %-5s | Hash: %s", client.getPlayerId(), client.getPlayerHash());
                                }
                                cnt++;
                            }
                        });
                    }
                }
                case "stop" -> {
                    Room.closeAllRooms();
                    ClientDatabase.closeIfExists();
                    System.exit(0);
                }
                case "help" -> {
                    String branchPipe = " \u251C\u2500";
                    String endPipe = " \u2514\u2500";

                    out.format("%-8s- Lists all rooms and their respective clients", "list");
                    out.format(branchPipe + " %-8s- Only lists all connected clients", "clients");
                    out.format(endPipe + " %-8s- Only lists all open rooms", "rooms");
                    out.format("%-8s- Disconnects every client and stops the server", "stop");
                    out.format("%-8s- Shows you a list of all commands with a description", "help");
                }
                default -> printError(command);
            }
        }
    }

    private static void printError(String input) {
        err.print("Unkown Command: '" + input + "'");
        err.print("Use 'help' to see a list of commands.");
    }

    public void print(String s) {
        synchronized (printer) {
            printer.println((char)13 + s);
            System.out.print(commandPrompt);
        }
    }

    public void format(String format, Object... args) {
        synchronized (printer) {
            printer.format((char)13 + format + "%n", args);
            System.out.print(commandPrompt);
        }
    }
}

package ic20b106.server;

import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.shared.RoomProfile;
import ic20b106.shared.utils.Pair;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Room {

    private static final int MAX_CAPACITY = 4;
    private static final HashSet<Room> rooms = new HashSet<>();
    private final HashSet<ClientHandler> clients = new HashSet<>(MAX_CAPACITY);
    private final ClientHandler roomOwner;
    private String roomName;
    private final Object roomNameLock = new Object();
    private final UUID uuid;
    private final Map<PlayerColor, Boolean> colors = new HashMap<>(Map.of(
      PlayerColor.RED, true,
      PlayerColor.BLUE, true,
      PlayerColor.YELLOW, true,
      PlayerColor.GREEN, true));
    private final Map<PlayerStartPosition, Boolean> startPositions = new HashMap<>(Map.of(
      PlayerStartPosition.TOP_LEFT, true,
      PlayerStartPosition.TOP_RIGHT, true,
      PlayerStartPosition.BOTTOM_LEFT, true,
      PlayerStartPosition.BOTTOM_RIGHT, true));

    public Room(ClientHandler client, String roomName) {
        this.uuid = UUID.randomUUID();
        this.roomName = roomName;
        this.roomOwner = client;
        this.clients.add(client);
        synchronized (rooms) {
            rooms.add(this);
        }
    }

    public void startRoom() {
        synchronized (clients) {
            clients.forEach(client -> {
                try {
                    client.clientStub.startGame(client.getPlayerColor(), client.getPlayerStartPosition());
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            });
        }
    }

    public void forceLobbyUpdate(ClientHandler... skippedClients) {
        CommandLineManager.out.logInfo(
          "Room(" + this.uuid + "): Forcing Lobby-Update on all Clients...");
        List<ClientHandler> skippedClientList = Arrays.asList(skippedClients);
        for (ClientHandler clientHandler : clients) {
            if (skippedClientList.contains(clientHandler)) {
                continue;
            }
            try {
                clientHandler.clientStub.updateLobby();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        }
        CommandLineManager.out.logInfo(
          "Room(" + this.uuid + "): Updated all Clients.");
    }

    public void addClient(ClientHandler client) {
        synchronized (clients) {
            if (clients.size() < MAX_CAPACITY) {
                CommandLineManager.out.logInfo(
                  "Room(" + this.uuid + "): Adding Client '" + client.getPlayerHash() + "'...");
                clients.add(client);
                client.setRoom(this);
            }
        }

        this.forceLobbyUpdate(client);
    }

    public static Room addClient(UUID roomUUID, ClientHandler client) {
        synchronized (rooms) {
            for (Room room : rooms) {
                if (room.uuid.equals(roomUUID)) {
                    room.addClient(client);
                    return room;
                }
            }
        }
        return null;
    }

    public void removeClient(ClientHandler clientHandler) {
        this.removeClient(clientHandler, true);
    }

    public void removeClient(ClientHandler clientHandler, boolean updateClients) {
        synchronized (clients) {
            clients.remove(clientHandler);
            if (clientHandler.getPlayerColor() != null) {
                this.setColorAvailability(clientHandler.getPlayerColor(), true);
            }
            if (clientHandler.getPlayerStartPosition() != null) {
                this.setPositionAvailability(clientHandler.getPlayerStartPosition(), true);
            }
            CommandLineManager.out.logInfo(
              "Room(" + this.uuid + "): Removed Client '" + clientHandler.getPlayerHash() + "'.");
        }
        if (updateClients) {
            forceLobbyUpdate();
        }
        clientHandler.close();
    }

    public void closeRoom() {
        CommandLineManager.out.logInfo("Closing Room '" + this.uuid + "'.");
        synchronized (clients) {
            clients.forEach(client -> this.removeClient(client, false));
        }
        synchronized (rooms) {
            rooms.remove(this);
        }
    }

    public void broadcast(Object message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                try {
                    client.clientStub.sendMessage(message);
                } catch (RemoteException e) {
                    removeClient(client);
                }
            }
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setColorAvailability(PlayerColor color, boolean isFree) {
        CommandLineManager.out.logInfo(
          "Setting Color '" + color.toString() + "'s availability to " + isFree);
        synchronized (colors) {
            colors.put(color, isFree);
        }
    }

    public void setPositionAvailability(PlayerStartPosition position, boolean isFree) {
        CommandLineManager.out.logInfo(
          "Setting Position '" + position.toString() + "'s availability to " + isFree);
        synchronized (startPositions) {
            startPositions.put(position, isFree);
        }
    }

    public static List<LinkedHashMap<String, String>> getRoomList() {
        List<LinkedHashMap<String, String>> roomList = new ArrayList<>();
        synchronized (rooms) {
            rooms.forEach((room) -> {
                LinkedHashMap<String, String> roomMap = new LinkedHashMap<>();
                roomMap.put("id", room.uuid.toString());
                roomMap.put("roomName", room.roomName);
                roomMap.put("playerCount", String.valueOf(room.clients.size()));

                roomList.add(roomMap);
            });
        }
        return roomList;
    }

    public String getRoomName() {
        synchronized (roomNameLock) {
            return roomName;
        }
    }

    public void setRoomName(String roomName) {
        synchronized (roomNameLock) {
            this.roomName = roomName;
        }
    }

    public ClientHandler getRoomOwner() {
        return roomOwner;
    }


    public Pair<RoomProfile, List<PlayerProfile>> getRoomContent() {
        List<PlayerColor> freeColors = new LinkedList<>();
        List<PlayerStartPosition> freeStartPositions = new LinkedList<>();
        synchronized (colors) {
            colors.forEach((color, isFree) -> {
                if (isFree) {
                    freeColors.add(color);
                }
            });
        }
        synchronized (startPositions) {
            startPositions.forEach((startPosition, isFree) -> {
                if (isFree) {
                    freeStartPositions.add(startPosition);
                }
            });
        }

        RoomProfile roomProfile = new RoomProfile(
          this.uuid, this.roomName, this.roomOwner.getPlayerId(), freeColors, freeStartPositions);

        List<PlayerProfile> clientProfiles = new LinkedList<>();
        synchronized (clients) {
            for (ClientHandler client : clients) {
                clientProfiles.add(
                  new PlayerProfile(
                    client.getPlayerHash(),
                    client.getPlayerId(),
                    client.getPlayerColor(),
                    client.getPlayerStartPosition(),
                    client.isReady()));
            }
        }

        Pair<RoomProfile, List<PlayerProfile>> roomContent = new Pair<>(roomProfile, clientProfiles);

        CommandLineManager.out.logInfo("Room Content: " + roomContent.toString());

        return roomContent;
    }

    public static void closeAllRooms() {
        synchronized (rooms) {
            rooms.forEach(Room::closeRoom);
        }
    }

    public static HashSet<Room> getRooms() {
        synchronized (rooms) {
            return rooms;
        }
    }

    public HashSet<ClientHandler> getClients() {
        synchronized (clients) {
            return this.clients;
        }
    }
}

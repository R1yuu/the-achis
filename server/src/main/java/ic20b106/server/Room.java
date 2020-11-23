package ic20b106.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Room {

    public Room(ClientHandler client, String roomName) {
        this.id = UUID.randomUUID();
        this.roomName = roomName;
        this.roomOwner = client;
        this.clients.add(client);
        synchronized (rooms) {
            rooms.add(this);
            System.out.println("Added");
        }
    }

    public void addClient(ClientHandler client) {
        synchronized (clients) {
            if (clients.size() < MAX_CAPACITY) {
                clients.add(client);
                clients.parallelStream().forEach(clientHandler -> {
                    try {
                        clientHandler.clientStub.updateLobby();
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
                });
            }
        }
    }

    public static void addClient(UUID roomUUID, ClientHandler client) {
        synchronized (rooms) {
            rooms.forEach(room -> {
                if (room.id.equals(roomUUID)) {
                    room.addClient(client);
                }
            });
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clientHandler.close();
            clients.remove(clientHandler);
            clients.parallelStream().forEach(clientHandler1 -> {
                try {
                    clientHandler.clientStub.updateLobby();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            });
        }
    }

    public void closeRoom() {
        synchronized (clients) {
            clients.forEach(ClientHandler::close);
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

    public UUID getId() {
        return this.id;
    }

    public static List<LinkedHashMap<String, String>> getRoomList() {
        List<LinkedHashMap<String, String>> roomList = new ArrayList<>();
        synchronized (rooms) {
            rooms.forEach((room) -> {
                LinkedHashMap<String, String> roomMap = new LinkedHashMap<>();
                roomMap.put("id", room.id.toString());
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


    public List<PlayerProfile> getRoomContent() {
        List<PlayerProfile> clientProfiles = new LinkedList<>();

        for (ClientHandler client : clients) {
            clientProfiles.add(
              new PlayerProfile(
                client.getPlayerColor(),
                client.getPlayerStartPosition(),
                client.isReady()));
        }

        return clientProfiles;
    }

    public void updateAvailableColors(PlayerColor freeColor, PlayerColor takenColor) {
        synchronized (clients) {
            clients.parallelStream().forEach(
              clientHandler -> {
                  try {
                      clientHandler.clientStub.updateColors(freeColor, takenColor);
                  } catch (RemoteException remoteException) {
                      remoteException.printStackTrace();
                  }
              });
        }
    }

    private static final int MAX_CAPACITY = 4;
    private static final HashSet<Room> rooms = new HashSet<>();
    private final HashSet<ClientHandler> clients = new HashSet<>(MAX_CAPACITY);
    private final ClientHandler roomOwner;
    private String roomName;
    private final Object roomNameLock = new Object();
    private final UUID id;
}

package ic20b106.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Room {

    public Room(ClientHandler client, String roomName) {
        this.id = UUID.randomUUID();
        this.roomName = roomName;
        this.roomOwner = client;
        this.addClient(client);
        synchronized (rooms) {
            rooms.add(this);
            System.out.println("Added");
        }
    }

    public void addClient(ClientHandler client) {
        synchronized (clients) {
            if (clients.size() < MAX_CAPACITY) {
                clients.add(client);
            }
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clientHandler.close();
            clients.remove(clientHandler);
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

    public static String getRoomList() {
        Gson gson = new Gson();
        JsonArray jsonRoomArray = new JsonArray();
        JsonObject jsonRoomObject = new JsonObject();
        synchronized (rooms) {
            rooms.forEach((room) -> {
                jsonRoomObject.addProperty("id", room.id.toString());
                jsonRoomObject.addProperty("roomName", room.roomName);
                jsonRoomObject.addProperty("playerCount", room.clients.size());
                jsonRoomArray.add(jsonRoomObject);
            });
        }
        return gson.toJson(jsonRoomArray);
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

    private static final int MAX_CAPACITY = 4;
    private static final HashSet<Room> rooms = new HashSet<>();
    private final HashSet<ClientHandler> clients = new HashSet<>(MAX_CAPACITY);
    private final ClientHandler roomOwner;
    private String roomName;
    private final Object roomNameLock = new Object();
    private final UUID id;
}

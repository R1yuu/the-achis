package ic20b106.client.game.menus;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic20b106.client.manager.NetworkManager;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.rmi.RemoteException;

public class LobbyListController {
    @FXML
    private void initialize() {
        listPane.setBorder(new Border(
          new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        retrieveLobbies();
    }

    private void retrieveLobbies() {
        try {
            String list = NetworkManager.getInstance().serverStub.listRooms();
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(list, JsonArray.class);
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                for (String key  : jsonObject.keySet()) {
                    System.out.println(key + ": " + jsonObject.get(key));
                }
            }
        } catch (RemoteException e) {
            NetworkManager.getInstance().close();

            e.printStackTrace();

        }

    }

    @FXML
    private ScrollPane listPane;

    @FXML
    private VBox listBox;
}

package ic20b106.client.game.menus;

import ic20b106.client.Game;
import ic20b106.client.manager.NetworkManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Controller Class of EscapeMenu.fxml
 */
public class LobbyListController {

    @FXML
    private ScrollPane listPane;

    @FXML
    private VBox listBox;

    @FXML
    private GridPane lobbyListPane;

    /**
     * Initializes Lobby List
     */
    @FXML
    private void initialize() {
        listPane.setBorder(new Border(
          new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        retrieveLobbies();
    }

    /**
     * Goes Back to the Main Menu
     */
    @FXML
    private void backToMainMenu() {
        Game.resetGame();
    }

    /**
     * Retrieves all Lobbies from the Server and Displays them
     */
    private void retrieveLobbies() {
        try {
            List<LinkedHashMap<String, String>> roomList = NetworkManager.getInstance().serverStub.listRooms();

            int rowIdx = 1;
            for (LinkedHashMap<String, String> roomMap : roomList) {

                int colIdx = 0;


                for (String value : roomMap.values()) {
                    lobbyListPane.add(new Label(value), colIdx, rowIdx);
                    colIdx++;
                }
                Button joinButton = new Button("Join");
                joinButton.setOnAction(actionEvent -> {
                    try {
                        NetworkManager.getInstance().serverStub.joinRoom(UUID.fromString(roomMap.get("id")));
                        VBox lobbyMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/LobbyMenu.fxml"));
                        Game.primaryPane.getChildren().setAll(lobbyMenu);
                    } catch (IOException remoteException) {
                        remoteException.printStackTrace();
                    }
                });
                lobbyListPane.add(joinButton, colIdx, rowIdx);
                rowIdx++;
            }
        } catch (RemoteException e) {
            NetworkManager.getInstance().close();

            e.printStackTrace();

        }

    }
}

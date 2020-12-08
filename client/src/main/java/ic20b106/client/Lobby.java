package ic20b106.client;

import ic20b106.client.manager.NetworkManager;
import ic20b106.client.util.ByteUtils;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.shared.utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.rmi.RemoteException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Multiplayer Lobby to set Settings
 */
public class Lobby {

    public static ComboBox<String> colorComboBox = new ComboBox<>();
    public static GridPane playerPane;
    @FXML
    private GridPane playerGridPane;
    @FXML
    private Button exitLobbyButton;
    @FXML
    private VBox roomNameBox;
    @FXML
    private Label roomOwnerLabel;
    @FXML
    private Label roomUUIDLabel;
    @FXML
    private VBox colorBox;
    @FXML
    private VBox positionBox;

    /**
     * FXML initialize Method
     */
    @FXML
    private void initialize() {

        playerPane = playerGridPane;

        class ColorLabelCell extends ListCell<String> {
            Label label;

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setItem(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    Rectangle rectangle = new Rectangle(20, 20, PlayerColor.fromString(item).toColor());
                    rectangle.setArcWidth(5);
                    rectangle.setArcHeight(5);
                    label = new Label("", rectangle);
                    setGraphic(label);
                }
            }
        }

        if (Game.roomOwner) {
            exitLobbyButton.setText("Close Lobby");
            exitLobbyButton.setOnAction((actionEvent) -> Game.resetGame());
        }

        colorComboBox.getItems().addAll("RED", "BLUE", "YELLOW", "GREEN");

        colorComboBox.setCellFactory(listView -> new ColorLabelCell());
        colorComboBox.setButtonCell(new ColorLabelCell());
        colorComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                NetworkManager.getInstance().serverStub.updateColor(PlayerColor.fromString(newValue));
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        ComboBox<PlayerStartPosition> positionComboBox = new ComboBox<>();
        positionComboBox.getItems().addAll(PlayerStartPosition.values());

        colorBox.getChildren().addAll(colorComboBox);
        positionBox.getChildren().addAll(positionComboBox);

        updateTable();
    }

    /**
     * Starts the Lobby for all Clients
     */
    @FXML
    private void startGame() {

    }

    /**
     * Updates the Table List and Player information
     */
    public static void updateTable() {
        try {
            int rowIdx = 1;
            for (PlayerProfile playerProfile : NetworkManager.getInstance().serverStub.showRoom()) {
                for (Node child : playerPane.getChildren()) {
                    if(GridPane.getRowIndex(child) == rowIdx) {
                        int colIdx = GridPane.getColumnIndex(child);
                        switch (colIdx) {
                            case 0 -> {
                                if (playerProfile.id != null) {
                                    ((Label) child).setText(playerProfile.id);
                                }
                            }
                            case 1 -> {
                                if (playerProfile.color != null) {
                                    ((Rectangle) child).setFill(playerProfile.color.toColor());
                                }
                            }
                            case 2 -> {
                                if (playerProfile.startPosition != null) {
                                    ((Label) child).setText(playerProfile.startPosition.toString());
                                }
                            }
                            case 3 -> {
                                if (playerProfile.isReady != null) {
                                    ((CheckBox) child).setSelected(playerProfile.isReady);
                                }
                            }
                        }
                    }
                }
                rowIdx++;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

package ic20b106.client;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.storage.Headquarters;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.link.LinkDirection;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.client.manager.NetworkManager;
import ic20b106.client.util.javafx.GameBoard;
import ic20b106.client.util.javafx.ZoomableScrollPane;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerProfile;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.shared.RoomProfile;
import ic20b106.shared.utils.IntPair;
import ic20b106.shared.utils.Pair;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Multiplayer Lobby to set Settings
 */
public class Lobby {

    public static final ComboBox<String> colorComboBox = new ComboBox<>();
    public static final ComboBox<String> positionComboBox = new ComboBox<>();
    public static GridPane playerPane;
    public static Button startGameButton;
    public static Label roomName;
    public static Label roomOwner;
    public static Label roomUUID;
    @FXML
    private GridPane playerGridPane;
    @FXML
    private Button exitLobbyButton;
    @FXML
    private Label roomNameLabel;
    @FXML
    private Label roomOwnerLabel;
    @FXML
    private Label roomUUIDLabel;
    @FXML
    private VBox colorBox;
    @FXML
    private VBox positionBox;
    @FXML
    private VBox lobbyNameBox;
    @FXML
    private Button startGameBtn;
    @FXML
    private TextField lobbyNameField;

    /**
     * FXML initialize Method
     */
    @FXML
    private void initialize() {

        playerPane = playerGridPane;
        roomName = roomNameLabel;
        roomOwner = roomOwnerLabel;
        roomUUID = roomUUIDLabel;
        startGameButton = startGameBtn;

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
                    Rectangle rectangle = new Rectangle(20, 20, Game.playerToColor(PlayerColor.fromString(item)));
                    rectangle.setArcWidth(5);
                    rectangle.setArcHeight(5);
                    label = new Label("", rectangle);
                    setGraphic(label);
                }
            }
        }

        if (Game.roomOwner) {
            exitLobbyButton.setText("Close Lobby");
        } else {
            exitLobbyButton.setText("Leave Lobby");
        }
        exitLobbyButton.setOnAction((actionEvent) -> {
            try {
                NetworkManager.getInstance().serverStub.quitRoom();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        colorComboBox.getItems().clear();
        colorComboBox.getItems().addAll("RED", "BLUE", "YELLOW", "GREEN");

        colorComboBox.setCellFactory(listView -> new ColorLabelCell());
        colorComboBox.setButtonCell(new ColorLabelCell());
        colorComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldColor, newColor) -> {
            try {
                if (newColor != null) {
                    NetworkManager.getInstance().serverStub.updateColor(PlayerColor.fromString(newColor));
                }
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        positionComboBox.getItems().clear();
        positionComboBox.getItems().addAll("TOP_LEFT", "TOP_RIGHT", "BOTTOM_LEFT", "BOTTOM_RIGHT");
        positionComboBox.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldPosition, newPosition) -> {
            try {
                if (newPosition != null) {
                    NetworkManager.getInstance().serverStub.updatePosition(PlayerStartPosition.fromString(newPosition));
                }
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });

        colorBox.getChildren().addAll(colorComboBox);
        positionBox.getChildren().addAll(positionComboBox);

        startGameButton.setDisable(true);
        if (!Game.roomOwner) {
            this.lobbyNameField.setDisable(true);
            this.lobbyNameBox.setVisible(false);
            startGameButton.setVisible(false);
        } else {
            this.lobbyNameField.setOnKeyReleased(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    try {
                        NetworkManager.getInstance().serverStub.updateRoomName(this.lobbyNameField.getText());
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    }
                }
            });
        }

        updateTable();
    }

    /**
     * Starts the Lobby for all Clients
     */
    @FXML
    private void startGame() {
        try {
            NetworkManager.getInstance().serverStub.startRoom();
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
    }

    /**
     * Starts Game
     *
     * @param playerColor Player Color
     * @param playerStartPosition Start Position of Player
     */
    public static void loadGame(PlayerColor playerColor, PlayerStartPosition playerStartPosition) {
        // max 100
        int boardWidth = 50;
        int boardHeight = 50;

        Game.playerColor = playerColor;
        Game.gameBoard = new GameBoard(boardWidth, boardHeight);

        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(Game.gameBoard, MouseButton.SECONDARY);

        IntPair startPosition = new IntPair(5, 5);
        HashMap<Storable, Integer> startStorage = new HashMap<>();

        switch (playerStartPosition) {
            case TOP_RIGHT -> {
                startPosition.setXY(5, boardWidth - 6);
                zoomableScrollPane.setHvalue(1);
            }
            case BOTTOM_LEFT -> {
                startPosition.setXY(boardHeight - 6, 5);
                zoomableScrollPane.setVvalue(1);
            }
            case BOTTOM_RIGHT -> {
                startPosition.setXY(boardHeight - 6, boardWidth - 6);
                zoomableScrollPane.setHvalue(1);
                zoomableScrollPane.setVvalue(1);
            }
        }

        startStorage.put(Material.WOOD, 16);
        startStorage.put(Material.ROCK, 12);

        // Sets Starting Storage
        /*
        switch (selectedStartResourcesToggle.getText()) {
            case "Few" -> {
                startStorage.put(Material.PEARL, 20);
                startStorage.put(Material.METAL, 20);
            }
            case "Medium" -> {
                startStorage.put(Material.PEARL, 40);
                startStorage.put(Material.METAL, 40);
            }
            case "Many" -> {
                startStorage.put(Material.PEARL, 60);
                startStorage.put(Material.METAL, 60);
            }
        }
         */


        Cell hqCell = Game.gameBoard.getCell(startPosition);

        Game.playerHQ = new Headquarters(hqCell, startStorage);

        hqCell.placeBuilding(Game.playerHQ);
        hqCell.setOwner(Game.playerColor);
        hqCell.extendArea(Game.playerColor, 5);
        hqCell.addLinks(LinkDirection.values());

        Platform.runLater(() -> Game.primaryPane.getChildren().setAll(zoomableScrollPane));
    }

    /**
     * Updates the Table List and Player information
     */
    public static void updateTable() {
        try {
            Pair<RoomProfile, List<PlayerProfile>> roomData = NetworkManager.getInstance().serverStub.showRoom();
            RoomProfile roomProfile = roomData.x;
            List<PlayerProfile> playerProfiles = roomData.y;
            Platform.runLater(() -> {
                roomName.setText(roomProfile.roomName);
                roomOwner.setText(roomProfile.roomOwner);
                roomUUID.setText(roomProfile.uuid.toString());

                colorComboBox.getItems().removeIf(color -> {
                    String selectedItem = colorComboBox.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        return !selectedItem.equals(color);
                    }
                    return true;
                });
                positionComboBox.getItems().removeIf(position -> {
                    String selectedItem = positionComboBox.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        return !selectedItem.equals(position);
                    }
                    return true;
                });

                roomProfile.freeColors.forEach(playerColor -> colorComboBox.getItems().add(playerColor.toString()));
                roomProfile.freeStartPositions.forEach(playerStartPosition ->
                  positionComboBox.getItems().add(playerStartPosition.toString()));

                playerPane.getChildren().clear();

                playerPane.add(new Label("Player Id"), 0, 0);
                playerPane.add(new Label("Player Color"), 1, 0);
                playerPane.add(new Label("Player Position"), 2, 0);
                playerPane.add(new Label("Ready?"), 3, 0);

                boolean startButtonDisabled = false;
                int rowIdx = 1;
                for (PlayerProfile playerProfile : playerProfiles) {

                    if (!playerProfile.isReady) {
                        startButtonDisabled = true;
                    }

                    playerPane.add(new Label(playerProfile.id), 0, rowIdx);

                    if (playerProfile.color != null) {
                        Rectangle colorRect = new Rectangle(20, 20, Game.playerToColor(playerProfile.color));
                        colorRect.setArcHeight(5);
                        colorRect.setArcWidth(5);
                        playerPane.add(colorRect, 1, rowIdx);
                    }

                    if (playerProfile.startPosition != null) {
                        playerPane.add(new Label(playerProfile.startPosition.toString()), 2, rowIdx);
                    }



                    CheckBox readyCheckbox = new CheckBox();
                    readyCheckbox.setSelected(playerProfile.isReady);
                    readyCheckbox.setOnAction(actionEvent -> {
                        try {
                            NetworkManager.getInstance().serverStub.updateReady(readyCheckbox.isSelected());
                        } catch (RemoteException remoteException) {
                            remoteException.printStackTrace();
                        }
                    });

                    if (!playerProfile.hash.equals(NetworkManager.getInstance().playerHash)) {
                        readyCheckbox.setDisable(true);
                    }

                    playerPane.add(readyCheckbox, 3, rowIdx);

                    rowIdx++;
                }

                startGameButton.setDisable(startButtonDisabled);

            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

package ic20b106.client.game.menus;

import ic20b106.client.Game;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.link.LinkDirection;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.core.Core;
import ic20b106.client.manager.NetworkManager;
import ic20b106.shared.utils.Pair;
import ic20b106.client.util.javafx.GameBoard;
import ic20b106.client.util.javafx.ZoomableScrollPane;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Controller Class of CreateGameMenu.fxml
 */
public class CreateGameMenuController {


    /**
     * Initializes Nodes
     */
    @FXML
    private void initialize() {
        Game.setAllButtonSFXOnAction(mainBox, List.of(ToggleButton.class));

        boardWidthLabel.setText(String.valueOf((int)boardWidthSlider.getValue()));
        boardHeightLabel.setText(String.valueOf((int)boardHeightSlider.getValue()));

        boardWidthSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            boardWidthSlider.setValue(newVal.intValue());
            boardWidthLabel.setText(String.valueOf(newVal.intValue()));
            boardRectangle.setWidth(boardRectangle.getWidth() +
              (newVal.intValue() - oldval.intValue()) * (boardWidthSlider.getMax() / boardWidthSlider.getMin()));
        });
        boardHeightSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            boardHeightSlider.setValue(newVal.intValue());
            boardHeightLabel.setText(String.valueOf(newVal.intValue()));
            boardRectangle.setHeight(boardRectangle.getHeight() +
              (newVal.intValue() - oldval.intValue()) * (boardHeightSlider.getMax() / boardHeightSlider.getMin()));
        });

        for (Toggle toggle : colorPicker.getToggles()) {
            ToggleButton toggleButton = (ToggleButton)toggle;

            String pressedBackgroundColor = "rgb(" +
              PlayerColor.fromString(toggleButton.getText()).getRGB() +
              ")";

            toggleButton.styleProperty().bind(Bindings.when(toggleButton.selectedProperty())
              .then("-fx-background-color: " + pressedBackgroundColor)
              .otherwise("-fx-background-color: #333333"));
        }
    }

    /**
     * Goes Back to the Main Menu
     */
    @FXML
    private void backToMainMenu() {
        Game.resetGame();
    }

    /**
     * Creates the Game Board
     */
    @FXML
    private void createLobby() {
        int boardWidth = (int)boardWidthSlider.getValue();
        int boardHeight = (int)boardHeightSlider.getValue();
        Game.gameBoard = new GameBoard(boardWidth, boardHeight);

        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(Game.gameBoard, MouseButton.SECONDARY);
        zoomableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoomableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        ToggleButton selectedColorToggle = (ToggleButton)colorPicker.getSelectedToggle();
        ToggleButton selectedStartPositionToggle = (ToggleButton)startPosition.getSelectedToggle();
        ToggleButton selectedStartResourcesToggle = (ToggleButton)startResources.getSelectedToggle();

        Pair<Integer, Integer> startPosition = new Pair<>(5, 5);
        HashMap<Material, Integer> startStorage = new HashMap<>();


        // Sets Player Color
        Game.playerColor = PlayerColor.fromString(selectedColorToggle.getText());


        // Sets Start-Position of Player
        Game.playerStartPosition = PlayerStartPosition.fromString(selectedStartPositionToggle.getText());
        switch (Game.playerStartPosition) {
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
                zoomableScrollPane.setVvalue(1);
                zoomableScrollPane.setHvalue(1);
            }
        }

        // Sets Starting Storage
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


        Cell coreCell = Game.gameBoard.getCell(startPosition);

        Game.playerCoreCell = coreCell;

        coreCell.placeBuilding(new Core(coreCell, startStorage));
        coreCell.setOwner(Game.playerColor.toColor());
        coreCell.extendArea(Game.playerColor.toColor(), 5);
        coreCell.addLinks(LinkDirection.values());

        try {
            NetworkManager.getInstance().serverStub.createRoom("Test", Game.playerColor, Game.playerStartPosition);
        } catch (RemoteException e) {
            NetworkManager.getInstance().close();
            // TODO: Handle Properly
            e.printStackTrace();
        }



        Game.primaryPane.getChildren().setAll(zoomableScrollPane);
    }

    @FXML
    private VBox mainBox;

    @FXML
    private Slider boardWidthSlider;

    @FXML
    private Slider boardHeightSlider;

    @FXML
    private Label boardWidthLabel;

    @FXML
    private Label boardHeightLabel;

    @FXML
    private Rectangle boardRectangle;

    @FXML
    private ToggleGroup colorPicker;

    @FXML
    private ToggleGroup startPosition;

    @FXML
    private ToggleGroup startResources;
}

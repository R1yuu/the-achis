package ic20b106.menus.main;

import com.sun.javafx.font.CharToGlyphMapper;
import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.LinkDirection;
import ic20b106.game.buildings.core.MainCore;
import ic20b106.util.Pair;
import ic20b106.util.javafx.GameBoard;
import ic20b106.util.javafx.ZoomableScrollPane;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
            String pressedBackgroundColor = "";

            switch (toggleButton.getText()) {
                case "Red" -> pressedBackgroundColor = "indianred";
                case "Blue" -> pressedBackgroundColor = "cornflowerblue";
                case "Yellow" -> pressedBackgroundColor = "orange";
                case "Green" -> pressedBackgroundColor = "forestgreen";
            }

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
    private void createGame() {
        int boardWidth = (int)boardWidthSlider.getValue();
        int boardHeight = (int)boardHeightSlider.getValue();
        Game.gameBoard = new GameBoard(boardWidth, boardHeight);

        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(Game.gameBoard, MouseButton.SECONDARY);
        zoomableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoomableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        ToggleButton selectedColorToggle = (ToggleButton)colorPicker.getSelectedToggle();
        ToggleButton selectedStartPositionToggle = (ToggleButton)startPosition.getSelectedToggle();
        Pair<Integer, Integer> startPosition = new Pair<>(5, 5);


        switch (selectedColorToggle.getText()) {
            case "Red" -> Game.playerColor = Color.INDIANRED;
            case "Blue" -> Game.playerColor = Color.CORNFLOWERBLUE;
            case "Yellow" -> Game.playerColor = Color.ORANGE;
            case "Green" -> Game.playerColor = Color.FORESTGREEN;
        }

        switch (selectedStartPositionToggle.getText()) {
            case "Top\nRight" -> {
                startPosition.setXY(5, boardWidth - 6);
                zoomableScrollPane.setHvalue(1);
            }
            case "Bottom\nLeft" -> {
                startPosition.setXY(boardHeight - 6, 5);
                zoomableScrollPane.setVvalue(1);
            }
            case "Bottom\nRight" -> {
                startPosition.setXY(boardHeight - 6, boardWidth - 6);
                zoomableScrollPane.setVvalue(1);
                zoomableScrollPane.setHvalue(1);
            }
        }

        Cell coreCell = Game.gameBoard.getCell(startPosition);

        coreCell.placeBuilding(new MainCore(coreCell));
        coreCell.setOwner(Game.playerColor);
        coreCell.extendArea(Game.playerColor, 5);
        coreCell.addLinks(LinkDirection.values());

        Game.primaryPane.getChildren().setAll(zoomableScrollPane);
    }

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
    private ToggleButton toggleRed;

    @FXML
    private ToggleButton toggleBlue;

    @FXML
    private ToggleButton toggleYellow;

    @FXML
    private ToggleButton toggleGreen;
}

package ic20b106.menus.main;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.LinkDirection;
import ic20b106.game.buildings.core.MainCore;
import ic20b106.util.javafx.GameBoard;
import ic20b106.util.javafx.ZoomableScrollPane;
import javafx.beans.binding.Bindings;
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

        toggleRed.styleProperty().bind(Bindings.when(toggleRed.selectedProperty())
          .then("-fx-background-color: indianred")
          .otherwise("-fx-background-color: #333333"));

        toggleBlue.styleProperty().bind(Bindings.when(toggleBlue.selectedProperty())
          .then("-fx-background-color: cornflowerblue")
          .otherwise("-fx-background-color: #333333"));

        toggleYellow.styleProperty().bind(Bindings.when(toggleYellow.selectedProperty())
          .then("-fx-background-color: orange")
          .otherwise("-fx-background-color: #333333"));

        toggleGreen.styleProperty().bind(Bindings.when(toggleGreen.selectedProperty())
          .then("-fx-background-color: forestgreen")
          .otherwise("-fx-background-color: #333333"));
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
        Game.gameBoard = new GameBoard((int)boardWidthSlider.getValue(), (int)boardHeightSlider.getValue());

        Toggle selectedToggle = colorPicker.getSelectedToggle();
        if (selectedToggle == toggleRed) {
            Game.playerColor = Color.INDIANRED;
        } else if (selectedToggle == toggleBlue) {
            Game.playerColor = Color.CORNFLOWERBLUE;
        } else if (selectedToggle == toggleYellow) {
            Game.playerColor = Color.ORANGE;
        } else {
            Game.playerColor = Color.FORESTGREEN;
        }

        Cell coreCell = Game.gameBoard.getCell(5, 5);

        coreCell.placeBuilding(new MainCore(coreCell));
        coreCell.setOwner(Game.playerColor);
        coreCell.addLinks(LinkDirection.values());


        coreCell.extendArea(Game.playerColor, 5);

        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(Game.gameBoard, MouseButton.SECONDARY);
        zoomableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoomableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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
    private ToggleButton toggleRed;

    @FXML
    private ToggleButton toggleBlue;

    @FXML
    private ToggleButton toggleYellow;

    @FXML
    private ToggleButton toggleGreen;
}

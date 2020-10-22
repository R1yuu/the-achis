package ic20b106.menus.main;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.LinkDirection;
import ic20b106.game.buildings.core.MainCore;
import ic20b106.util.javafx.GameBoard;
import ic20b106.util.javafx.ZoomableScrollPane;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

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
        // Slider Step from Stackoverflow: https://stackoverflow.com/a/38682583
        boardWidthSlider.valueProperty().addListener((obs, oldval, newVal) ->
                boardWidthSlider.setValue((int) Math.round(newVal.doubleValue())));
        boardHeightSlider.valueProperty().addListener((obs, oldval, newVal) ->
                boardHeightSlider.setValue((int) Math.round(newVal.doubleValue())));
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

        int coreX = ThreadLocalRandom.current().nextInt(0, 40);
        int coreY = ThreadLocalRandom.current().nextInt(0, 40);

        Cell coreCell = Game.gameBoard.getCell(5, 5);

        coreCell.placeBuilding(new MainCore(coreCell));
        coreCell.addLinks(LinkDirection.values());


        coreCell.extendArea(Color.LIGHTCYAN, 5);

        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(Game.gameBoard, MouseButton.SECONDARY);
        zoomableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoomableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Game.primaryPane.getChildren().setAll(zoomableScrollPane);
    }

    @FXML
    private Slider boardWidthSlider;

    @FXML
    private Slider boardHeightSlider;
}

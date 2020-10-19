package ic20b106.menus.main;

import ic20b106.game.Cell;
import ic20b106.game.buildings.core.MainCore;
import ic20b106.util.javafx.GameBoard;
import ic20b106.game.GameStage;
import ic20b106.util.javafx.ZoomableScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
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
     *
     * @throws IOException Thrown by FXMLLoader if .fxml not found
     */
    @FXML
    private void backToMainMenu() throws IOException {
        VBox mainMenu = FXMLLoader.load(getClass().getResource("main/MainMenu.fxml"));
        GameStage.mainGameStage.setContent(mainMenu);
    }

    /**
     * Creates the Game Board
     */
    @FXML
    private void createGame() {
        GameStage.gameBoard = new GameBoard((int)boardWidthSlider.getValue(), (int)boardHeightSlider.getValue());

        int coreX = ThreadLocalRandom.current().nextInt(0, 40);
        int coreY = ThreadLocalRandom.current().nextInt(0, 40);

        System.out.println("X: " + coreX + ", Y: " + coreY);

        Cell coreCell = GameStage.gameBoard.getCell(5, 5);

        coreCell.setBuilding(new MainCore(coreCell));
        coreCell.extendArea(Color.LIGHTCYAN, 5);

        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(GameStage.gameBoard, MouseButton.SECONDARY);
        zoomableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoomableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GameStage.mainGameStage.setContent(zoomableScrollPane);
    }

    @FXML
    private Slider boardWidthSlider;

    @FXML
    private Slider boardHeightSlider;
}

package ic20b106.menus;

import ic20b106.board.Board;
import ic20b106.board.ZoomableScrollPane;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
        VBox mainMenu = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        backButton.getScene().setRoot(mainMenu);
    }

    /**
     * Creates the Game Board
     */
    @FXML
    private void createGame() {


        Board.createBoard((int)boardWidthSlider.getValue(), (int)boardHeightSlider.getValue());

        ZoomableScrollPane sp = new ZoomableScrollPane(Board.gameBoard, MouseButton.SECONDARY);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        createGameButton.getScene().setRoot(sp);
    }

    @FXML
    private Button backButton;

    @FXML
    private Slider boardWidthSlider;

    @FXML
    private Slider boardHeightSlider;

    @FXML
    private Button createGameButton;
}

package ic20b106.menus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

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
     * Creates the Game Board
     */
    @FXML
    private void createGame() {

    }

    @FXML
    private Slider boardWidthSlider;

    @FXML
    private Slider boardHeightSlider;

    @FXML
    private Button createGameButton;
}

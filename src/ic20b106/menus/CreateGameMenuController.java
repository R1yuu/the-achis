package ic20b106.menus;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class CreateGameMenuController {

    public CreateGameMenuController() {}

    @FXML
    private void initialize() {
        // Slider Step from Stackoverflow: https://stackoverflow.com/a/38682583
        boardWidth.valueProperty().addListener((obs, oldval, newVal) ->
                boardWidth.setValue((int) Math.round(newVal.doubleValue())));
        boardHeight.valueProperty().addListener((obs, oldval, newVal) ->
                boardHeight.setValue((int) Math.round(newVal.doubleValue())));
    }

    @FXML
    private Slider boardWidth;

    @FXML
    private Slider boardHeight;
}

package ic20b106.client.game.menus;

import ic20b106.client.Game;
import ic20b106.shared.PlayerColor;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Controller Class of CreateLobbyMenu.fxml
 */
public class CreateLobbyMenuController {

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
}

package ic20b106.fxml.game;

import ic20b106.Game;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EscapeMenuController {

    @FXML
    private void initialize() {
        escapeMenu.setBackground(new Background(
          new BackgroundFill(new Color(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));

        Game.escapeMenuOpen = true;

        escapeMenuBox = escapeMenu;
    }

    @FXML
    private void backToGame() {
        closeEscapeMenu();
    }

    @FXML
    private void exitGame() {
        Game.resetGame();
    }

    public static void closeEscapeMenu() {
        Game.primaryPane.getChildren().remove(escapeMenuBox);

        Game.escapeMenuOpen = false;
    }

    @FXML
    private VBox escapeMenu;

    public static VBox escapeMenuBox;
}

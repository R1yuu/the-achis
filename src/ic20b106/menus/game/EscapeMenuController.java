package ic20b106.menus.game;

import ic20b106.game.GameStage;
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

        GameStage.escapeMenuOpen = true;

        escapeMenuBox = escapeMenu;
    }

    @FXML
    private void backToGame() {
        closeEscapeMenu();
    }

    public static void closeEscapeMenu() {
        GameStage.mainGameStage.removeContent(escapeMenuBox);
        GameStage.escapeMenuOpen = false;
    }

    @FXML
    private VBox escapeMenu;

    public static VBox escapeMenuBox;
}

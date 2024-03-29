package ic20b106.client.game.menus;

import ic20b106.client.Game;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Controller Class of EscapeMenu.fxml
 */
public class EscapeMenuController {

    @FXML
    private VBox escapeMenu;

    public static VBox escapeMenuBox;

    /**
     * Sets Escape Menu
     */
    @FXML
    private void initialize() {
        escapeMenu.setBackground(new Background(
          new BackgroundFill(new Color(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));

        Game.escapeMenuOpen = true;

        escapeMenuBox = escapeMenu;
    }

    /**
     * Exits the Esacape Menu
     */
    @FXML
    private void backToGame() {
        closeEscapeMenu();
    }

    /**
     * Exits the game
     */
    @FXML
    private void exitGame() {
        Game.resetGame();
    }

    /**
     * closes the Escape menu
     */
    public static void closeEscapeMenu() {
        Game.primaryPane.getChildren().remove(escapeMenuBox);

        Game.escapeMenuOpen = false;
    }
}

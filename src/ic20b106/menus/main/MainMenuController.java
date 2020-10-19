package ic20b106.menus.main;

import ic20b106.game.GameStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Controller Class of MainMenu.fxml
 */
public class MainMenuController {

    /**
     * Opens the CreateGame Menu when onMouseClicked is triggered
     *
     * @throws IOException Thrown by FXMLLoader if .fxml not found
     */
    @FXML
    private void newGame() throws IOException {
        VBox createGameMenu = FXMLLoader.load(getClass().getResource("CreateGameMenu.fxml"));
        GameStage.mainGameStage.setContent(createGameMenu);
    }

    /**
     * Exits the Game
     */
    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}

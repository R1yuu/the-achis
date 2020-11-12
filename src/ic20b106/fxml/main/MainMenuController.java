package ic20b106.fxml.main;

import ic20b106.Game;
import ic20b106.util.javafx.eventhandler.ButtonSFXEventHandler;
import ic20b106.util.javafx.eventhandler.SFXEventHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Controller Class of MainMenu.fxml
 */
public class MainMenuController {

    @FXML
    private void initialize() {
        Game.setAllButtonSFXOnAction(mainBox);
        if (createGame.getOnAction() instanceof SFXEventHandler) {
            SFXEventHandler<ActionEvent> sfxEventHandler = ((SFXEventHandler<ActionEvent>) createGame.getOnAction());
            sfxEventHandler.setEventHandler(new ButtonSFXEventHandler<>());
        }

        createGame.setOnAction(new ButtonSFXEventHandler<>(this::newGame));
        options.setOnAction(new ButtonSFXEventHandler<>(this::options));
    }

    private void newGame(ActionEvent actionEvent) {
        try {
            VBox createGameMenu = FXMLLoader.load(getClass().getResource("CreateGameMenu.fxml"));
            Game.primaryPane.getChildren().setAll(createGameMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void options(ActionEvent actionEvent) {
        try {
            VBox optionsMenu = FXMLLoader.load(getClass().getResource("OptionsMenu.fxml"));
            Game.primaryPane.getChildren().setAll(optionsMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the Game
     */
    @FXML
    private void exitApplication() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private VBox mainBox;

    @FXML
    private Button createGame;

    @FXML
    private Button options;
}

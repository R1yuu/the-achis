package ic20b106.client.game.menus;

import ic20b106.client.Game;
import ic20b106.client.manager.LoadingTask;
import ic20b106.client.manager.NetworkManager;
import ic20b106.client.util.javafx.eventhandler.ButtonSFXEventHandler;
import ic20b106.client.util.javafx.eventhandler.SFXEventHandler;
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
    private VBox mainBox;

    @FXML
    private Button createGame;

    @FXML
    private Button lobbyList;

    @FXML
    private Button options;

    /**
     * Initializes MainMenu
     */
    @FXML
    private void initialize() {
        Game.setAllButtonSFXOnAction(mainBox);
        if (createGame.getOnAction() instanceof SFXEventHandler) {
            SFXEventHandler<ActionEvent> sfxEventHandler = ((SFXEventHandler<ActionEvent>) createGame.getOnAction());
            sfxEventHandler.setEventHandler(new ButtonSFXEventHandler<>());
        }

        createGame.setOnAction(new ButtonSFXEventHandler<>(this::openLobby));
        lobbyList.setOnAction(new ButtonSFXEventHandler<>(this::lobbyList));
        options.setOnAction(new ButtonSFXEventHandler<>(this::options));


    }

    /**
     * Opens a new Lobby
     *
     * @param actionEvent Action Event
     */
    private void openLobby(ActionEvent actionEvent) {

        new LoadingTask(() -> {
            try {
                NetworkManager networkManager = NetworkManager.getInstance();

                if (networkManager != null) {
                    networkManager.serverStub.createRoom();
                    Game.roomOwner = true;
                    VBox lobbyMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/LobbyMenu.fxml"));
                    Platform.runLater(() -> Game.primaryPane.getChildren().setAll(lobbyMenu));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).startInThread();
    }

    /**
     * Opens the Lobby List
     *
     * @param actionEvent Action Event
     */
    private void lobbyList(ActionEvent actionEvent) {
        new LoadingTask(() -> {
            try {
                if (NetworkManager.getInstance() != null) {
                    VBox lobbyListMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/LobbyList.fxml"));
                    Platform.runLater(() -> Game.primaryPane.getChildren().setAll(lobbyListMenu));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).startInThread();
    }

    /**
     * Opens the Options Menu
     *
     * @param actionEvent Action Event
     */
    private void options(ActionEvent actionEvent) {
        try {
            VBox optionsMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/OptionsMenu.fxml"));
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
        Game.close();
    }
}

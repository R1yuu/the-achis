package ic20b106;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class MenuController {

    public MenuController() {}

    @FXML
    private void initialize() {}

    @FXML
    private void createGame() throws IOException {
        VBox createGameMenu = FXMLLoader.load(getClass().getResource("menus/CreateGameMenu.fxml"));
        createGameButton.getScene().setRoot(createGameMenu);
    }

    @FXML
    private Button createGameButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button exitButton;
}

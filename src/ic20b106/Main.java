package ic20b106;

import ic20b106.game.GameStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A The Settlers 1/2 and Achikaps inspired Game
 * The Settlers: https://de.wikipedia.org/wiki/Die_Siedler
 * Achikaps: https://achikaps.fandom.com/wiki/Achikaps_Wiki
 */
public class Main extends Application {

    /**
     * Start Method of JavaFX
     *
     * @param primaryStage Main Window of the Game
     * @throws IOException Thrown by FXMLLoader if .fxml not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        GameStage mainGameStage = new GameStage(primaryStage, 1280, 720);
        GameStage.mainGameStage = mainGameStage;

        VBox mainMenu = FXMLLoader.load(getClass().getResource("menus/main/MainMenu.fxml"));

        mainGameStage.setTitle("The Achis");
        mainGameStage.setContent(mainMenu);
        mainGameStage.show();
    }


    /**
     * Main Method of Program
     *
     * @param args Console Parameters
     */
    public static void main(String[] args) {
        launch(args);
    }
}

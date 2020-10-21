package ic20b106;

import ic20b106.game.buildings.Building;
import ic20b106.game.menus.BuildMenu;
import ic20b106.game.menus.BuildingMenu;
import ic20b106.util.javafx.GameBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
public class Game extends Application {

    /**
     * Start Method of JavaFX
     *
     * @param primaryStage Main Window of the Game
     * @throws IOException Thrown by FXMLLoader if .fxml not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        VBox mainMenu = FXMLLoader.load(getClass().getResource("menus/main/MainMenu.fxml"));

        primaryPane.getChildren().setAll(mainMenu);

        primaryStage.setTitle("The Achis");
        primaryStage.setScene(new Scene(primaryPane, 1280, 720));

        primaryStage.show();
    }


    /**
     * Main Method of Program
     *
     * @param args Console Parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static StackPane primaryPane = new StackPane();

    public static BuildMenu activeBuildMenu;
    public static BuildingMenu activeBuildingMenu;
    public static GameBoard gameBoard;
    public static Building currentlyBuilt;
    public static boolean escapeMenuOpen = false;
}

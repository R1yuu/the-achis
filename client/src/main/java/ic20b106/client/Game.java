package ic20b106.client;

import ic20b106.shared.PlayerColor;
import ic20b106.shared.PlayerStartPosition;
import ic20b106.client.game.board.Cell;
import ic20b106.client.manager.AudioManager;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.manager.FileManager;
import ic20b106.client.game.menus.submenus.SubMenu;
import ic20b106.client.util.javafx.GameBoard;
import ic20b106.client.util.javafx.eventhandler.ButtonSFXEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

        mainMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/MainMenu.fxml"));
        AudioManager audioManager = AudioManager.getInstance();

        primaryPane.getChildren().setAll(mainMenu, new MediaView(audioManager.getBackgroundMediaPlayer()));

        Scene primaryScene = new Scene(primaryPane, 1280, 720);

        primaryStage.setTitle("The Achis");
        primaryStage.setScene(primaryScene);

        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
        primaryStage.show();

        FileManager.getInstance().readOptions();
    }

    public static void resetGame() {
        primaryPane.getChildren().clear();
        primaryPane.getChildren().setAll(mainMenu);

        activeSubMenu = null;
        gameBoard = null;
        currentlyBuilt = null;
        escapeMenuOpen = false;
        roomOwner = false;
    }

    /**
     * Main Method of Program
     *
     * @param args Console Parameters
     */
    public static void main(String[] args) {
        Application.launch(args);
    }


    public static void setAllButtonSFXOnAction(Parent parent) {
        Game.setAllButtonSFXOnAction(parent, List.of(ButtonBase.class));
    }

    public static void setAllButtonSFXOnAction(Parent parent, List<Class<? extends ButtonBase>> checkClasses) {
        for (Node child : parent.getChildrenUnmodifiable()) {
            for (Class<? extends ButtonBase> checkClass : checkClasses) {
                if (checkClass.isInstance(child)) {
                    checkClass.cast(child).setOnAction(new ButtonSFXEventHandler<>());
                } else if (child instanceof Parent) {
                    Game.setAllButtonSFXOnAction((Parent)child, checkClasses);
                }
            }
        }
    }


    private static VBox mainMenu;

    public static StackPane primaryPane = new StackPane();

    public static Building currentlyBuilt;

    public static SubMenu activeSubMenu;
    public static GameBoard gameBoard;
    public static PlayerColor playerColor;
    public static PlayerStartPosition playerStartPosition;
    public static Cell playerCoreCell;
    public static boolean escapeMenuOpen = false;
    public static boolean roomOwner = false;

}

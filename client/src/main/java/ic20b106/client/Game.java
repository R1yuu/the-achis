package ic20b106.client;

import ic20b106.client.manager.NetworkManager;
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
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A The Settlers 1/2 and Achikaps inspired Game
 * The Settlers: https://de.wikipedia.org/wiki/Die_Siedler
 * Achikaps: https://achikaps.fandom.com/wiki/Achikaps_Wiki
 */
public class Game extends Application {

    private static VBox mainMenu;
    public static StackPane primaryPane = new StackPane();
    public static Building currentlyBuilt;
    public static SubMenu activeSubMenu;
    public static GameBoard gameBoard;
    public static PlayerColor playerColor;
    public static Cell playerCoreCell;
    public static boolean escapeMenuOpen = false;
    public static boolean roomOwner = false;
    public static VBox loadingBox;
    public static int cellSize = 50;
    public static int resolution = 200;

    /**
     * Start Method of JavaFX
     *
     * @param primaryStage Main Window of the Game
     * @throws IOException Thrown by FXMLLoader if .fxml not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        mainMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/MainMenu.fxml"));

        AudioManager.getInstance();
        primaryPane.getChildren().setAll(mainMenu);
        primaryPane.setBackground(new Background(new BackgroundImage(
          new Image(getClass().getResource("/images/main-menu-background.png").toString()),
          BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
          new BackgroundSize(100, 100, true, true, true, true))));

        Scene primaryScene = new Scene(primaryPane, 1280, 720);

        primaryStage.setTitle("The Achis");
        primaryStage.setScene(primaryScene);

        // TODO: Correctly End Program on exit
        primaryStage.setOnCloseRequest(windowEvent -> close());
        primaryStage.show();

        FileManager.getInstance().readOptions();
    }

    /**
     * Closes the Game properly
     */
    public static void close() {
        NetworkManager.closeIfExists();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Sets the Loading Screen Bound to a specific Progress
     *
     * @param progressProperty Progress to bind the Loading Screen to
     */
    public static void openLoadingScreen(ReadOnlyDoubleProperty progressProperty) {
        Logger.getGlobal().info("Loading...");
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.toFront();
        centerBox.setBackground(new Background(
          new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        ProgressIndicator progressIndicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.progressProperty().bind(progressProperty);
        centerBox.getChildren().setAll(progressIndicator);
        loadingBox = centerBox;
        primaryPane.getChildren().add(loadingBox);
    }

    /**
     * Resets the Client to its default State in the MainMenu
     */
    public static void resetGame() {
        Logger.getGlobal().info("Resetting Game!");
        Platform.runLater(() -> {
            primaryPane.getChildren().clear();
            primaryPane.getChildren().setAll(mainMenu);
        });

        NetworkManager.closeIfExists();
        activeSubMenu = null;
        gameBoard = null;
        currentlyBuilt = null;
        escapeMenuOpen = false;
        roomOwner = false;
    }

    /**
     * Takes PlayerColor and returns javafx Color
     *
     * @param playerColor Player Color
     * @return Javafx corresponding Color
     */
    public static Color playerToColor(PlayerColor playerColor) {
        switch (playerColor) {
            case RED -> {
                return Color.INDIANRED;
            }
            case BLUE -> {
                return Color.CORNFLOWERBLUE;
            }
            case YELLOW -> {
                return Color.ORANGE;
            }
            case GREEN -> {
                return Color.FORESTGREEN;
            }
            default -> {
                return Color.SLATEGRAY;
            }
        }
    }

    /**
     * Main Method of Program
     *
     * @param args Console Parameters
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Adds SFX to all Child Nodes of Type ButtonBase
     *
     * @param parent Root Node
     */
    public static void setAllButtonSFXOnAction(Parent parent) {
        Game.setAllButtonSFXOnAction(parent, List.of(ButtonBase.class));
    }

    /**
     * Adds SFX to all Child Nodes of the chosen Type
     *
     * @param parent Root Node
     * @param checkClasses Classes to set on
     */
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
}

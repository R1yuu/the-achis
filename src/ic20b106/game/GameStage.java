package ic20b106.game;

import ic20b106.util.javafx.GameBoard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * Contains the PrimaryStage and other important Variables
 */
public class GameStage {

    /**
     * Constructor
     *
     * @param stage Stage bound to the GameStage
     * @param width Width of the Stage
     * @param height Height of the Stage
     */
    public GameStage(Stage stage, double width, double height) {
        this.stage = stage;

        Scene gameScene = new Scene(mainPane, width, height);

        this.stage.setScene(gameScene);

    }

    /**
     * Sets the Content of the Stage
     *
     * @param content Content Nodes
     */
    public void setContent(Node... content) {
        this.mainPane.getChildren().setAll(content);
    }

    /**
     * Adds Content to the Stage
     *
     * @param content Content Nodes to be added
     */
    public void addContent(Node... content) {
        this.mainPane.getChildren().addAll(content);
    }

    /**
     * Removes Content from the Stage
     *
     * @param content Content Nodes to be removed
     */
    public void removeContent(Node... content) {
        this.mainPane.getChildren().removeAll(content);
    }

    /**
     * Sets the Title of the Stage
     *
     * @param stageTitle Title
     */
    public void setTitle(String stageTitle) {
        this.stage.setTitle(stageTitle);
    }

    /**
     * Shows the Stage on the Screen
     */
    public void show() {
        this.stage.show();
    }

    private final Stage stage;
    public StackPane mainPane = new StackPane();
    public static GameBoard gameBoard;
    public static BuildMenu activeBuildMenu;
    public static GameStage mainGameStage;
    public static boolean escapeMenuOpen = false;
}

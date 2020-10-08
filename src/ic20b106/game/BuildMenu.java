package ic20b106.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * Contains the Current BuildMenu and Cell
 */
public class BuildMenu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildMenu(Cell selectedCell) throws IOException {
        this.selectedCell = selectedCell;
        this.buildMenuBox = FXMLLoader.load(getClass().getResource("/ic20b106/menus/BuildMenu.fxml"));
        GameStage.mainGameStage.addContent(this.buildMenuBox);
    }

    /**
     * Closes and Removes Build Menu
     */
    public void close() {
        GameStage.mainGameStage.removeContent(this.buildMenuBox);
        GameStage.mainGameStage.activeBuildMenu = null;
    }

    public final VBox buildMenuBox;
    public Cell selectedCell;
}

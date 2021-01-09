package ic20b106.client.game.menus.submenus.buildings.production;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Submenu of the Factory Building
 */
public class ForesterSubMenu extends BuildingSubMenu {

    @FXML
    private HBox foresterBox;

    /**
     * Constructor
     *
     * @param selectedCell Clicked Cell
     * @param building Corresponding Building
     * @throws IOException Thrown if fxml file couldn't be found
     */
    public ForesterSubMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building);
    }

    /**
     * FXML initialize Method
     *
     * @param location FXML file location
     * @param resources FXML Node Resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (location.getFile().endsWith("PopupMenu.fxml")) {
            return;
        }

        foresterBox.getChildren().add(destroyBox);
    }
}

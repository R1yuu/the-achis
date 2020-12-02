package ic20b106.client.game.menus.submenus.buildings;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Submenu of the Barracks Building
 */
public class BarracksSubMenu extends BuildingSubMenu {

    @FXML
    private AnchorPane barracksMenu;

    /**
     * Constructor
     *
     * @param selectedCell Clicked Cell
     * @param building Corresponding Building
     * @throws IOException Thrown if fxml file couldn't be found
     */
    public BarracksSubMenu(Cell selectedCell, Building building) throws IOException {
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

        barracksMenu.getChildren().add(destroyBox);
    }
}

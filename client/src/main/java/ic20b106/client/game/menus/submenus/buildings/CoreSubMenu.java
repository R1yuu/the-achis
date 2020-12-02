package ic20b106.client.game.menus.submenus.buildings;

import ic20b106.client.Game;
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
 * Submenu of the Core Building
 */
public class CoreSubMenu extends BuildingSubMenu {

    @FXML
    private AnchorPane coreMenu;

    /**
     * Constructor
     *
     * @param selectedCell Clicked Cell
     * @param building Corresponding Building
     * @throws IOException Thrown if fxml file couldn't be found
     */
    public CoreSubMenu(Cell selectedCell, Building building) throws IOException {
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

        coreMenu.getChildren().add(destroyBox);

        for (String key : Game.gameBoard.links.keySet()) {
            StringBuilder output = new StringBuilder(key + ": [");
            for (String value : Game.gameBoard.links.get(key)) {
                output.append(value).append(", ");
            }
            output.append("]");
            System.out.println(output);
        }
    }
}

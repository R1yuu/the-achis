package ic20b106.client.game.menus.submenus;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.production.Factory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Contains the Current BuildMenu and Cell
 */
public class BuildSubMenu extends SubMenu {

    @FXML
    private Button buildFactoryButton;

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildSubMenu(Cell selectedCell) throws IOException {
        super(selectedCell, "/fxml/popup/BuildSubMenu.fxml");
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
    }

    /**
     * Places corresponding Building
     *
     * @param mouseEvent Event Triggered by a mouse action
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @FXML
    private void placeBuilding(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource().equals(buildFactoryButton)) {
            Game.currentlyBuilt = new Factory(this.selectedCell);
            this.selectedCell.placeBuilding(Game.currentlyBuilt);
            openLinkSubMenu();
        }
    }

    /**
     * Opens the corresponding Submenu
     *
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @FXML
    private void openLinkSubMenu() throws IOException {
        Game.activeSubMenu = new LinkSubMenu(selectedCell);
    }
}

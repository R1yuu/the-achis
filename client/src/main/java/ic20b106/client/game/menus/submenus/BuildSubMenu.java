package ic20b106.client.game.menus.submenus;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.production.Barracks;
import ic20b106.client.game.buildings.production.Forester;
import ic20b106.client.game.buildings.production.Quarry;
import ic20b106.client.game.buildings.production.Smeltery;
import javafx.event.ActionEvent;
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
    private Button buildForesterButton;

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
     * Places Forester
     *
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @FXML
    private void placeForester() throws IOException {
        Game.currentlyBuilt = new Forester(this.selectedCell);
        placeBuilding();
    }

    /**
     * Places Quarry
     *
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @FXML
    private void placeQuarry() throws IOException {
        Game.currentlyBuilt = new Quarry(this.selectedCell);
        placeBuilding();
    }

    /**
     * Places Forester
     *
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @FXML
    private void placeSmeltery() throws IOException {
        Game.currentlyBuilt = new Smeltery(this.selectedCell);
        placeBuilding();
    }

    /**
     * Places Quarry
     *
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @FXML
    private void placeBarracks() throws IOException {
        Game.currentlyBuilt = new Barracks(this.selectedCell);
        placeBuilding();
    }

    private void placeBuilding() throws IOException {
        this.selectedCell.placeBuilding(Game.currentlyBuilt);
        openLinkSubMenu();
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

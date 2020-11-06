package ic20b106.game.menus;

import ic20b106.Game;
import ic20b106.game.board.Cell;
import ic20b106.game.buildings.production.Factory;
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

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildSubMenu(Cell selectedCell) throws IOException {
        super(selectedCell, "/ic20b106/fxml/game/submenus/BuildSubMenu.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @FXML
    private void placeBuilding(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource().equals(buildFactoryButton)) {
            Game.currentlyBuilt = new Factory(this.selectedCell);
            this.selectedCell.placeBuilding(Game.currentlyBuilt);
            openLinkSubMenu();
        }
    }

    @FXML
    private void openLinkSubMenu() throws IOException {
        Game.activeSubMenu = new LinkSubMenu(selectedCell);
    }

    @FXML
    private Button buildFactoryButton;
}

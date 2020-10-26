package ic20b106.game.menus;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.Link;
import ic20b106.game.buildings.production.Factory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Contains the Current BuildMenu and Cell
 */
public class BuildMenu extends Menu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildMenu(Cell selectedCell) throws IOException {
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
        Game.activeMenu = new LinkMenu(selectedCell);
    }

    @FXML
    private Button buildFactoryButton;
}

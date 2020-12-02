package ic20b106.client.game.menus.submenus;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Popup Submenu of a Building
 */
public abstract class BuildingSubMenu extends SubMenu {

    protected VBox destroyBox;

    /**
     * Constructor
     *
     * @param selectedCell Clicked Cell
     * @param building Corresponding Building
     * @throws IOException Thrown if fxml file couldn't be found
     */
    public BuildingSubMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building.getMenuPath());
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

        Button destroyButton = new Button("Destroy");
        destroyButton.setOnMouseClicked(mouseEvent -> {
            Game.activeSubMenu.selectedCell.removeBuilding();
            Game.activeSubMenu.close();
        });

        destroyBox = new VBox(destroyButton);
        destroyBox.setStyle("-fx-alignment: center");
    }
}

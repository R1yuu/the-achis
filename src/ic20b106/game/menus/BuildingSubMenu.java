package ic20b106.game.menus;

import ic20b106.Game;
import ic20b106.game.board.Cell;
import ic20b106.game.buildings.Building;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class BuildingSubMenu extends SubMenu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildingSubMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building.getMenuPath());
    }

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

    protected VBox destroyBox;
}

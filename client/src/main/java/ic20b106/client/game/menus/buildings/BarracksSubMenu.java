package ic20b106.client.game.menus.buildings;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.menus.BuildingSubMenu;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BarracksSubMenu extends BuildingSubMenu {

    public BarracksSubMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (location.getFile().endsWith("PopupMenu.fxml")) {
            return;
        }

        barracksMenu.getChildren().add(destroyBox);
    }

    @FXML
    private AnchorPane barracksMenu;
}

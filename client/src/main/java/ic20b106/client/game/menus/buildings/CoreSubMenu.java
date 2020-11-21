package ic20b106.client.game.menus.buildings;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.menus.BuildingSubMenu;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CoreSubMenu extends BuildingSubMenu {

    public CoreSubMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (location.getFile().endsWith("PopupMenu.fxml")) {
            return;
        }

        coreMenu.getChildren().add(destroyBox);

        String output = "";
        for (String key : Game.gameBoard.links.keySet()) {
            output = key + ": [";
            for (String value : Game.gameBoard.links.get(key)) {
                output += value + ", ";
            }
            output += "]";
            System.out.println(output);
        }
    }

    @FXML
    private AnchorPane coreMenu;
}

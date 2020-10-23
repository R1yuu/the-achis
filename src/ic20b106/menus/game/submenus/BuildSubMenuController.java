package ic20b106.menus.game.submenus;

import ic20b106.Game;
import ic20b106.game.buildings.production.Factory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class BuildSubMenuController {

    @FXML
    private void placeBuilding(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource().equals(buildFactoryButton)) {
            Game.currentlyBuilt = new Factory(Game.activeMenu.selectedCell);
            Game.activeMenu.selectedCell.placeBuilding(Game.currentlyBuilt);
            openLinkSubMenu();
        }
    }

    @FXML
    private void openLinkSubMenu() throws IOException {
        FlowPane linkSubMenu =
          FXMLLoader.load(getClass().getResource("/ic20b106/menus/game/submenus/LinkSubMenu.fxml"));
        Game.activeMenu.setSubMenu(linkSubMenu);
    }

    @FXML
    private Button buildFactoryButton;
}
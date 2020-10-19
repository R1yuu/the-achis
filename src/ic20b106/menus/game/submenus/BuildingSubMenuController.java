package ic20b106.menus.game.submenus;

import ic20b106.game.GameStage;
import ic20b106.game.buildings.Factory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class BuildingSubMenuController {

    @FXML
    private void placeBuilding(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(buildFactoryButton)) {
            GameStage.activeBuildMenu.selectedCell.setBuilding(new Factory(GameStage.activeBuildMenu.selectedCell));
        }
    }

    @FXML
    private void openLinkSubMenu() throws IOException {
        FlowPane linkSubMenu =
          FXMLLoader.load(getClass().getResource("/ic20b106/menus/game/submenus/LinkSubMenu.fxml"));
        GameStage.activeBuildMenu.setSubMenu(linkSubMenu);
    }

    @FXML
    private Button buildFactoryButton;
}

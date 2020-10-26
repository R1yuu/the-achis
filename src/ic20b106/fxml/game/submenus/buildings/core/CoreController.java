package ic20b106.fxml.game.submenus.buildings.core;

import ic20b106.fxml.game.submenus.buildings.BuildingController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CoreController extends BuildingController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        mainCoreMenu.getChildren().add(destroyBox);
    }

    @FXML
    private AnchorPane mainCoreMenu;
}

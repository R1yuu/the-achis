package ic20b106.fxml.game.submenus.buildings.production;

import ic20b106.fxml.game.submenus.buildings.BuildingController;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FactoryController extends BuildingController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        factoryMenu.getChildren().add(destroyBox);
    }

    @FXML
    private HBox factoryMenu;
}

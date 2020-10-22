package ic20b106.menus.game.submenus.buildings;

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

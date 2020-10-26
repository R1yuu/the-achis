package ic20b106.fxml.game.submenus.buildings;

import ic20b106.Game;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BuildingController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button destroyButton = new Button("Destroy");
        destroyButton.setOnMouseClicked(mouseEvent -> {
            Game.activeMenu.selectedCell.removeBuilding();
        });

        destroyBox = new VBox(destroyButton);
        destroyBox.setStyle("-fx-alignment: center");
    }

    protected VBox destroyBox;
}

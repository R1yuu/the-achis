package ic20b106.menus;

import ic20b106.board.GameMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * Controller Class of BuildMenu.fxml
 */
public class BuildMenuController {

    /**
     * Initializes Nodes
     */
    @FXML
    public void initialize() {
        buildMenuBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    /**
     * Closes the Build Menu
     */
    @FXML
    public void closeBuildMenu() {
        Stage buildMenuStage = (Stage)buildMenuBox.getScene().getWindow();
        buildMenuStage.close();
    }

    @FXML
    protected void placeBuilding(MouseEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        if (clickedButton.getId().equals("buildFactoryButton")) {
            Stage mainStage = (Stage)((Stage)buildMenuBox.getScene().getWindow()).getOwner();
        }
    }

    @FXML
    private HBox buildMenuBox;
}

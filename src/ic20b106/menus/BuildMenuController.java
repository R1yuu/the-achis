package ic20b106.menus;

import ic20b106.game.GameStage;
import ic20b106.util.DragBox;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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

        buildMenuBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        dragBox.setDraggable(buildMenuBox);
    }

    /**
     * Closes the Build Menu
     */
    @FXML
    public void closeBuildMenu() {
        GameStage.mainGameStage.removeContent(buildMenuBox);
        GameStage.mainGameStage.activeBuildMenu = null;
    }

    @FXML
    protected void placeBuilding(MouseEvent actionEvent) {

    }

    @FXML
    private VBox buildMenuBox;

    @FXML
    private DragBox dragBox;
}

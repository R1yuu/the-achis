package ic20b106.menus.game;

import ic20b106.game.GameStage;
import ic20b106.util.javafx.DragBox;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PopupMenuController {

    /**
     * Initializes Nodes
     */
    @FXML
    private void initialize() {
        popupMenuBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
          CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        popupMenuBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        dragBox.setDraggable(popupMenuBox);

        //submenuBox.getChildren().setAll(setBuildingSubmenu);
    }

    @FXML
    public void closePopupMenu() {
        GameStage.mainGameStage.removeContent(popupMenuBox);
    }

    public void setSubmenuBox(Node... subMenu) {
        this.submenuBox.getChildren().setAll(subMenu);
    }

    @FXML
    private VBox popupMenuBox;

    @FXML
    private DragBox dragBox;

    @FXML
    private HBox submenuBox;
}

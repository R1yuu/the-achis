package ic20b106.menus.game;

import ic20b106.Game;
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
import jfxtras.styles.jmetro.JMetroStyleClass;

public class PopupMenuController {

    /**
     * Initializes Nodes
     */
    @FXML
    private void initialize() {
        popupMenuBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
          CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        popupMenuBox.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        dragBox.setDraggable(popupMenuBox);
    }

    @FXML
    public void closePopupMenu() {
        Game.activeMenu.close();
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

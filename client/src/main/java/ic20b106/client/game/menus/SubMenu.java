package ic20b106.client.game.menus;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.util.javafx.DragBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class SubMenu implements Initializable {

    protected SubMenu(Cell selectedCell, String subMenuPath) throws IOException {

        if (Game.activeSubMenu != null) {
            if (this.getClass() == LinkSubMenu.class) {
                Game.activeSubMenu.close(false);
            } else {
                Game.activeSubMenu.close();
            }
        }

        this.selectedCell = selectedCell;

        FXMLLoader popupMenuLoader =
          new FXMLLoader(getClass().getResource("/fxml/PopupMenu.fxml"));
        popupMenuLoader.setController(this);
        this.popupMenuBox = popupMenuLoader.load();
        this.popupMenuBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        FXMLLoader subMenuLoader = new FXMLLoader(getClass().getResource(subMenuPath));
        subMenuLoader.setController(this);
        Pane subMenu = subMenuLoader.load();

        if (this.getClass() == BuildSubMenu.class) {
            submenuBox.getChildren().clear();
        }

        this.submenuBox.getChildren().add(0, subMenu);

        Game.primaryPane.getChildren().add(this.popupMenuBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.popupMenuBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
          CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.dragBox.setDraggable(this.popupMenuBox);
    }

    @FXML
    private void reset() throws IOException {
        Game.activeSubMenu = new BuildSubMenu(selectedCell);
        removeCurrentlyBuilt();
    }

    /**
     * Closes and Removes Build Menu
     */

    public void close(boolean removeCurrentlyBuilt) {
        if (removeCurrentlyBuilt) {
            removeCurrentlyBuilt();
        }
        Game.primaryPane.getChildren().remove(popupMenuBox);
        Game.activeSubMenu = null;
        Game.primaryPane.setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void close() {
        close(true);
    }

    private void removeCurrentlyBuilt() {
        if (Game.currentlyBuilt != null) {
            selectedCell.removeBuilding();
            Game.currentlyBuilt = null;
        }
    }

    public Cell selectedCell;

    @FXML
    protected VBox popupMenuBox;

    @FXML
    protected HBox submenuBox;

    @FXML
    protected VBox buttonBox;

    @FXML
    private DragBox dragBox;
}

package ic20b106.game.menus;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.util.javafx.DragBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class Menu implements Initializable {

    protected Menu(Cell selectedCell, String subMenuPath) throws IOException {

        if (Game.activeMenu != null) {
            Game.activeMenu.close();
        }

        this.selectedCell = selectedCell;

        FXMLLoader popupMenuLoader =
          new FXMLLoader(getClass().getResource("/ic20b106/fxml/game/PopupMenu.fxml"));
        popupMenuLoader.setController(this);
        this.popupMenuBox = popupMenuLoader.load();

        FXMLLoader subMenuLoader = new FXMLLoader(getClass().getResource(subMenuPath));
        subMenuLoader.setController(this);
        this.subMenu = subMenuLoader.load();

        if (this.getClass() == BuildMenu.class) {
            submenuBox.getChildren().clear();
        }

        this.submenuBox.getChildren().add(0, subMenu);

        Game.primaryPane.getChildren().add(this.popupMenuBox);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        popupMenuBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
          CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        popupMenuBox.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        dragBox.setDraggable(popupMenuBox);
    }

    @FXML
    private void reset() throws IOException {
        Game.activeMenu = new BuildMenu(selectedCell);
        if (Game.currentlyBuilt != null) {
            selectedCell.getChildren().remove(Game.currentlyBuilt.getTexture());
            Game.currentlyBuilt = null;
        }
    }

    public void setSubMenu(Pane subMenu) {
        Button backButton = new Button("Back");
        backButton.setOnMouseClicked(mouseEvent -> {

        });
        VBox backBox = new VBox(backButton);
        backBox.setStyle("-fx-padding: 5");

        backButton.setMinWidth(50);
        backButton.setMinHeight(subMenu.getPrefHeight());

        this.submenuBox.getChildren().setAll(subMenu, backBox);
    }

    /**
     * Closes and Removes Build Menu
     */
    @FXML
    public void close() {
        Game.primaryPane.getChildren().remove(popupMenuBox);
        Game.activeMenu = null;
        Game.primaryPane.setCursor(Cursor.DEFAULT);
    }

    public Cell selectedCell;
    public Pane subMenu;

    @FXML
    protected VBox popupMenuBox;

    @FXML
    private DragBox dragBox;

    @FXML
    protected HBox submenuBox;
}

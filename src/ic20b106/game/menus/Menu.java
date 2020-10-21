package ic20b106.game.menus;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.menus.game.PopupMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class Menu {

    protected Menu(Cell selectedCell, String subMenuPath) throws IOException {
        this.selectedCell = selectedCell;

        this.subMenu = FXMLLoader.load(getClass().getResource(subMenuPath));

        FXMLLoader popupMenuLoader =
          new FXMLLoader(getClass().getResource("/ic20b106/menus/game/PopupMenu.fxml"));

        this.popupMenuBox = popupMenuLoader.load();
        this.popupMenuController = popupMenuLoader.getController();

        this.popupMenuController.setSubmenuBox(subMenu);

        Game.primaryPane.getChildren().add(this.popupMenuBox);
    }

    public void setSubMenu(Pane subMenu) {
        Button backButton = new Button("Back");
        backButton.setOnMouseClicked(mouseEvent -> {
            this.popupMenuController.setSubmenuBox(this.subMenu);
            if (Game.currentlyBuilt != null) {
                selectedCell.getChildren().remove(Game.currentlyBuilt.getTexture());
                Game.currentlyBuilt = null;
            }
        });
        VBox backBox = new VBox(backButton);
        backBox.setStyle("-fx-padding: 5");

        backButton.setMinWidth(50);
        backButton.setMinHeight(subMenu.getPrefHeight());

        this.popupMenuController.setSubmenuBox(subMenu, backBox);
    }

    /**
     * Closes and Removes Build Menu
     */
    public void close() {
        popupMenuController.closePopupMenu();
        Game.activeBuildMenu = null;
    }

    public Cell selectedCell;
    protected Pane subMenu;
    protected VBox popupMenuBox;
    protected final PopupMenuController popupMenuController;
}

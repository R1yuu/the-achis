package ic20b106.game;

import ic20b106.menus.game.PopupMenuController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Contains the Current BuildMenu and Cell
 */
public class BuildMenu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildMenu(Cell selectedCell) throws IOException {
        this.selectedCell = selectedCell;

        this.subMenu = FXMLLoader.load(getClass().getResource("/ic20b106/menus/game/submenus/BuildingSubMenu.fxml"));

        FXMLLoader popupMenuLoader =
          new FXMLLoader(getClass().getResource("/ic20b106/menus/game/PopupMenu.fxml"));

        this.popupMenuBox = popupMenuLoader.load();
        this.popupMenuController = popupMenuLoader.getController();

        this.popupMenuController.setSubmenuBox(subMenu);

        GameStage.mainGameStage.addContent(this.popupMenuBox);
    }

    public void setSubMenu(Pane subMenu) {
        Button backButton = new Button("Back");
        backButton.setOnMouseClicked(mouseEvent -> {
            this.popupMenuController.setSubmenuBox(this.subMenu);
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
        GameStage.activeBuildMenu = null;
    }


    private Pane subMenu;
    private VBox popupMenuBox;
    private final PopupMenuController popupMenuController;
    public Cell selectedCell;
}

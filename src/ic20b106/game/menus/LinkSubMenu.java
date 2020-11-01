package ic20b106.game.menus;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.LinkDirection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Contains the Current BuildMenu and Cell
 */
public class LinkSubMenu extends SubMenu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public LinkSubMenu(Cell selectedCell) throws IOException {
        super(selectedCell, "/ic20b106/fxml/game/submenus/LinkSubMenu.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (location.getFile().endsWith("PopupMenu.fxml")) {
            return;
        }

        chooseLinkDirBox.setPrefWidth(this.submenuBox.getPrefWidth());

        Set<LinkDirection> existingLinks = this.selectedCell.getLinks().keySet();
        buttonPanes = new Pane[]{topLeftPane, topRightPane, leftPane, rightPane, bottomLeftPane, bottomLeftPane};

        clearButtons();

        for (LinkDirection linkDirection : LinkDirection.values()) {
            if (!existingLinks.contains(linkDirection)) {
                Cell neighbourCell = Cell.getNeighbourByCell(selectedCell, linkDirection);
                if (neighbourCell != null && neighbourCell.getOwner() == Game.playerColor) {
                    String linkDirectionStr = linkDirection.toString().replace('-', '\n');

                    Button linkButton = new Button(linkDirectionStr);
                    linkButton.setOnMouseClicked(this::createLink);
                    linkButton.setPrefHeight(60);
                    linkButton.setPrefWidth(60);
                    linkButton.setTextAlignment(TextAlignment.CENTER);

                    switch (linkDirection) {
                        case TOP_LEFT -> topLeftPane.getChildren().add(linkButton);
                        case TOP_RIGHT -> topRightPane.getChildren().add(linkButton);
                        case RIGHT -> rightPane.getChildren().add(linkButton);
                        case BOTTOM_RIGHT -> bottomRightPane.getChildren().add(linkButton);
                        case BOTTOM_LEFT -> bottomLeftPane.getChildren().add(linkButton);
                        case LEFT -> leftPane.getChildren().add(linkButton);
                    }
                }
            }
        }

        Button destroyButton = new Button("Destroy");
        destroyButton.setOnMouseClicked(mouseEvent -> {
            this.selectedCell.removeLinks(LinkDirection.values());
            this.close();
        });

        buttonBox.getChildren().add(destroyButton);
    }

    private void createLink(MouseEvent mouseEvent) {
        Button clickedButton = (Button)mouseEvent.getSource();
        LinkDirection linkDirection  = LinkDirection.stringToLinkDirection(clickedButton.getText());

        this.selectedCell.addLinks(linkDirection);

        if (Game.currentlyBuilt != null) {
            System.out.println(Game.gameBoard.findRoute(selectedCell, Game.playerCoreCell)
              .stream().map(Cell::getPosition).collect(Collectors.toList()));
            Game.currentlyBuilt = null;
        }

        Game.activeSubMenu.close(false);
    }

    private void clearButtons() {
        for (Pane buttonPane : buttonPanes) {
            if (buttonPane != null) {
                buttonPane.getChildren().clear();
            }
        }
    }

    private Pane[] buttonPanes = new Pane[6];

    @FXML
    private VBox chooseLinkDirBox;

    @FXML
    private Pane topLeftPane;

    @FXML
    private Pane topRightPane;

    @FXML
    private Pane leftPane;

    @FXML
    private Pane rightPane;

    @FXML
    private Pane bottomLeftPane;

    @FXML
    private Pane bottomRightPane;

}

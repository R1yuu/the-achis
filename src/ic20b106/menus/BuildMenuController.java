package ic20b106.menus;

import ic20b106.game.GameStage;
import ic20b106.game.LinkDirection;
import ic20b106.game.buildings.Factory;
import ic20b106.util.javafx.DragBox;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Set;

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
    private void initialize() {
        buildMenuBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        buildMenuBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        dragBox.setDraggable(buildMenuBox);

        submenuBox.getChildren().setAll(setBuildingSubmenu);
    }

    /**
     * Closes the Build Menu
     */
    @FXML
    private void closeBuildMenu() {
        GameStage.activeBuildMenu.close();
    }

    @FXML
    private void placeBuilding(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(buildFactoryButton)) {
            GameStage.activeBuildMenu.selectedCell.setBuilding(new Factory(GameStage.activeBuildMenu.selectedCell));
        }
    }

    @FXML
    private void choosePathDirection() {
        Set<LinkDirection> existingLinks = GameStage.activeBuildMenu.selectedCell.getLinks().keySet();
        chooseLinkDirSubmenu.getChildren().clear();

        for (LinkDirection linkDirection : LinkDirection.values()) {
            if (!existingLinks.contains(linkDirection)) {

                Button linkButton = new Button(linkDirection.toString());
                linkButton.setOnMouseClicked(this::createLink);
                linkButton.setId(linkDirection.toString());

                chooseLinkDirSubmenu.getChildren().add(linkButton);
            }
        }

        submenuBox.getChildren().setAll(chooseLinkDirSubmenu);
        submenuBox.getChildren().add(backButton);
    }

    @FXML
    private void createLink(MouseEvent mouseEvent) {
        Button clickedButton = (Button)mouseEvent.getSource();
        LinkDirection linkDirection  = LinkDirection.stringToLinkDirection(clickedButton.getText());

        chooseLinkDirSubmenu.getChildren().removeAll(clickedButton);

        GameStage.activeBuildMenu.selectedCell.addLink(linkDirection);
    }

    @FXML
    private void backToBuilding() {

    }

    @FXML
    private VBox buildMenuBox;

    @FXML
    private DragBox dragBox;

    @FXML
    private Button buildFactoryButton;

    @FXML
    private Button backButton;

    @FXML
    private HBox submenuBox;

    @FXML
    private HBox setBuildingSubmenu;

    @FXML
    private FlowPane chooseLinkDirSubmenu;
}

package ic20b106.menus.game.submenus;

import ic20b106.Game;
import ic20b106.game.LinkDirection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.util.Set;

public class LinkSubMenuController {
    @FXML
    private void initialize() {
        Set<LinkDirection> existingLinks = Game.activeBuildMenu.selectedCell.getLinks().keySet();
        chooseLinkDirPane.getChildren().clear();

        for (LinkDirection linkDirection : LinkDirection.values()) {
            if (!existingLinks.contains(linkDirection)) {

                Button linkButton = new Button(linkDirection.toString());
                linkButton.setOnMouseClicked(this::createLink);
                linkButton.setId(linkDirection.toString());

                chooseLinkDirPane.getChildren().add(linkButton);
            }
        }
    }

    private void createLink(MouseEvent mouseEvent) {
        Button clickedButton = (Button)mouseEvent.getSource();
        LinkDirection linkDirection  = LinkDirection.stringToLinkDirection(clickedButton.getText());

        chooseLinkDirPane.getChildren().removeAll(clickedButton);

        Game.activeBuildMenu.selectedCell.addLink(linkDirection);

        Game.activeBuildMenu.close();
    }


    @FXML
    private FlowPane chooseLinkDirPane;
}

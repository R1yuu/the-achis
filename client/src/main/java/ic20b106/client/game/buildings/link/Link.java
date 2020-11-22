package ic20b106.client.game.buildings.link;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.LinkSubMenu;

import java.io.IOException;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * A Link is a Connection between Cells/Buildings
 */
public class Link extends Building {

    /**
     * Constructor
     *
     * @param linkDirection Direction of the Link
     */
    public Link(LinkDirection linkDirection, Cell cell) {
        super("/images/buildings/Link-" + linkDirection.toString() + ".png",
          null, cell, false);


        this.linkDirection = linkDirection;
    }

    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new LinkSubMenu(this.cell, this);
    }

    @Override
    public String getMenuPath() {
        return "/fxml/Link.fxml";
    }

    public LinkDirection linkDirection;
}

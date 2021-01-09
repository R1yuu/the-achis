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

    public LinkDirection linkDirection;

    /**
     * Constructor
     *
     * @param linkDirection Direction of the Link
     */
    public Link(LinkDirection linkDirection, Cell cell) {
        super("/images/neutral/buildings/links/Link-" + linkDirection.toString() + ".png",
          null, cell, false);


        this.linkDirection = linkDirection;
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new LinkSubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/Link.fxml";
    }
}

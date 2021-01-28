package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.production.ForesterSubMenu;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Map;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Produces Energy Particles
 */
public class Forester extends Producer {

    private static final Image texture = new Image(Forester.class.getResource("/images/neutral/buildings/production/forester.png").toString(),
      Game.resolution, 0, true, false, true);

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    public Forester(Cell cell) {
        super(texture, Map.of(Material.WOOD, 2),
          null, cell);
    }

    /**
     * Prod
     */
    @Override
    protected void produce() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //Thread.sleep(135000);
                Thread.sleep(10000);
                synchronized (products) {
                    Integer producedAmount = this.products.getOrDefault(Material.WOOD, 0);
                    this.products.put(Material.WOOD, producedAmount + 1);
                }
            } catch (InterruptedException intExc) {
                break;
            }
        }
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new ForesterSubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/production/Forester.fxml";
    }
}

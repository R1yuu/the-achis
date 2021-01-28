package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.Soldier;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.client.game.entities.Carrier;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.production.BarracksSubMenu;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Produces Energy Particles
 */
public class Barracks extends Producer {

    private static final Image texture = new Image(Barracks.class.getResource("/images/neutral/buildings/production/barracks.png").toString(),
      Game.resolution, 0, true, false, true);

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    public Barracks(Cell cell) {
        super(texture, Map.of(Material.WOOD, 4, Material.ROCK, 2),
          null, cell);
    }

    @Override
    protected void produce() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //Thread.sleep(45000);
                Thread.sleep(10000);
                synchronized (this.storage) {
                    Integer storedMetalAmount;
                    if ((storedMetalAmount = this.storage.getOrDefault(Material.METAL, 0)) > 0) {
                        this.storage.put(Material.METAL, storedMetalAmount - 1);
                        synchronized (this.products) {
                            Integer producedPrivateAmount = this.products.getOrDefault(Soldier.PRIVATE, 0);
                            this.products.put(Soldier.PRIVATE, producedPrivateAmount + 1);
                        }
                    } else {
                        Queue<Storable> supplies = new LinkedList<>();
                        supplies.add(Material.METAL);
                        Carrier gatherer = new Carrier(
                          Game.playerHQ.getCell(), this.cell,
                          Game.playerHQ.getStorage(), this.getStorage(),
                          supplies);
                        gatherer.start();
                    }
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
        return new BarracksSubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/production/Barracks.fxml";
    }

}

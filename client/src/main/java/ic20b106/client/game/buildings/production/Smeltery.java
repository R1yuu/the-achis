package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.client.game.entities.Carrier;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.production.SmelterySubMenu;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Smelts Rock into Metal
 */
public class Smeltery extends Producer {

    private static final Image texture = new Image(Smeltery.class.getResource("/images/neutral/buildings/production/smeltery.png").toString(),
      Game.resolution, 0, true, false, true);

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    public Smeltery(Cell cell) {
        super(texture, Map.of(Material.WOOD, 2, Material.ROCK, 3),
          null, cell);
    }

    @Override
    protected void produce() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //Thread.sleep(45000);
                Thread.sleep(10000);
                synchronized (this.storage) {
                    Integer storedRockAmount;
                    if ((storedRockAmount = this.storage.getOrDefault(Material.ROCK, 0)) > 0) {
                        this.storage.put(Material.ROCK, storedRockAmount - 1);
                        synchronized (this.products) {
                            Integer producedMetalAmount = this.products.getOrDefault(Material.METAL, 0);
                            this.products.put(Material.METAL, producedMetalAmount + 1);
                        }
                    } else {
                        Queue<Storable> supplies = new LinkedList<>();
                        supplies.add(Material.ROCK);
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
        return new SmelterySubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/production/Smeltery.fxml";
    }


}

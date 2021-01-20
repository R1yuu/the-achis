package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Headquarters;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.production.SmelterySubMenu;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;

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
        super(texture,
          new HashMap<>(){{
              put(Material.WOOD, 2);
              put(Material.ROCK, 3);
          }}, null,
          cell);
    }

    @Override
    protected void produce() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(45000);
                synchronized (this.storedMaterials) {
                    Integer storedRockAmount;
                    if ((storedRockAmount = this.storedMaterials.getOrDefault(Material.ROCK, 0)) > 0) {
                        this.storedMaterials.put(Material.ROCK, storedRockAmount - 1);
                        synchronized (this.producedMaterials) {
                            Integer producedMetalAmount = this.producedMaterials.getOrDefault(Material.METAL, 0);
                            this.producedMaterials.put(Material.METAL, producedMetalAmount + 1);
                        }
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

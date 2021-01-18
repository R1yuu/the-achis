package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Headquarters;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.production.ForesterSubMenu;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;

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
        super(texture,
          new HashMap<>(){{
              put(Material.WOOD, 2);
          }},
          cell);
    }

    @Override
    protected void produce() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //Thread.sleep(135000);
                Thread.sleep(10000);
                synchronized (producedMaterials) {
                    Integer producedAmount = this.producedMaterials.getOrDefault(Material.WOOD, 0);
                    this.producedMaterials.put(Material.WOOD, producedAmount + 1);
                }
                synchronized (Game.playerHQ.getNeededMaterials()) {
                    Integer neededAmount = Game.playerHQ.getNeededMaterials().getOrDefault(Material.WOOD, 0);
                    Game.playerHQ.getNeededMaterials().put(Material.WOOD, neededAmount + 1);
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

package ic20b106.game.buildings.defense;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Material;
import ic20b106.game.menus.BuildingMenu;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;

public class Defense extends Building {

    protected Defense(Image texture, HashMap<Material, Integer> buildingCost, Cell cell, int area) {
        super(texture, buildingCost, cell);
        cell.extendArea(Color.LIGHTCYAN, area);
    }

    @Override
    public void openMenu(Cell selectedCell) throws IOException {
        Game.activeBuildingMenu =
          new BuildingMenu(selectedCell,"/ic20b106/menus/game/submenus/buildings/MainCore.fxml");
    }
}

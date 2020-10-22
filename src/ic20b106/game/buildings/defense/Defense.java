package ic20b106.game.buildings.defense;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;

public abstract class Defense extends Building {

    protected Defense(Image texture, HashMap<Material, Integer> buildingCost, Cell cell, int area) {
        super(texture, buildingCost, cell);
        cell.extendArea(Color.LIGHTCYAN, area);
    }
}

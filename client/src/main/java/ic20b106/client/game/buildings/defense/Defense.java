package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;

public abstract class Defense extends Building {

    protected Defense(String texturePath, HashMap<Material, Integer> buildingCost, Cell cell, int area) {
        super(texturePath, buildingCost, cell);
        cell.extendArea(Color.LIGHTCYAN, area);
    }
}
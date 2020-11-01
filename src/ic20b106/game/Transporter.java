package ic20b106.game;

import ic20b106.Game;
import ic20b106.game.buildings.Material;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class Transporter implements Runnable {

    public Transporter(Cell from, Cell to, Material cargo) {
        this.from = from;
        this.to = to;
        this.cargo = cargo;
    }

    @Override
    public void run() {
        List<Cell> transportPath = Game.gameBoard.findRoute(from , to);

        Cell firstCell = transportPath.get(0);

        Circle transportTexture = new Circle(20, Color.BLACK);
        Game.gameBoard.getChildren().add(transportTexture);
        transportTexture.setCenterX(firstCell.getTranslateX());
        transportTexture.setCenterY(firstCell.getTranslateY());
    }

    public Material getCargo() {
        return cargo;
    }

    private final Cell from;
    private final Cell to;
    private final Material cargo;
}

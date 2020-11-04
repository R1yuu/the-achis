package ic20b106.game;

import ic20b106.Game;
import ic20b106.game.buildings.Material;
import ic20b106.util.IntPair;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

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

        Bounds currCellBounds = from.getBoundsInParent();

        Path transitionPath = new Path();

        Circle transportTexture = new Circle(20, Color.BLACK);
        transportTexture.setLayoutX(currCellBounds.getMinX());
        transportTexture.setLayoutY(currCellBounds.getMinY());
        Game.gameBoard.getChildren().add(transportTexture);

        transitionPath.getElements().add(new MoveTo(currCellBounds.getMinX() + 5, currCellBounds.getMinY()));

        for (Cell currPathCell : transportPath.subList(1, transportPath.size())) {
            currCellBounds = currPathCell.getBoundsInParent();
            transitionPath.getElements().add(new LineTo(currCellBounds.getMinX() + 5, currCellBounds.getMinY()));
        }

        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(10));
        pt.setNode(transportTexture);
        pt.setPath(transitionPath);
        pt.play();
    }

    public Material getCargo() {
        return cargo;
    }

    private final Cell from;
    private final Cell to;
    private final Material cargo;
}

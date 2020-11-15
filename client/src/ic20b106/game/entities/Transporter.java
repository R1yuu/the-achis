package ic20b106.game.entities;

import ic20b106.Game;
import ic20b106.game.board.Cell;
import ic20b106.game.buildings.Material;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        ImageView transportTexture = new ImageView(new Image("/images/entities/transporter.png"));
        transportTexture.setFitWidth(50);
        transportTexture.setFitHeight(50);

        transportTexture.setLayoutX(currCellBounds.getMinX() + transportTexture.getFitWidth() / 2);
        transportTexture.setLayoutY(currCellBounds.getMinY() + transportTexture.getFitHeight() / 2);

        Game.gameBoard.getChildren().add(transportTexture);


        //Todo: MoveTo Korrigieren

        transitionPath.getElements().add(new MoveTo(currCellBounds.getMinX() + 5, currCellBounds.getMinY()));

        for (Cell currPathCell : transportPath.subList(1, transportPath.size())) {
            currCellBounds = currPathCell.getBoundsInParent();
            transitionPath.getElements().add(new LineTo(currCellBounds.getMinX() + 5, currCellBounds.getMinY()));
        }

        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(10));
        pt.setNode(transportTexture);
        pt.setPath(transitionPath);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pt.play();
    }

    public Material getCargo() {
        return cargo;
    }

    private final Cell from;
    private final Cell to;
    private final Material cargo;
}

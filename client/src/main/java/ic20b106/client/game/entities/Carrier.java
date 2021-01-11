package ic20b106.client.game.entities;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Transporters carry a material from Node Node to another
 */
public class Carrier implements Runnable {

    private final Cell from;
    private final Cell to;
    private final Material cargo;

    /**
     * Constructor
     *
     * @param from Start Node
     * @param to End Node
     * @param cargo Carried Material
     */
    public Carrier(Cell from, Cell to, Material cargo) {
        this.from = from;
        this.to = to;
        this.cargo = cargo;
    }

    /**
     * Thread Method
     * Calculates shortest Route using A*
     * Moves the Transporter accordingly
     */
    @Override
    public void run() {
        List<Cell> transportPath = Game.gameBoard.findRoute(from , to);

        Bounds currCellBounds = from.getBoundsInParent();

        Path transitionPath = new Path();

        Image frame0 = new Image(getClass().getResource("/images/red/entities/carrier/empty/left-0.png").toString(),
          Game.resolution, 0, true, false, false);
        Image frame1 = new Image(getClass().getResource("/images/red/entities/carrier/empty/left-1.png").toString(),
          Game.resolution, 0, true, false, false);

        ImageView transportTexture = new ImageView(frame1);
        transportTexture.setCache(false);
        transportTexture.setFitWidth(25);
        transportTexture.setFitHeight(25);

        transportTexture.setLayoutX(currCellBounds.getMinX() + transportTexture.getFitWidth() / 2);
        transportTexture.setLayoutY(currCellBounds.getMinY() + transportTexture.getFitHeight() / 2);

        Game.gameBoard.setCache(false);
        Game.gameBoard.getChildren().add(transportTexture);

        transitionPath.getElements().add(new MoveTo(currCellBounds.getMinX() + 5, currCellBounds.getMinY()));

        for (Cell currPathCell : transportPath.subList(1, transportPath.size())) {
            currCellBounds = currPathCell.getBoundsInParent();
            transitionPath.getElements().add(new LineTo(currCellBounds.getMinX() + 5, currCellBounds.getMinY()));
        }

        PathTransition pT = new PathTransition();
        pT.setDuration(Duration.seconds(10));
        pT.setNode(transportTexture);
        pT.setPath(transitionPath);
        pT.setOnFinished((actionEvent) -> System.out.println("Finished Walking"));
        pT.play();
    }

    /**
     * Getter
     *
     * @return Retungs Material cargo
     */
    public Material getCargo() {
        return cargo;
    }
}

package ic20b106.client.game.entities;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.StorageBuilding;
import ic20b106.shared.utils.IntPair;
import ic20b106.shared.utils.Pair;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Transporters carry a material from Node Node to another
 */
public class Carrier extends Thread {

    private final ImageView texture = new ImageView(
      new Image(getClass().getResource("/images/red/entities/carrier/empty/left-0.png").toString(),
        Game.resolution, 0, true, false, false));
    private final PathTransition walkPath = new PathTransition();
    private final ListIterator<Cell> transIter;
    private final Cell from;
    private final Cell to;
    private Cell currCell;
    private Cell nextCell;
    private Material cargo;
    private boolean reverse = false;

    /**
     * Constructor
     *
     * @param from Start Node
     * @param to End Node
     */
    public Carrier(Cell from, Cell to) {
        this.from = from;
        this.to = to;
        List<Cell> transportPath = Game.gameBoard.findRoute(this.from, this.to);
        this.transIter = transportPath.listIterator();

        this.texture.setSmooth(false);
        this.texture.setCache(true);
        this.texture.setCacheHint(CacheHint.SPEED);
        this.texture.setFitWidth(12);
        this.texture.setFitHeight(12);

        Game.gameBoard.getChildren().add(texture);

        walkPath.setNode(texture);
        walkPath.setOnFinished(actionEvent -> {
            this.currCell = this.nextCell;
            walk();
        });
    }

    /**
     * Calculates shortest Route using A*
     * Moves the Transporter accordingly
     */
    @Override
    public void run() {
        prepareCargo();
    }

    private void prepareCargo() {
        Building building = to.getBuilding();
        if (building != null) {
            Material neededMaterial = building.peekNeededMaterial();

            if (neededMaterial != null) {
                if (from.getBuilding() instanceof StorageBuilding) {
                    StorageBuilding storageBuilding = (StorageBuilding) from.getBuilding();
                    this.cargo = storageBuilding.popStoredMaterial(neededMaterial);
                    if (this.cargo != null) {
                        this.currCell = this.transIter.next();
                        walk();
                    }
                }
            }
        }
        if (building != null && !building.isConstructionSite()) {
            Game.gameBoard.getChildren().remove(texture);
        }
    }

    private void walk() {
        if ((!this.reverse && transIter.hasNext()) || (this.reverse && transIter.hasPrevious())) {
            if (!this.reverse) {
                this.nextCell = this.transIter.next();
            } else {
                this.nextCell = this.transIter.previous();
            }

            double pathDuration = currCell.getCellTerrain().travelTime;

            Bounds currCellBounds = currCell.getBoundsInParent();
            Bounds nextCellBounds = nextCell.getBoundsInParent();
            Pair<Double, Double> currCellCenter = new Pair<>(
              currCellBounds.getMaxX() - (currCellBounds.getWidth() / 2),
              currCellBounds.getMaxY() - currCellBounds.getHeight()
            );
            Pair<Double, Double> nextCellCenter = new Pair<>(
              nextCellBounds.getMaxX() - (nextCellBounds.getWidth() / 2),
              nextCellBounds.getMaxY() - nextCellBounds.getHeight()
            );

            Path transPath = new Path(
              new MoveTo(currCellCenter.x, currCellCenter.y),
              new LineTo(nextCellCenter.x, nextCellCenter.y)
            );

            walkPath.setDuration(Duration.seconds(pathDuration));
            walkPath.setPath(transPath);
            walkPath.play();
        } else {
            if (!this.reverse) {
                to.getBuilding().popNeededMaterial(this.cargo);
                this.cargo = null;
                this.reverse = true;
                walk();
            } else {
                this.reverse = false;
                this.prepareCargo();
            }
        }
    }
}

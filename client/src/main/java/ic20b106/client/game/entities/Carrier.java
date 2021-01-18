package ic20b106.client.game.entities;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.shared.utils.Pair;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Transporters carry a material from Node Node to another
 */
public class Carrier extends Thread {

    protected final ImageView texture = new ImageView(
      new Image(getClass().getResource("/images/red/entities/carrier/empty/left-0.png").toString(),
        Game.resolution, 0, true, false, true));
    protected final PathTransition walkPath = new PathTransition();
    protected final ListIterator<Cell> transIter;
    protected final Cell from;
    protected final Cell to;
    protected final Map<Material, Integer> source;
    protected final Map<Material, Integer> target;
    protected Cell currCell;
    protected Cell nextCell;
    protected Material cargo;
    protected boolean reverse = false;

    /**
     * Constructor
     *
     * @param from Start Node
     * @param to End Node
     */
    public Carrier(Cell from, Cell to, Map<Material, Integer> source, Map<Material, Integer> target) {
        this.from = from;
        this.to = to;
        this.source = source;
        this.target = target;
        List<Cell> transportPath = Game.gameBoard.findRoute(this.from, this.to);
        this.transIter = transportPath.listIterator();

        this.texture.setSmooth(false);
        this.texture.setCache(true);
        this.texture.setCacheHint(CacheHint.SPEED);
        this.texture.setFitWidth(12);
        this.texture.setFitHeight(12);

        walkPath.setNode(texture);
        walkPath.setOnFinished(this::onFinished);
    }

    protected void onFinished(ActionEvent actionEvent) {
        this.currCell = this.nextCell;
        walk();
    }

    /**
     * Calculates shortest Route using A*
     * Moves the Transporter accordingly
     */
    @Override
    public void run() {
        System.out.println("------------------Add Texture");
        Platform.runLater(() -> Game.gameBoard.addChild(texture));
        prepareCargo();
    }

    protected void prepareCargo() {
        Material targetMaterial = this.peekTargetMaterial();

        System.out.println(targetMaterial);

        if (targetMaterial != null) {
            if (this.popSourceMaterial(targetMaterial)) {
                this.cargo = targetMaterial;
                this.currCell = this.transIter.next();
                this.walk();
            }
        } else {
            System.out.println("--------------------Remove Texture");
            Platform.runLater(() -> Game.gameBoard.removeChild(texture));
        }
    }

    protected void walk() {
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
            System.out.println("Pfad wird abgegangen");
        } else {
            if (!this.reverse) {
                this.popTargetMaterial(this.cargo);
                this.cargo = null;
                this.reverse = true;
                walk();
            } else {
                this.taskCompleted();
            }
        }
    }

    protected void taskCompleted() {
        Platform.runLater(() -> Game.gameBoard.removeChild(texture));
    }

    /**
     * Peeks if there is a needed Material
     *
     * @return Needed Material
     */
    protected Material peekSourceMaterial() {
        synchronized (this.source) {
            for (Material material : Material.values()) {
                Integer buildMaterial = this.source.getOrDefault(material, 0);
                if (buildMaterial > 0) {
                    return material;
                }
            }
            return null;
        }
    }

    protected boolean popSourceMaterial(Material material) {
        synchronized (this.source) {
            Integer matQuantity = this.source.getOrDefault(material, 0);
            if (matQuantity > 0) {
                this.source.put(material, matQuantity - 1);
                return true;
            }
            return false;
        }
    }

    protected Material peekTargetMaterial() {
        synchronized (this.target) {
            for (Material material : Material.values()) {
                Integer buildMaterial = this.target.getOrDefault(material, 0);
                if (buildMaterial > 0) {
                    return material;
                }
            }
            return null;
        }
    }

    protected boolean popTargetMaterial(Material material) {
        synchronized (this.target) {
            Integer matQuantity = this.target.getOrDefault(material, 0);
            if (matQuantity > 0) {
                this.target.put(material, matQuantity - 1);
                return true;
            }
            return false;
        }
    }
}

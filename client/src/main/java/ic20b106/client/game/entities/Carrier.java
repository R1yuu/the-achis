package ic20b106.client.game.entities;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.client.util.javafx.SpriteAnimation;
import ic20b106.shared.utils.Pair;
import javafx.animation.Animation;
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
import java.util.Queue;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Transporters carry a material from Node Node to another
 */
public class Carrier extends Thread {

    private final ImageView texture = new ImageView();
    private final PathTransition walkPath = new PathTransition();
    private final ListIterator<Cell> transIter;
    private final Cell from;
    private final Cell to;
    private final Map<Storable, Integer> source;
    private final Map<Storable, Integer> target;
    private Cell currCell;
    private Cell nextCell;
    private Storable cargo;
    private boolean reverse = false;
    Queue<Storable> storableQueue;

    /**
     * Constructor
     *
     * @param from Start Node
     * @param to End Node
     */
    public Carrier(Cell from, Cell to, Map<Storable, Integer> source, Map<Storable, Integer> target,
                   Queue<Storable> storableQueue) {
        this.from = from;
        this.to = to;
        this.source = source;
        this.target = target;
        this.storableQueue = storableQueue;
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

    /**
     * Activates when Carrier Finishes Walking
     *
     * @param actionEvent Action Event
     */
    private void onFinished(ActionEvent actionEvent) {
        this.currCell = this.nextCell;
        walk();
    }

    /**
     * Calculates shortest Route using A*
     * Moves the Transporter accordingly
     */
    @Override
    public void run() {
        Platform.runLater(() -> Game.gameBoard.addChild(texture));
        prepareCargo();
    }

    /**
     * Prepares the Carriers Travel
     */
    private void prepareCargo() {
        Storable targetStorable = this.storableQueue.poll();

        if (targetStorable != null) {
            if (this.popSourceStorable(targetStorable)) {
                this.cargo = targetStorable;
                if (this.transIter.hasNext()) {
                    this.currCell = this.transIter.next();
                }
                this.walk();
            } else {
                Platform.runLater(() -> Game.gameBoard.removeChild(texture));
            }
        } else {
            Platform.runLater(() -> Game.gameBoard.removeChild(texture));
        }
    }

    /**
     * Walk Animation of Carrier
     */
    private void walk() {
        if ((!this.reverse && transIter.hasNext()) || (this.reverse && transIter.hasPrevious())) {
            if (!this.reverse) {
                this.nextCell = this.transIter.next();
            } else {
                this.nextCell = this.transIter.previous();
                if (this.transIter.hasPrevious()) {
                    this.nextCell = this.transIter.previous();
                }
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

            this.texture.setImage(CarrierSprites.getCarrierSprite(
              Game.playerColor,
              this.currCell.getDirectionByLinkedCell(this.nextCell),
              this.cargo
            ));

            walkPath.setDuration(Duration.seconds(pathDuration));
            walkPath.setPath(transPath);
            walkPath.play();
        } else {
            if (!this.reverse) {
                this.pushTargetStorable(this.cargo);
                this.cargo = null;
                this.reverse = true;
                walk();
            } else {
                this.taskCompleted();
            }
        }
    }

    /**
     * Carrier Finished one specific Task
     */
    private void taskCompleted() {
        if (this.reverse && !this.transIter.hasPrevious() && this.walkPath.getStatus() == Animation.Status.STOPPED) {
            this.reverse = false;
            this.prepareCargo();
        }
    }

    /**
     * Pops Storable from Source
     *
     * @param storable storable to pop
     * @return Boolean if poping was successful
     */
    private boolean popSourceStorable(Storable storable) {
        synchronized (this.source) {
            Integer matQuantity = this.source.getOrDefault(storable, 0);
            if (matQuantity > 0) {
                this.source.put(storable, matQuantity - 1);
                return true;
            }
            return false;
        }
    }

    /**
     * Pushes Storable on Target
     *
     * @param storable Storable to push
     */
    private void pushTargetStorable(Storable storable) {
        synchronized (this.target) {
            Integer matQuantity = this.target.getOrDefault(storable, 0);
            this.target.put(storable, matQuantity + 1);
        }
    }
}

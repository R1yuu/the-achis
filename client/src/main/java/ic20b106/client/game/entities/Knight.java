package ic20b106.client.game.entities;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.List;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Transporters carry a material from Node Node to another
 */
public class Knight implements Runnable {

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
    public Knight(Cell from, Cell to, Material cargo) {
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

package ic20b106.board.buildings;

import javafx.scene.image.Image;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Core is the Main Building of a Player.
 * When it gets Destroyed, the Player loses.
 */
public class Core extends Building {

    /**
     * Constructor
     *
     * @param posX Row position of the Core
     * @param posY Col Position of the Core
     */
    public Core(int posX, int posY) {
        super(new Image("/images/buildings/core.png", 50, 0, true, false, true),
                posX, posY);

    }
}

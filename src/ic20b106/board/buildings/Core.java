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
     */
    public Core() {
        super(texture);
    }


    private static final Image texture =
            new Image("/images/buildings/core.png", 50, 0, true, false, true);

}

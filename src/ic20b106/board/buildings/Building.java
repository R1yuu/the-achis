package ic20b106.board.buildings;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building extends ImageView implements Buildable {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     * @param posX Row position of the Building
     * @param posY Col position of Building
     */
    protected Building(Image texture, int posX, int posY) {
        super(texture);
        this.posX = posX;
        this.posY = posY;
        this.entranceX = posY % 2 == 0 ? posX : posX + 1;
        this.entranceY = posY + 1;
    }

    /**
     * X Position Getter
     *
     * @return Returns X Position
     */
    @Override
    public int getPosX() {
        return posX;
    }

    /**
     * Y Position Getter
     *
     * @return Returns Y Position
     */
    @Override
    public int getPosY() {
        return posY;
    }

    /**
     * X Entrance Getter
     *
     * @return Returns X Entrance
     */
    public int getEntranceX() {
        return entranceX;
    }

    /**
     * Y Entrance Getter
     *
     * @return Returns Y Entrance
     */
    public int getEntranceY() {
        return entranceY;
    }

    private int posX;
    private int posY;
    private int entranceX;
    private int entranceY;
}

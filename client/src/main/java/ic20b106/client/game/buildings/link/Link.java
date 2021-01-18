package ic20b106.client.game.buildings.link;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.LinkSubMenu;
import ic20b106.shared.PlayerColor;
import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * A Link is a Connection between Cells/Buildings
 */
public class Link extends Building {

    private static final Image redPole = new Image(
      Link.class.getResource("/images/red/buildings/links/pole.png").toString(),
      Game.resolution, 0, true, false, true);
    private static final Image bluePole = new Image(
      Link.class.getResource("/images/blue/buildings/links/pole.png").toString(),
      Game.resolution, 0, true, false, true);
    private static final Image greenPole = new Image(
      Link.class.getResource("/images/green/buildings/links/pole.png").toString(),
      Game.resolution, 0, true, false, true);
    private static final Image yellowPole = new Image(
      Link.class.getResource("/images/yellow/buildings/links/pole.png").toString(),
      Game.resolution, 0, true, false, true);

    public LinkDirection linkDirection;

    /**
     * Constructor
     *
     * @param linkDirection Direction of the Link
     */
    public Link(LinkDirection linkDirection, Cell cell) {
        super(linkDirection.texture,
          new HashMap<>(), cell, false);
        this.linkDirection = linkDirection;

        this.activeTexture.setSmooth(false);
        this.activeTexture.setCache(true);
        this.activeTexture.setCacheHint(CacheHint.SPEED);
    }

    /**
     * Gets Pole
     *
     * @param owner Cell Owner
     * @return Pole Image with correct Color
     */
    public static ImageView getPole(PlayerColor owner) {
        ImageView poleImage = new ImageView();
        switch (owner) {
            case RED -> poleImage.setImage(redPole);
            case BLUE -> poleImage.setImage(bluePole);
            case GREEN -> poleImage.setImage(greenPole);
            case YELLOW -> poleImage.setImage(yellowPole);
        }
        poleImage.setFitHeight(Game.cellSize);
        poleImage.setFitWidth(Game.cellSize);
        poleImage.setSmooth(false);
        poleImage.setCache(true);
        poleImage.setCacheHint(CacheHint.SPEED);

        return poleImage;
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new LinkSubMenu(this.cell, this);
    }

    @Override
    protected void constructionListener(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {

    }

    @Override
    public void demolish() {

    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/Link.fxml";
    }
}

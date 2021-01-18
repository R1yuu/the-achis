package ic20b106.client.game.board;

import ic20b106.client.Game;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public enum Terrain {
    GRASS(1), FLOWERS(1.5), TALL_GRASS(2);

    public final double travelTime;
    public final BackgroundImage bgImg;

    Terrain(double travelTime) {
        this.travelTime = travelTime;
        this.bgImg = new BackgroundImage(
          new Image(
            getClass().getResource("/images/neutral/background/" + name().toLowerCase() + ".png").toString(),
            Game.resolution, 0, true, false, true),
          BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
          new BackgroundSize(Game.cellSize, Game.cellSize, true, true, true, true));
    }
}

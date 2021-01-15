package ic20b106.client.game.board;

import ic20b106.client.Game;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public enum Terrain {
    GRASS(1, new BackgroundImage(
      new Image(
        Cell.class.getResource("/images/neutral/background/grass.png").toString(),
        Game.resolution, 0, true, false, true),
      BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
      new BackgroundSize(Game.cellSize, Game.cellSize, true, true, true, true))),
    FLOWERS(1.5, new BackgroundImage(
      new Image(
        Cell.class.getResource("/images/neutral/background/tall-grass.png").toString(),
        Game.resolution, 0, true, false, true),
      BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
      new BackgroundSize(Game.cellSize, Game.cellSize, true, true, true, true))),
    TALL_GRASS(2, new BackgroundImage(
      new Image(
        Cell.class.getResource("/images/neutral/background/flowers.png").toString(),
        Game.resolution, 0, true, false, true),
      BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
      new BackgroundSize(Game.cellSize, Game.cellSize, true, true, true, true)));

    public final double travelTime;
    public final BackgroundImage bgImg;

    Terrain(double travelTime, BackgroundImage bgImage) {
        this.travelTime = travelTime;
        this.bgImg = bgImage;
    }
}

package ic20b106.shared;

import java.io.Serializable;

public class PlayerProfile implements Serializable {
    public PlayerProfile(PlayerColor color, PlayerStartPosition startPosition, Boolean isReady) {
        this.color = color;
        this.startPosition = startPosition;
        this.isReady = isReady;
    }

    public final PlayerColor color;
    public final PlayerStartPosition startPosition;
    public final Boolean isReady;
}

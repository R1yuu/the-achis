package ic20b106.shared;

import java.io.Serializable;

public class PlayerProfile implements Serializable {
    public PlayerProfile(String hash, String id, PlayerColor color, PlayerStartPosition startPosition, Boolean isReady) {
        this.hash = hash;
        this.id = id;
        this.color = color;
        this.startPosition = startPosition;
        this.isReady = isReady;
    }

    public final String hash;
    public final String id;
    public final PlayerColor color;
    public final PlayerStartPosition startPosition;
    public final Boolean isReady;
}

package ic20b106.shared;

import java.io.Serializable;

public class PlayerProfile implements Serializable {
    public final String hash;
    public final String id;
    public final PlayerColor color;
    public final PlayerStartPosition startPosition;
    public final Boolean isReady;

    public PlayerProfile(String hash, String id, PlayerColor color, PlayerStartPosition startPosition, Boolean isReady) {
        this.hash = hash;
        this.id = id;
        this.color = color;
        this.startPosition = startPosition;
        this.isReady = isReady;
    }

    @Override
    public String toString() {
        return "Player-Profile(Hash: " + (hash != null ? hash : "") +
          "; Id: " + (id != null ? id : "") +
          "; Color: " + (color != null ? color.toString() : "") +
          "; Start-Position: " + (startPosition != null ? startPosition.toString() : "") +
          "; IsReady: " + (isReady != null ? isReady.toString() : "") + ")";
    }
}

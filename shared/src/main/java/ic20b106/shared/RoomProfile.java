package ic20b106.shared;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class RoomProfile implements Serializable {
    public final UUID uuid;
    public final String roomName;
    public final String roomOwner;
    public final List<PlayerColor> freeColors;
    public final List<PlayerStartPosition> freeStartPositions;

    public RoomProfile(UUID uuid, String roomName, String roomOwner,
                       List<PlayerColor> freeColors, List<PlayerStartPosition> freeStartPositions) {
        this.uuid = uuid;
        this.roomName = roomName;
        this.roomOwner = roomOwner;
        this.freeColors = freeColors;
        this.freeStartPositions = freeStartPositions;
    }

    @Override
    public String toString() {
        return "Room-Profile(UUID: " + (uuid != null ? uuid.toString() : "") +
          "; Room-Name: " + (roomName != null ? roomName : "") +
          "; Room-Owner: " + (roomOwner != null ? roomOwner : "") +
          "; Free Colors: " + (freeColors != null ? freeColors.toString() : "") +
          "; Free Start-Positions: " + (freeStartPositions != null ? freeStartPositions.toString() : "") + ")";
    }
}

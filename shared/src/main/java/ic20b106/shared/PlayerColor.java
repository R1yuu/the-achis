package ic20b106.shared;

public enum PlayerColor {

    RED, BLUE, YELLOW, GREEN;

    PlayerColor() {
    }

    public String getPlayerTexturePath() {
        return "/images/" + this.toString().toLowerCase();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static PlayerColor fromString(String str) {
        for(PlayerColor pc : values())
            if(pc.name().equalsIgnoreCase(str.strip())) {
                return pc;
            }
        throw new IllegalArgumentException("Unexpected value: " + str);
    }
}

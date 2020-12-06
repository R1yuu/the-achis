package ic20b106.shared;

import javafx.scene.paint.Color;

public enum PlayerColor {

    RED(Color.INDIANRED), BLUE(Color.CORNFLOWERBLUE),
    YELLOW(Color.ORANGE), GREEN(Color.FORESTGREEN);

    private final Color color;

    PlayerColor(Color color) {
        this.color = color;
    }

    public Color toColor() {
        return color;
    }

    public static PlayerColor fromColor(Color color) {
        for(PlayerColor pc : values())
            if(pc.color.equals(color)) {
                return pc;
            }
        throw new IllegalArgumentException("Unexpected value: " + color);
    }

    public String getRGB() {
        return (color.getRed() * 100) + "%," + (color.getGreen() * 100) + "%," + (color.getBlue() * 100) + "%";
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

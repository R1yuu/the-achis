package ic20b106.shared;

public enum PlayerStartPosition {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

    @Override
    public String toString() {
        return super.toString();
    }

    public static PlayerStartPosition fromString(String str) {
        String strFormatted = str.toLowerCase().strip();
        if (strFormatted.startsWith("top")) {
            if (strFormatted.endsWith("left")) {
                return TOP_LEFT;
            } else if (strFormatted.endsWith("right")) {
                return TOP_RIGHT;
            }
        } else if (strFormatted.startsWith("bottom")) {
            if (strFormatted.endsWith("left")) {
                return BOTTOM_LEFT;
            } else if (strFormatted.endsWith("right")) {
                return BOTTOM_RIGHT;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + str);
    }
}

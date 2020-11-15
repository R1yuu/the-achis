package ic20b106;

public enum NetworkCommandEnum {
    QUIT, CREATE, LIST, JOIN, SHOW, START;

    public static NetworkCommandEnum fromString(String str) {
        for(NetworkCommandEnum nc : values())
            if(nc.name().equalsIgnoreCase(str.strip())) {
                return nc;
            }
        throw new IllegalArgumentException("Unexpected value: " + str);
    }
}

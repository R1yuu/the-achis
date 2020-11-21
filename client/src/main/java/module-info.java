module client {
    requires shared;
    requires java.desktop;
    requires java.rmi;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports ic20b106.client;
    exports ic20b106.client.fxml.main;
    exports ic20b106.client.fxml.game;

    opens ic20b106.client               to javafx.fxml;
    opens ic20b106.client.fxml.main     to javafx.fxml;
    opens ic20b106.client.fxml.game     to javafx.fxml;
    opens ic20b106.client.game.menus    to javafx.fxml;
    opens ic20b106.client.util.javafx   to javafx.fxml;
    opens ic20b106.client.manager       to com.google.gson;

    // --module-path "C:\Program Files\Java\javafx-sdk-15\lib" --add-modules javafx.controls,javafx.fxml,javafx.media
}
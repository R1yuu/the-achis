module client {
    requires shared;
    requires java.desktop;
    requires java.rmi;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires hashids;

    exports ic20b106.client;
    exports ic20b106.client.game.menus;
    exports ic20b106.client.game.menus.submenus;
    exports ic20b106.client.game.menus.submenus.buildings;

    opens ic20b106.client                               to javafx.fxml;
    opens ic20b106.client.game.menus                    to javafx.fxml;
    opens ic20b106.client.game.menus.submenus           to javafx.fxml;
    opens ic20b106.client.game.menus.submenus.buildings to javafx.fxml;
    opens ic20b106.client.util.javafx                   to javafx.fxml;
    opens ic20b106.client.manager                       to com.google.gson;
}
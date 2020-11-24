package ic20b106.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientDatabase {

    private static ClientDatabase singleInstance = null;
    private static final String dbFilePath = System.getProperty("user.home").replace("\\", "/") +
      "/.the_achis/clients.db";
    private static final String dbPath = "jdbc:sqlite:" + dbFilePath;
    private Connection dbConnection = null;

    private ClientDatabase() {

        connect();
    }

    /**
     * Connect to a sample database
     */
    private void connect() {
        try {
            File dbFile = new File(dbFilePath);
            File externalSafeFolder = dbFile.getParentFile();
            if (!externalSafeFolder.exists() && !externalSafeFolder.mkdirs()) {
                throw new IllegalStateException("Couldn't create dir: " + externalSafeFolder);
            }

            this.dbConnection = DriverManager.getConnection(dbPath);
            initializeTables();

            System.out.println(this.dbConnection.getMetaData().getDriverName());

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeTables() throws SQLException {
        DatabaseMetaData metaData = this.dbConnection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, "client", null);
        int size = 0;
        while (resultSet.next() && size == 0) {
            size++;
        }
        if (size == 0) {
            this.updateStatement(
              "CREATE TABLE client (" +
                "uuid VARCHAR(36) PRIMARY KEY" +
              ");");
        }

        resultSet = metaData.getTables(null, null, "block", null);
        size = 0;
        while (resultSet.next() && size == 0) {
            size++;
        }
        if (size == 0) {
            this.updateStatement(
              "CREATE TABLE block (" +
                "uuid VARCHAR(36), " +
                "blocked_uuid VARCHAR(36), " +
                "PRIMARY KEY(uuid, blocked_uuid), " +
                "FOREIGN KEY(uuid) REFERENCES client(uuid), " +
                "FOREIGN KEY(blocked_uuid) REFERENCES client(uuid)" +
              ");");
        }
    }

    public void addClient(String hash) {
        try (Statement stmt = this.dbConnection.createStatement()) {
            stmt.execute(
              "INSERT INTO client(uuid) " +
                  "SELECT '" + hash + "' " +
                  "WHERE NOT EXISTS(SELECT 1 " +
                  "FROM client " +
                  "WHERE uuid = '" + hash + "')" +
              ";"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getId(String hash) {
        String id;
        try (Statement stmt = this.dbConnection.createStatement()) {
            ResultSet idSet = stmt.executeQuery(
              "SELECT rowid " +
                "FROM client " +
                "WHERE uuid = '" + hash + "';"
              );
            id = idSet.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            id = "";
        }
        return id;
    }

    public void updateStatement(String sqlQuery) {
        try (Statement stmt = this.dbConnection.createStatement()) {
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ClientDatabase getInstance() {
        if (singleInstance == null) {
            singleInstance = new ClientDatabase();
        }
        return singleInstance;
    }

}

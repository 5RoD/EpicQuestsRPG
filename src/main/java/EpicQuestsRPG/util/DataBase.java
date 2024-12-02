package EpicQuestsRPG.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private ConfigUtil configUtil;
    private String host;
    private int port;
    private String name;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;
    private boolean useSSL;
    private boolean autoReconnect;

    public DataBase(ConfigUtil configUtil) {
        this.configUtil = configUtil;
        getDatabaseInfo();
    }


    private void getDatabaseInfo() {
        this.host = configUtil.getDatabaseConfig().getString("database.host");
        this.port = configUtil.getDatabaseConfig().getInt("database.port");
        this.name = configUtil.getDatabaseConfig().getString("database.name");
        this.username = configUtil.getDatabaseConfig().getString("database.username");
        this.password = configUtil.getDatabaseConfig().getString("database.password");
        this.useSSL = configUtil.getDatabaseConfig().getBoolean("options.use-ssl");
        this.autoReconnect = configUtil.getDatabaseConfig().getBoolean("options.auto-reconnect");
    }

    public void dataConnect() {
        try {
            // Connect to MySQL server without specifying a database
            String url = "jdbc:mysql://" + host + ":" + port + "/?useSSL=" + useSSL + "&autoReconnect=" + autoReconnect;
            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS " + name;
            statement.executeUpdate(sql); // Use executeUpdate for DDL statements
            System.out.println("Database created or already exists: " + name);

        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework
        } finally {
            // Close resources in the finally block to ensure they are closed even if an exception occurs
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace(); // Consider using a logging framework
            }
        }
    }

    public void dataDisconnect() {

        if (connection != null) {

            try {
                connection.close();
                String url = "jdbc:mysql://" + host + ":" + port + "/" + name;
                System.out.println("Database " + url + " disconnected successfully");
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}
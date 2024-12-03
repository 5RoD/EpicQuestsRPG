package EpicQuestsRPG.util;

import EpicQuestsRPG.Player.PlayerManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

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
        Connection tempConnection = null;

        try {
            // Initial connection to the MySQL server (without database)
            String url = "jdbc:mysql://" + host + ":" + port + "/?useSSL=" + useSSL + "&autoReconnect=" + autoReconnect;
            tempConnection = DriverManager.getConnection(url, username, password);
            statement = tempConnection.createStatement();

            // Create the database if it doesn't exist
            String sql = "CREATE DATABASE IF NOT EXISTS " + name;
            statement.executeUpdate(sql);

            System.out.println("Database created or already exists: " + name);

            // Close the initial connection
            statement.close();
            tempConnection.close();

            // Reconnect to the specific database
            String dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=" + useSSL + "&autoReconnect=" + autoReconnect;
            connection = DriverManager.getConnection(dbUrl, username, password);
            statement = connection.createStatement();

            // Create the table if it doesn't exist
            String sqlTable = """
            CREATE TABLE IF NOT EXISTS player_data (
                uuid varchar(36) PRIMARY KEY,
                player_class VARCHAR(50) NOT NULL,
                player_current_quest VARCHAR(255),
                player_money DOUBLE DEFAULT 0.0,
                player_done_before BOOLEAN DEFAULT FALSE
            );
            """;
            statement.execute(sqlTable);
            statement.close();

            System.out.println("Table created or already exists: player_data");

        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework
        } finally {
            // Close resources in the finally block
            try {
                if (statement != null) statement.close();
                if (tempConnection != null && !tempConnection.isClosed()) tempConnection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void dataDisconnect(){

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


    public PlayerManager findStatsByUUID(UUID uuid) {

        try {
            statement = connection.createStatement();
            String getUUID = "SELECT * FROM player_data WHERE uuid = " + uuid;
            statement.executeQuery(getUUID);
        }catch (SQLException e) {
            e.printStackTrace();
        }

      return ;
    }
}
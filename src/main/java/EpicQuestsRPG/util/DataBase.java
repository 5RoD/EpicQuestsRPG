package EpicQuestsRPG.util;

import EpicQuestsRPG.Player.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

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
    private final JavaPlugin plugin;

    public DataBase(ConfigUtil configUtil, JavaPlugin plugin) {
        this.configUtil = configUtil;
        this.plugin = plugin;
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

            plugin.getLogger().info("Database created or already exists: " + name);

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
                            player_name VARCHAR(50) NOT NULL,
                            player_class VARCHAR(50) NOT NULL,
                            player_current_quest VARCHAR(255),
                            player_money DOUBLE DEFAULT 0.0,
                            player_done_before BOOLEAN DEFAULT FALSE
                        );
                    """;
            statement.execute(sqlTable);
            statement.close();

            plugin.getLogger().info("Table created or already exists: player_data");

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

    public void updatePlayerMoney(String uuid, double money) {
        try {
            // SQL query to update the player's money
            String updateMoneySQL = "UPDATE player_data SET player_money = ? WHERE uuid = ?";
            // Prepare the statement with the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(updateMoneySQL);
            // Set the money value in the query
            preparedStatement.setDouble(1, money);
            // Set the UUID value in the query
            preparedStatement.setString(2, uuid);
            // Execute the update query
            preparedStatement.executeUpdate();
            // Close the prepared statement
            preparedStatement.close();
        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace(); // Consider using a logging framework
        }
    }

    public void dataDisconnect() {
        if (connection != null) {
            try {
                connection.close();
                String url = "jdbc:mysql://" + host + ":" + port + "/" + name;
                plugin.getLogger().info("Database " + url + " disconnected successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void addPlayer(String uuid, String playerName) {
        try {
            String sql = "INSERT INTO player_data (uuid, player_name, player_class, player_current_quest, player_money, player_done_before) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, playerName);
            preparedStatement.setString(3, "default"); // Set default class or retrieve from player data
            preparedStatement.setString(4, ""); // Default current quest
            preparedStatement.setDouble(5, 0.0); // Default money
            preparedStatement.setBoolean(6, false); // Default done before
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerManager findStatsByUUID(String uuid) {
        try {
            statement = connection.createStatement();
            String getUUID = "SELECT * FROM player_data WHERE uuid = '" + uuid + "'";
            ResultSet resultSet = statement.executeQuery(getUUID);

            if (resultSet.next()) {
                // This gets the info from the database
                String player_class = resultSet.getString("player_class");
                String player_name = resultSet.getString("player_name");
                String player_current_quest = resultSet.getString("player_current_quest");
                Boolean player_done_before = resultSet.getBoolean("player_done_before");
                double player_money = resultSet.getDouble("player_money");

                PlayerManager playerManager = new PlayerManager(player_done_before, player_money, uuid, player_class, player_current_quest, player_name);
                statement.close();

                return playerManager;
            } else {
                plugin.getLogger().severe("Error! look for me @ Database#findStatsByUUID");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
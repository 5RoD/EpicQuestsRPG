package EpicQuestsRPG.util;

import EpicQuestsRPG.Player.PlayerManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class DataBase {

    private ConfigUtil configUtil;
    private String host;
    private int port;
    private String name;
    private String username;
    private String password;
    private HikariDataSource dataSource;  // HikariCP DataSource
    private final JavaPlugin plugin;

    public DataBase(ConfigUtil configUtil, JavaPlugin plugin) {
        this.configUtil = configUtil;
        this.plugin = plugin;
        getDatabaseInfo();
        setupHikariCP();  // Initialize HikariCP on startup
    }

    private void getDatabaseInfo() {
        // Get the database details from the config file
        this.host = configUtil.getDatabaseConfig().getString("database.host");
        this.port = configUtil.getDatabaseConfig().getInt("database.port");
        this.name = configUtil.getDatabaseConfig().getString("database.name");
        this.username = configUtil.getDatabaseConfig().getString("database.username");
        this.password = configUtil.getDatabaseConfig().getString("database.password");
    }

    private void setupHikariCP() {
        // Configure HikariCP connection pool
        HikariConfig config = new HikariConfig();
        String dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + name + "?useSSL=" + configUtil.getDatabaseConfig().getBoolean("options.use-ssl") + "&autoReconnect=" + configUtil.getDatabaseConfig().getBoolean("options.auto-reconnect");
        config.setJdbcUrl(dbUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10); // Number of connections in the pool (adjust as needed)
        config.setConnectionTestQuery("SELECT 1");  // Test query for validating connections
        config.setIdleTimeout(30000);  // Time before a connection is considered idle
        config.setMaxLifetime(600000);  // Max lifetime of a connection

        // Initialize the HikariDataSource (connection pool)
        dataSource = new HikariDataSource(config);
    }

    public void dataConnect() {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            // Create the database if it doesn't exist
            String sql = "CREATE DATABASE IF NOT EXISTS " + name;
            statement.executeUpdate(sql);

            plugin.getLogger().info("Database created or already exists: " + name);

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
            plugin.getLogger().info("Table created or already exists: player_data");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerMoney(String uuid, double money) {
        // SQL query to update the player's money
        String updateMoneySQL = "UPDATE player_data SET player_money = ? WHERE uuid = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateMoneySQL)) {

            // Set values
            preparedStatement.setDouble(1, money);
            preparedStatement.setString(2, uuid);

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dataDisconnect() {
        if (dataSource != null) {
            dataSource.close(); // Properly close the HikariCP data source
            plugin.getLogger().info("Database disconnected successfully.");
        }
    }

    public PlayerManager playerSearch(String player_name) {
        PlayerManager playerManager = null;
        String searchSQL = "SELECT * FROM player_data WHERE player_name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {

            preparedStatement.setString(1, player_name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String playerClass = resultSet.getString("player_class");
                String playerCurrentQuest = resultSet.getString("player_current_quest");
                boolean playerDoneBefore = resultSet.getBoolean("player_done_before");
                double playerMoney = resultSet.getDouble("player_money");

                playerManager = new PlayerManager(playerDoneBefore, playerMoney, uuid, playerClass, playerCurrentQuest, player_name);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerManager;
    }

    public void addPlayer(String uuid, String playerName) {
        String checkSQL = "SELECT * FROM player_data WHERE uuid = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {

            checkStatement.setString(1, uuid);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                String existingName = resultSet.getString("player_name");
                if (!existingName.equals(playerName)) {
                    String updateSQL = "UPDATE player_data SET player_name = ? WHERE uuid = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                        updateStatement.setString(1, playerName);
                        updateStatement.setString(2, uuid);
                        updateStatement.executeUpdate();
                    }
                }
            } else {
                String insertSQL = "INSERT INTO player_data (uuid, player_name, player_class, player_current_quest, player_money, player_done_before) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                    insertStatement.setString(1, uuid);
                    insertStatement.setString(2, playerName);
                    insertStatement.setString(3, "default");  // Default class
                    insertStatement.setString(4, "NONE");    // Default current quest
                    insertStatement.setDouble(5, 0.0);       // Default money
                    insertStatement.setBoolean(6, false);    // Default done before
                    insertStatement.executeUpdate();
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateClass(String class_name, String player_name) {
        String updateSQL = "UPDATE player_data SET player_class = ? WHERE player_name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, class_name);
            preparedStatement.setString(2, player_name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerManager findStatsByUUID(String uuid) {
        String getUUID = "SELECT * FROM player_data WHERE uuid = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUUID)) {

            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String playerClass = resultSet.getString("player_class");
                String playerName = resultSet.getString("player_name");
                String playerCurrentQuest = resultSet.getString("player_current_quest");
                boolean playerDoneBefore = resultSet.getBoolean("player_done_before");
                double playerMoney = resultSet.getDouble("player_money");

                return new PlayerManager(playerDoneBefore, playerMoney, uuid, playerClass, playerCurrentQuest, playerName);
            } else {
                plugin.getLogger().severe("Error finding stats for UUID: " + uuid);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

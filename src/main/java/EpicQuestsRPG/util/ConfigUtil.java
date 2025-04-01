package EpicQuestsRPG.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigUtil {
    private FileConfiguration config;

    private File bossesFile;
    private FileConfiguration bossesConfig;

    private File questsFile;
    private FileConfiguration questsConfig;

    private File databaseFile;
    private FileConfiguration databaseConfig;

    private File classesFile;
    private FileConfiguration classesConfig;

    private JavaPlugin plugin;

    public ConfigUtil(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
        loadBosses();
        loadQuests();
        loadDatabase();
        loadClasses();
    }

    private void loadConfig() {
        // Load the main config file
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // Save the default config if it doesn't exist
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void loadBosses() {
        // Load the bosses file
        bossesFile = new File(plugin.getDataFolder(), "bosses.yml");
        if (!bossesFile.exists()) {
            plugin.saveResource("bosses.yml", false); // Save the default bosses file if it doesn't exist
        }
        bossesConfig = YamlConfiguration.loadConfiguration(bossesFile);
    }

    private void loadQuests() {
        // Load the quests file
        questsFile = new File(plugin.getDataFolder(), "quests.yml");
        if (!questsFile.exists()) {
            plugin.saveResource("quests.yml", false); // Save the default quests file if it doesn't exist
        }
        questsConfig = YamlConfiguration.loadConfiguration(questsFile);
    }

    private void loadDatabase() {
        // Load the quests file
        databaseFile = new File(plugin.getDataFolder(), "database.yml");
        if (!databaseFile.exists()) {
            plugin.saveResource("database.yml", false); // Save the default quests file if it doesn't exist
        }
        databaseConfig = YamlConfiguration.loadConfiguration(databaseFile);
    }

    private void loadClasses() {
        // Load the quests file
        classesFile = new File(plugin.getDataFolder(), "classes.yml");
        if (!classesFile.exists()) {
            plugin.saveResource("classes.yml", false); // Save the default class file if it doesn't exist
        }
        classesConfig = YamlConfiguration.loadConfiguration(classesFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getBossesConfig() {
        return bossesConfig;
    }

    public FileConfiguration getQuestsConfig() {
        return questsConfig;
    }
    public FileConfiguration getDatabaseConfig() {
        return databaseConfig;
    }

    public FileConfiguration getClassesConfig() {
        return classesConfig;
    }
}

package EpicQuestsRPG;

import EpicQuestsRPG.Player.PlayerListener;
import EpicQuestsRPG.economy.VaultUtil;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.plugin.java.JavaPlugin;

public final class EpicQuestRPG extends JavaPlugin {

    private VaultUtil vaultUtil;
    private DataBase dataBase;

    public DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void onEnable() {
        // Register ConfigUtil
        ConfigUtil configUtil = new ConfigUtil(this);
        // Register database.java config only
        this.dataBase = new DataBase(configUtil, this);
        // Connect to database on startup
        dataBase.dataConnect();

        // Initialize VaultUtil and register commands
        vaultUtil = new VaultUtil(this); // Pass main plugin reference

        if (getCommand("eco") != null) {
            getCommand("eco").setExecutor(vaultUtil);
        } else {
            getLogger().severe("Command /eco not found in plugin.yml");
        }

        if (getCommand("ecodeposit") != null) {
            getCommand("ecodeposit").setExecutor(vaultUtil);
        } else {
            getLogger().severe("Command /ecodeposit not found in plugin.yml");
        }

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        dataBase.dataDisconnect();
    }
}
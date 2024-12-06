package EpicQuestsRPG;

import EpicQuestsRPG.Player.PlayerListener;
import EpicQuestsRPG.commands.Eco;
import EpicQuestsRPG.economy.VaultUtil;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.plugin.java.JavaPlugin;

public final class EpicQuestRPG extends JavaPlugin {

    private VaultUtil vaultUtil;
    private Eco eco;
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



        //Intialize Commands
            getCommand("eco").setExecutor(eco);
            getCommand("ecodeposit").setExecutor(eco);


        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        dataBase.dataDisconnect();
    }
}
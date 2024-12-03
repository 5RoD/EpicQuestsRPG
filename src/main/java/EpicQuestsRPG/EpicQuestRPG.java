package EpicQuestsRPG;

import EpicQuestsRPG.economy.VaultUtil;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class EpicQuestRPG extends JavaPlugin {

   // private VaultUtil vaultUtil;
    private DataBase dataBase;

    @Override
    public void onEnable() {



        // Register ConfigUtil
        ConfigUtil configUtil = new ConfigUtil(this);
        // Register database.java config only
        dataBase = new DataBase(configUtil);
        // Connect to database on startup
        dataBase.dataConnect();

        // Initialize VaultUtil and register commands
        //vaultUtil = new VaultUtil(this); // Pass main plugin reference

//        getCommand("eco").setExecutor((CommandExecutor) vaultUtil);
//        getCommand("ecodeposit").setExecutor((CommandExecutor) vaultUtil);
    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        dataBase.dataDisconnect();
    }
}

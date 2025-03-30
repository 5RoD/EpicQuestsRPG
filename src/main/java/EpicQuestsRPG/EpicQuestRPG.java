package EpicQuestsRPG;

import EpicQuestsRPG.Player.PlayerListener;
import EpicQuestsRPG.commands.Class;
import EpicQuestsRPG.commands.Eco;
import EpicQuestsRPG.commands.Gui;
import EpicQuestsRPG.economy.VaultUtil;
import EpicQuestsRPG.gui.MenuUtil;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.plugin.java.JavaPlugin;

public final class EpicQuestRPG extends JavaPlugin {

    private VaultUtil VaultUtil;
    private Eco Eco;
    private Gui Gui;
    private DataBase DataBase;
    private Class Class;


    public DataBase getDataBase() {
        return DataBase;
    }

    @Override
    public void onEnable() {
        // Register ConfigUtil
        ConfigUtil configUtil = new ConfigUtil(this);
        // Register database.java config only
        this.DataBase = new DataBase(configUtil, this);
        // Connect to database on startup
        DataBase.dataConnect();

        // Initialize VaultUtil and register commands
        VaultUtil = new VaultUtil(this); // Pass main plugin reference

        // Initialize MenuUtil
        MenuUtil menuUtil = new MenuUtil();

        // Initialize Commands
        Gui = new Gui(menuUtil);


        //Intialize Commands
        getCommand("eco").setExecutor(Eco);
        getCommand("ecodeposit").setExecutor(Eco);
        getCommand("gui").setExecutor(Gui);
        this.getCommand("classsearch").setExecutor(new Class(DataBase));


        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        DataBase.dataDisconnect();
    }
}
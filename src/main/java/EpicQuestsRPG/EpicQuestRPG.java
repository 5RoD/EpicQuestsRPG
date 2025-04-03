package EpicQuestsRPG;

import EpicQuestsRPG.Player.PlayerListener;
import EpicQuestsRPG.classes.Archer;
import EpicQuestsRPG.classes.ChangeClass;
import EpicQuestsRPG.classes.Mage;
import EpicQuestsRPG.classes.Warrior;
import EpicQuestsRPG.commands.Search;
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

    private ChangeClass ChangeClass;

    public DataBase getDataBase() {
        return DataBase;
    }

    @Override
    public void onEnable() {


        // Register ConfigUtil
        ConfigUtil configUtil = new ConfigUtil(this);


        // Initialize DataBase with the ConfigUtil and main plugin reference
        this.DataBase = new DataBase(configUtil, this);

        // Ensure the table is created on initialization
        DataBase.dataCreate();

        // Database connection is handled automatically by HikariCP, no need for immediate connection call
        // DataBase.dataConnect(); (not needed anymore since Hikari handles it)

        // Initialize VaultUtil and register commands
        VaultUtil = new VaultUtil(this); // Pass main plugin reference

        // Initialize MenuUtil
        MenuUtil menuUtil = new MenuUtil();

        // Initialize Commands
        Gui = new Gui(menuUtil);

        // Initialize Classes with Database
        Warrior warrior = new Warrior(DataBase); // Initialize Warrior
        Mage mage = new Mage(DataBase); // Initialize Mage
        Archer archer = new Archer(configUtil, DataBase); // Initialize Archer

        // Pass instances to ChangeClass
        ChangeClass = new ChangeClass(warrior, mage, archer);

        // Register events and commands
        getServer().getPluginManager().registerEvents(archer, this); // Register Archer events
        getServer().getPluginManager().registerEvents(new PlayerListener(this.getDataBase()), this); // Register PlayerListener

        // Initialize Commands
        getCommand("eco").setExecutor(Eco);
        getCommand("ecodeposit").setExecutor(Eco);
        getCommand("gui").setExecutor(Gui);
        getCommand("class").setExecutor(ChangeClass); // Set ChangeClass as command executor
        this.getCommand("search").setExecutor(new Search(DataBase));






    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        DataBase.dataDisconnect();
    }
}

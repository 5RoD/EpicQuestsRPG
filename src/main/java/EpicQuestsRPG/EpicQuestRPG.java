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
    private Search Search;
    private ChangeClass ChangeClass;

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

        // Initializes Classes
        Warrior warrior = new Warrior(DataBase); // Initialize Warrior
        Mage mage = new Mage(DataBase); // Initialize Mage
        Archer archer = new Archer(DataBase); // Initialize Archer
        ChangeClass = new ChangeClass(warrior, mage, archer); // Pass instances to ChangeClass

        //Intialize Commands
        getCommand("eco").setExecutor(Eco);
        getCommand("ecodeposit").setExecutor(Eco);
        getCommand("gui").setExecutor(Gui);
        getCommand("class").setExecutor(ChangeClass); // Set ChangeClass as command executor
        this.getCommand("search").setExecutor(new Search(DataBase));

        getServer().getPluginManager().registerEvents(new PlayerListener(this.getDataBase()), this);
    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        DataBase.dataDisconnect();
    }
}
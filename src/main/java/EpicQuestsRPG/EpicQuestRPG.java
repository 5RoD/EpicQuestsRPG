package EpicQuestsRPG;

import EpicQuestsRPG.Player.PlayerListener;
import EpicQuestsRPG.classes.Archer;
import EpicQuestsRPG.classes.ChangeClass;
import EpicQuestsRPG.classes.Mage;
import EpicQuestsRPG.classes.Warrior;
import EpicQuestsRPG.commands.Search;
import EpicQuestsRPG.commands.Gui;
import EpicQuestsRPG.commands.invalidUsageHandler;
import EpicQuestsRPG.gui.MenuUtil;
import EpicQuestsRPG.util.CC;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.message.LiteMessages;
import dev.rollczi.litecommands.time.DurationParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;

public final class EpicQuestRPG extends JavaPlugin {

    private Gui Gui;
    private DataBase DataBase;
    private ChangeClass ChangeClass;
    private LiteCommands liteCommands;

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


        // Initialize Commands with other stuff
        MenuUtil menuUtil = new MenuUtil();
        Gui = new Gui(menuUtil);
        Warrior warrior = new Warrior(DataBase); // Initialize Warrior
        Mage mage = new Mage(DataBase); // Initialize Mage
        Archer archer = new Archer(configUtil, DataBase); // Initialize Archer

        // Register events and commands
        getServer().getPluginManager().registerEvents(archer, this); // Register Archer events
        getServer().getPluginManager().registerEvents(new PlayerListener(this.getDataBase()), this); // Register PlayerListener


// Initialize Commands
        this.liteCommands = LiteBukkitFactory.builder("EpicQuestsRPG", this)
                .message(LiteMessages.COMMAND_COOLDOWN, (invocation, cooldownState) -> {
                    Duration remainingDuration = cooldownState.getRemainingDuration();
                    String formattedTime = DurationParser.DATE_TIME_UNITS.format(remainingDuration);
                    return CC.translate("&CPlease wait &a" + formattedTime + " &cbefore using this command again.");
                })


                .commands(new Search(DataBase))
                .commands(Gui)
                .commands(new ChangeClass(warrior, mage, archer))



                .invalidUsage(new invalidUsageHandler())
                .build();
    }

    @Override
    public void onDisable() {
        // Disconnect from the database on server shutdown
        DataBase.dataDisconnect();

        // Unregister liteCommands
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }
}

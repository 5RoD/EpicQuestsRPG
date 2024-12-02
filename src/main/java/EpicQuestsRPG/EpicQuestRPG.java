package EpicQuestsRPG;

import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.plugin.java.JavaPlugin;

public final class EpicQuestRPG extends JavaPlugin {

    DataBase dataBase;

    @Override
    public void onEnable() {
        /*Register ConfigUtil*/
        ConfigUtil configUtil = new ConfigUtil(this);
        /*Register database.java config only*/
        DataBase dataBase = new DataBase(configUtil);
        /*connect to database on startup*/
        dataBase.dataConnect();



    }

    @Override
    public void onDisable() {
        /*Disconnect database on server shutdown*/
       dataBase.dataDisconnect();
    }
}

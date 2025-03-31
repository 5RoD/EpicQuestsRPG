package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.DataBase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Warrior {

    private DataBase dataBase;

    public Warrior(DataBase dataBase) {
        this.dataBase = dataBase;
    }

public void warrior(String player_name, String class_name) {


    dataBase.UpdateClass(class_name, player_name);


}

}

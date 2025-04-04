package EpicQuestsRPG.classes;

import EpicQuestsRPG.Player.PlayerManager;
import EpicQuestsRPG.util.DataBase;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;



public class Default implements Listener {

    private DataBase dataBase;

    public Default(DataBase dataBase) {
        this.dataBase = dataBase;
    }


//Unfinished
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        var player_name = player.getName();

        PlayerManager playerSearch = dataBase.playerSearch(player_name);
        var results = playerSearch.getPlayer_class();

        if (results == null) {
            return;
        }

        if (results.startsWith("default")) {
            player.removePotionEffect(PotionEffectType.STRENGTH);
            player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        }





    }

    public void Default(String player_name, String class_name) {
        dataBase.UpdateClass(class_name, player_name);
    }

    public void onArcherSelect(Player player) {
        var player_name = player.getName();
        player.removePotionEffect(PotionEffectType.STRENGTH);
        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
    }
}

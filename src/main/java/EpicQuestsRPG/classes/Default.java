package EpicQuestsRPG.classes;

import EpicQuestsRPG.Player.PlayerManager;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import javax.xml.crypto.Data;

public class Default implements Listener {

    private DataBase dataBase;


//Unfinished
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        var player_name = player.getName();

        PlayerManager playerSearch = dataBase.playerSearch(player_name);
        var results = playerSearch.getPlayer_class();



player.removePotionEffect(PotionEffectType.STRENGTH);
player.removePotionEffect(PotionEffectType.HEALTH_BOOST);



    }
}

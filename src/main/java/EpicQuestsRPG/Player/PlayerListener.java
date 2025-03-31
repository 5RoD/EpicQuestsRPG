package EpicQuestsRPG.Player;

import EpicQuestsRPG.util.DataBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerListener implements Listener {

    private DataBase dataBase;

    public PlayerListener(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String uuid = player.getUniqueId().toString();

        // Check if the player exists in the database
        if (dataBase.playerSearch(playerName) == null) {
            // Add the player to the database if they don't exist
            dataBase.addPlayer(uuid, playerName);
        }
    }
}
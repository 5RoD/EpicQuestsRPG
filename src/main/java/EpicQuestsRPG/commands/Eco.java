package EpicQuestsRPG.commands;

import EpicQuestsRPG.economy.VaultUtil;
import EpicQuestsRPG.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Eco implements CommandExecutor {

    private VaultUtil vaultUtil;


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("epicquestsrpg.eco")) {
            sender.sendMessage(CC.translate("&cYou do not have permission to run this"));
            return true;
        }


        switch (command.getName().toLowerCase()) {
            case "eco":
                vaultUtil.handleEcoCommand(player);
                return true;
            case "ecodeposit":
                vaultUtil.handleEcoDepositCommand(player, args);
                return true;
            default:

                return false;
        }

    }
}

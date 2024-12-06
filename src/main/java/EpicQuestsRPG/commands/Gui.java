package EpicQuestsRPG.commands;

import EpicQuestsRPG.gui.MenuUtil;
import EpicQuestsRPG.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Gui implements CommandExecutor {

    private MenuUtil menu;

    public Gui(MenuUtil menu) {
        this.menu = menu;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("epicquestsrpg.gui")) {
            sender.sendMessage(CC.translate("&cYou do not have permission to run this"));
            return true;
        }

        if (command.getName().equalsIgnoreCase("gui")) {
            menu.guiOpen(player);
            return true;
        }

        return false;
    }
}
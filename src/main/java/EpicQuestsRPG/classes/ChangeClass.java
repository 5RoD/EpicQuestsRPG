package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChangeClass implements CommandExecutor, TabCompleter {

    private Warrior warrior;
    private Mage mage;
    private Archer archer;

    public ChangeClass(Warrior warrior, Mage mage, Archer archer) {
        this.warrior = warrior;
        this.mage = mage;
        this.archer = archer;
    }

    enum Classes {
        WARRIOR,
        MAGE,
        ARCHER
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cThis command can only be executed by players."));
            return true; // Command handled
        }

        Player player = (Player) sender;

        if (!player.hasPermission("epicquestsrpg.class")) {
            sender.sendMessage(CC.translate("&cYou do not have permission to run this command."));
            return true; // Command handled
        }

        if (command.getName().equalsIgnoreCase("class")) {
            if (args.length == 0) {
                sender.sendMessage(CC.translate("&cPlease provide a class name to select!\n&7Available Classes:\n&6- &eWARRIOR\n&6- &bMAGE\n&6- &aARCHER"));
                return true; // Command handled
            }

            String className = args[0].toUpperCase();
            Classes selectedClass = null;

            // Check if the selected class is valid
            try {
                selectedClass = Classes.valueOf(className);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(CC.translate("&cInvalid class name! Please choose from the following:\n&6- &cWARRIOR\n&d- &bMAGE\n&6- &aARCHER"));
                return true; // Command handled
            }

            // Handle the class change logic
            switch (selectedClass) {
                case WARRIOR:
                    sender.sendMessage(CC.translate("&cYou have selected the Warrior class!"));
                    warrior.warrior(player.getName(), selectedClass.toString());
                    break;
                case MAGE:
                    sender.sendMessage(CC.translate("&dYou have selected the Mage class!"));
                    mage.Mage(player.getName(), selectedClass.toString());
                    break;
                case ARCHER:
                    sender.sendMessage(CC.translate("&aYou have selected the Archer class!"));
                    archer.Archer(player.getName(), selectedClass.toString());
                    break;
            }

            return true; // Command handled
        }

        return false; // Invalid command, let the server handle it
    }

@Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (!(sender instanceof Player)) return null;

        if (command.getName().equalsIgnoreCase("class")) {
            // If no arguments are typed, suggest classes
            if (args.length == 1) {
                List<String> suggestions = new ArrayList<>();
                for (Classes c : Classes.values()) {
                    suggestions.add(c.name().toLowerCase()); // Add class names to the list
                }
                return suggestions;
            }
        }
        return null; // Return null if no suggestions
    }
}
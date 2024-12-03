package EpicQuestsRPG.economy;

import EpicQuestsRPG.EpicQuestRPG;
import net.milkbowl.vault2.economy.Economy;
import net.milkbowl.vault2.economy.EconomyResponse;
import net.milkbowl.vault2.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.math.BigDecimal;

public class VaultUtil {

    private static Economy econ = null;
    private static Permission perms = null;

    private final EpicQuestRPG plugin; // Store reference to the main plugin class

    public VaultUtil(EpicQuestRPG plugin) {
        this.plugin = plugin;
        setupEconomy();
        setupPermissions();
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("Vault dependency not found!");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            plugin.getLogger().severe("Economy service not available.");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        switch (command.getName().toLowerCase()) {
            case "eco":
                handleEcoCommand(player);
                return true;
            case "ecodeposit":
                handleEcoDepositCommand(player);
                return true;
            default:
                return false; // Command not handled
        }
    }

    private void handleEcoCommand(Player player) {
        if (!player.hasPermission("epicquestsrpg.balance")) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }

        String balance = econ.format(econ.getBalance("AceBank", player.getUniqueId()));
        player.sendMessage(String.format("Your current balance is: %s", balance));
    }

    private void handleEcoDepositCommand(Player player) {
        if (!player.hasPermission("epicquestsrpg.deposit")) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }

        BigDecimal depositAmount = BigDecimal.valueOf(1.05); // Example deposit amount
        EconomyResponse response = econ.bankDeposit("AceBank", player.getUniqueId(), "PlayerAccount", depositAmount);

        if (response.transactionSuccess()) {
            player.sendMessage(String.format("Successfully deposited %s. New balance: %s",
                    econ.format(response.amount), econ.format(response.balance)));
        } else {
            player.sendMessage(String.format("Deposit failed: %s", response.errorMessage));
        }
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }
}

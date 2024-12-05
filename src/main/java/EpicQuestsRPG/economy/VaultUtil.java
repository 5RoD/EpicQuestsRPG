package EpicQuestsRPG.economy;

import EpicQuestsRPG.EpicQuestRPG;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;

public class VaultUtil implements CommandExecutor {

    private static Economy econ = null;
    private static Permission perms = null;

    private final EpicQuestRPG plugin;
    private final MoneyUtil moneyUtil;

    public VaultUtil(EpicQuestRPG plugin) {
        this.plugin = plugin;
        this.moneyUtil = new MoneyUtil(plugin);
        new BukkitRunnable() {
            @Override
            public void run() {
                setupEconomy();
                setupPermissions();
            }
        }.runTaskLater(plugin, 20L); // Delay by 1 second (20 ticks)
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("Vault dependency not found!");
            return false;
        }
        econ = new CustomEconomy(moneyUtil);
        plugin.getServer().getServicesManager().register(Economy.class, econ, plugin, org.bukkit.plugin.ServicePriority.Highest);
        return true;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    @Override
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
                handleEcoDepositCommand(player, args);
                return true;
            default:
                return false;
        }
    }

    private void handleEcoCommand(Player player) {
        if (!player.hasPermission("epicquestsrpg.balance")) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }

        double balance = moneyUtil.getBalance(player.getUniqueId().toString());
        player.sendMessage(String.format("Your current balance is: %s", econ.format(balance)));
    }

    private void handleEcoDepositCommand(Player player, String[] args) {
        if (!player.hasPermission("epicquestsrpg.deposit")) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }

        if (args.length != 1) {
            player.sendMessage("Usage: /ecodeposit <amount>");
            return;
        }

        BigDecimal depositAmount;
        try {
            depositAmount = new BigDecimal(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid amount. Please enter a valid number.");
            return;
        }

        boolean success = moneyUtil.deposit(player.getUniqueId().toString(), depositAmount.doubleValue());

        if (success) {
            double newBalance = moneyUtil.getBalance(player.getUniqueId().toString());
            player.sendMessage(String.format("Successfully deposited %s. New balance: %s",
                    econ.format(depositAmount.doubleValue()), econ.format(newBalance)));
        } else {
            player.sendMessage("Deposit failed. Please try again later.");
        }
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }
}
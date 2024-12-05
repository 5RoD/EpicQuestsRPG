package EpicQuestsRPG.economy;

import EpicQuestsRPG.EpicQuestRPG;
import EpicQuestsRPG.Player.PlayerManager;

public class MoneyUtil {

    private final EpicQuestRPG plugin;

    public MoneyUtil(EpicQuestRPG plugin) {
        this.plugin = plugin;
    }

    public double getBalance(String uuid) {
        PlayerManager playerManager = plugin.getDataBase().findStatsByUUID(uuid);
        if (playerManager != null) {
            return playerManager.getMoney();
        } else {
            plugin.getLogger().severe("Could not retrieve balance for UUID: " + uuid);
            return 0.0;
        }
    }

    public boolean deposit(String uuid, double amount) {
        PlayerManager playerManager = plugin.getDataBase().findStatsByUUID(uuid);
        if (playerManager != null) {
            double newBalance = playerManager.getMoney() + amount;
            plugin.getDataBase().updatePlayerMoney(uuid, newBalance);
            return true;
        } else {
            plugin.getLogger().severe("Could not deposit money for UUID: " + uuid);
            return false;
        }
    }

    public boolean withdraw(String uuid, double amount) {
        PlayerManager playerManager = plugin.getDataBase().findStatsByUUID(uuid);
        if (playerManager != null) {
            double currentBalance = playerManager.getMoney();
            if (currentBalance >= amount) {
                double newBalance = currentBalance - amount;
                plugin.getDataBase().updatePlayerMoney(uuid, newBalance);
                return true;
            } else {
                plugin.getLogger().severe("Insufficient funds for UUID: " + uuid);
                return false;
            }
        } else {
            plugin.getLogger().severe("Could not withdraw money for UUID: " + uuid);
            return false;
        }
    }
}
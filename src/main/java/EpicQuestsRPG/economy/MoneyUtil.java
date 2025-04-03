package EpicQuestsRPG.economy;

import EpicQuestsRPG.EpicQuestRPG;
import EpicQuestsRPG.Player.PlayerManager;

public class MoneyUtil {

    private final EpicQuestRPG plugin;

    public MoneyUtil(EpicQuestRPG plugin) {
        this.plugin = plugin;
    }

    public double getBalance(String playerName) {
        PlayerManager playerManager = plugin.getDataBase().playerSearch(playerName);
        if (playerManager != null) {
            return playerManager.getMoney();
        } else {
            plugin.getLogger().severe("Could not retrieve balance for player: " + playerName);
            return 0.0;
        }
    }

    public boolean deposit(String playerName, double amount) {
        PlayerManager playerManager = plugin.getDataBase().playerSearch(playerName);
        if (playerManager != null) {
            double newBalance = playerManager.getMoney() + amount;
            plugin.getDataBase().updatePlayerMoney(playerManager.getUuid(), newBalance);
            return true;
        } else {
            plugin.getLogger().severe("Could not deposit money for player: " + playerName);
            return false;
        }
    }

    public boolean withdraw(String playerName, double amount) {
        PlayerManager playerManager = plugin.getDataBase().playerSearch(playerName);
        if (playerManager != null) {
            double currentBalance = playerManager.getMoney();
            if (currentBalance >= amount) {
                double newBalance = currentBalance - amount;
                plugin.getDataBase().updatePlayerMoney(playerManager.getUuid(), newBalance);
                return true;
            } else {
                plugin.getLogger().severe("Insufficient funds for player: " + playerName);
                return false;
            }
        } else {
            plugin.getLogger().severe("Could not withdraw money for player: " + playerName);
            return false;
        }
    }
}
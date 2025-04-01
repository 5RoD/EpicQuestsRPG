package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.CC;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.attribute.Attribute.ATTACK_DAMAGE;

public class Archer implements Listener {

    // placeholder for getClassesInfo
    private double health;
    private double damage;

    // ConcurrentHashMap.newKeySet method is thread safe so we use this
    Set<String> archerHash = ConcurrentHashMap.newKeySet();

    private ConfigUtil configUtil;
    private final DataBase dataBase;

    public Archer(ConfigUtil configUtil, DataBase dataBase) {
        this.configUtil = configUtil;
        this.dataBase = dataBase;
        getClassesInfo();
    }

    private void getClassesInfo() {
        // Get the class details from the config file
        this.health = configUtil.getClassesConfig().getDouble("classes.Archer.health");  // changed to getDouble()
        this.damage = configUtil.getClassesConfig().getDouble("classes.Archer.damage");  // changed to getDouble()
    }

    public void archer(String player_name, String class_name) {
        dataBase.UpdateClass(class_name, player_name);
    }

    public void archerPowers(Player player) {
        String player_name = player.getName();

        if (archerHash.contains(player_name)) {
            try {
                player.setHealthScale(health);

                AttributeInstance instance = player.getAttribute(ATTACK_DAMAGE);
                if (instance != null) {
                    instance.setBaseValue(damage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Write here the logic for each class and then pass it into ChangeClass
    public void onArcherSelect(Player player) {
        String player_name = player.getName();

        // If the player is not in the hashSet, add them and give them powers
        if (!archerHash.contains(player_name)) {
            archerHash.add(player_name);
            archerPowers(player);
        }
        archerPowers(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String player_name = e.getPlayer().getName();
        Player player = e.getPlayer();

        try {
            var results = dataBase.playerSearch(player_name);
            var getClass = results.getPlayer_class();

            if (getClass != null && getClass.contains("ARCHER")) {
                archerHash.add(player_name);
                archerPowers(player);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Right Click Ability Archer SOON
    // Shift + Right Click Ability Archer SOON
}

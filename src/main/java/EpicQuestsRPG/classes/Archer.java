package EpicQuestsRPG.classes;

import EpicQuestsRPG.Player.PlayerManager;
import EpicQuestsRPG.util.CC;
import EpicQuestsRPG.util.ConfigUtil;
import EpicQuestsRPG.util.DataBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

                // Apply the Potion effect
                // Effect level is based on the `damage/health` value from the config
                int strengthLevel = (int) Math.floor(damage);
                int healthLevel = (int) Math.floor(health);

                PotionEffect strengthEffect = new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, strengthLevel - 1, false, false);
                PotionEffect healthEffect = new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, healthLevel - 1, false, false);

                player.addPotionEffect(strengthEffect);
                player.addPotionEffect(healthEffect);

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
            return;
        }
        archerHash.add(player_name);
        archerPowers(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String player_name = e.getPlayer().getName();
        Player player = e.getPlayer();

        try {
            PlayerManager results = dataBase.playerSearch(player_name);
            var getClass = results.getPlayer_class();

            // Do nothing if it's not an archer
            if (!getClass.contains("ARCHER")) {
                return;
            }

            if (getClass != null && getClass.contains("ARCHER")) {
                archerHash.add(player_name);
                archerPowers(player);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //Unfinished
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String player_name = e.getPlayer().getName();

        try {
            archerHash.remove(player_name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Right Click Ability Archer SOON
    // Shift + Right Click Ability Archer SOON
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        var player_name = e.getPlayer().getName();

        if (!archerHash.contains(player_name)) {
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();

            // Check if the player is holding a bow
            if (player.getInventory().getItemInMainHand().getType() == Material.BOW) {
                Arrow arrow = player.launchProjectile(Arrow.class);
                arrow.setVelocity(player.getLocation().getDirection().multiply(2)); // Adjust speed
            }
        }
    }
}

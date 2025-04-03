package EpicQuestsRPG.commands;

import EpicQuestsRPG.util.CC;
import EpicQuestsRPG.util.DataBase;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.cooldown.Cooldown;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;

@Command(name = "search")
@Permission("epicquestsrpg.class")
@Description("Search a player in the database")
@Cooldown(key = "search-cooldown", count = 5L, unit = ChronoUnit.SECONDS, bypass = "admin-bypass.permission")
public class Search {

    private DataBase dataBase;

    public Search(DataBase dataBase) {
        this.dataBase = dataBase;
    }

   @Execute
    public void onSearchCommand(@Context CommandSender sender, @Arg Player playerName) {

        var target = playerName.getName();




        // Check if the player provided a name
        if (target.isEmpty()) {
            sender.sendMessage(CC.translate("&cPlease provide a player name to search."));
            return;
        }

        // Validate and search for the player in the database
        if (dataBase.playerSearch(target) != null) {
            sender.sendMessage(CC.translate("&aFound player &e" + playerName + " &ain the database. Their info: \n"
                    +
                    dataBase.playerSearch(target).toString()));

        } else {
            sender.sendMessage(CC.translate("&cPlayer &e" + playerName + " &cnot found in the database."));
        }
    }
}

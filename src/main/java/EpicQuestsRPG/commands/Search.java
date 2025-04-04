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

        StringBuilder sb = new StringBuilder();

        var target = playerName.getName();
        var results = dataBase.playerSearch(target);


        // Validate and search for the player in the database
        if (results != null) {
            sb.append(CC.translate("&aFound player &e"))
                    .append(target)
                    .append(CC.translate(" &ain the database. Their info:\n"))
                    .append(results.toString());
            sender.sendMessage(sb.toString());


        }
    }
}
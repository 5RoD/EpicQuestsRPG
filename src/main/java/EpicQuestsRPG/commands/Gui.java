package EpicQuestsRPG.commands;

import EpicQuestsRPG.gui.MenuUtil;
import EpicQuestsRPG.util.CC;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command(name = "gui")
@Permission("epicquestsrpg.gui")
@Description("Open the GUI menu")
public class Gui {

    private MenuUtil menu;

    public Gui(MenuUtil menu) {
        this.menu = menu;
    }

    @Async
    @Execute
    public void onGuiCommand(@Context CommandSender sender) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("epicquestsrpg.gui")) {
            sender.sendMessage(CC.translate("&cYou do not have permission to run this"));
            return;
        }

        menu.guiOpen(player);
    }
}

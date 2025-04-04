package EpicQuestsRPG.classes;

import EpicQuestsRPG.util.CC;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Command(name = "class")
@Permission("epicquestsrpg.class")
@Description("Change your class")

public class ChangeClass {

    private final Warrior warrior;
    private final Mage mage;
    private final Archer archer;
    private final Default Default;

    public ChangeClass(Warrior warrior, Mage mage, Archer archer, Default Default) {
        this.warrior = warrior;
        this.mage = mage;
        this.archer = archer;
        this.Default = Default;
    }

    enum Classes {
        WARRIOR,
        MAGE,
        ARCHER,
        DEFAULT

    }

    @Async
    @Execute
    public void onChangeClassCommand(@Context CommandSender sender, @Async @Arg Classes classes) {

        Player player = (Player) sender;

        switch (classes) {
            case WARRIOR:
                warrior.warrior(player.getName(), classes.toString());
                sender.sendMessage(CC.translate("&cYou have selected the Warrior class!"));
                break;
            case MAGE:
                mage.mage(player.getName(), classes.toString());
                sender.sendMessage(CC.translate("&dYou have selected the Mage class!"));
                break;
            case ARCHER:
                archer.archer(player.getName(), classes.toString());
                sender.sendMessage(CC.translate("&aYou have selected the Archer class!"));
                archer.onArcherSelect(player);
            case DEFAULT:
                Default.Default(player.getName(), classes.toString());
                sender.sendMessage(CC.translate("&aYou have selected the Archer class!"));
                Default.onArcherSelect(player);
                break;
        }
    }

}

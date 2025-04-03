package EpicQuestsRPG.commands;

import EpicQuestsRPG.util.CC;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;


public class invalidUsageHandler implements InvalidUsageHandler<CommandSender> {

    @Async
    @Override
    public void handle(
            Invocation<CommandSender> invocation,
            InvalidUsage<CommandSender> result,
            ResultHandlerChain<CommandSender> chain
    ) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            sender.sendMessage(CC.translate("&cInvalid usage of command! &7(" + schematic.first() + ")"));
            return;
        }


        sender.sendMessage(CC.translate("&cInvalid usage of command!"));
        for (String scheme : schematic.all()) {
            sender.sendMessage(CC.translate("&8 - &7" + scheme));
        }
    }
}

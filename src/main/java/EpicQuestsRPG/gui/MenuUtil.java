package EpicQuestsRPG.gui;

import EpicQuestsRPG.util.CC;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MenuUtil {

    public void guiOpen(Player player) {
        Gui gui = Gui.gui()
                .title(Component.text("GUI Title!"))
                .rows(6)
                .create();

        GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem(event -> {
            player.sendMessage(CC.translate("You clicked me hehe"));
        });

        gui.addItem(guiItem);
        gui.open(player);
    }
}
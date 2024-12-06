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

        GuiItem filler = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem(event -> {
            event.setCancelled(true);

        });

        GuiItem guiItem = ItemBuilder.from(Material.SHULKER_BOX).asGuiItem(event -> {
            event.setCancelled(true);
            player.sendMessage(CC.translate("You clicked me hehe"));
        });


        GuiItem guiItem2 = ItemBuilder.from(Material.BARREL).asGuiItem(event -> {
            event.setCancelled(true);
            player.sendMessage(CC.translate("You clicked me hehe"));
        });

        GuiItem guiItem3 = ItemBuilder.from(Material.BARRIER).asGuiItem(event -> {
            event.setCancelled(true);
            player.sendMessage(CC.translate("You closed the gui"));
            gui.close(player);
        });

        gui.getFiller().fill(filler);
        gui.setItem(21, guiItem);
        gui.setItem(22, guiItem2);
        gui.setItem(23, guiItem3);
        gui.open(player);
    }
}
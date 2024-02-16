package day.lone.mail.plugin.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import day.lone.mail.plugin.inventory.GuiMailBox;
import day.lone.mail.plugin.inventory.manage.GuiMailBoxManage;
import org.bukkit.entity.Player;

@CommandAlias("mailbox")
public class BoxCommand extends BaseCommand {

    @Default
    public void box(Player player) {
        new GuiMailBox(player).openInventory(player);
    }

    @Subcommand("manage")
    @CommandPermission("mailbox.admin")
    public void manage(Player player) {
        new GuiMailBoxManage().openInventory(player);
    }

}
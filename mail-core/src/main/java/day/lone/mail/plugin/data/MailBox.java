package day.lone.mail.plugin.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MailBox {

    private OfflinePlayer player;
    private List<ItemStack> items;

    public MailBox(OfflinePlayer player, List<ItemStack> items) {
        this.player = player;
        this.items = items;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public MailBox setPresents(List<ItemStack> items) {
        this.items = items;
        return this;
    }

    public MailBox addPresent(ItemStack itemStack) {
        this.items.add(itemStack);
        return this;
    }

    public MailBox removePresent(ItemStack itemStack) {
        this.items.remove(itemStack);
        return this;
    }

    public List<ItemStack> getItems() {
        return items;
    }

}
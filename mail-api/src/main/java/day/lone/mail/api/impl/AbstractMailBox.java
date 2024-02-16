package day.lone.mail.api.impl;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public abstract class AbstractMailBox implements MailBox {

    @Override
    public MailBoxResponse addPresent(UUID uuid, Material material) {
        return addPresent(uuid, new ItemStack(material));
    }

    @Override
    public MailBoxResponse addPresent(Player player, Material material) {
        return addPresent(player, new ItemStack(material));
    }

    @Override
    public MailBoxResponse pickUpPresent(UUID uuid, Material material) {
        return pickUpPresent(uuid, new ItemStack(material));
    }

    @Override
    public MailBoxResponse pickUpPresent(Player player, Material material) {
        return pickUpPresent(player, new ItemStack(material));
    }

    @Override
    public MailBoxResponse removePresent(UUID uuid, Material material) {
        return removePresent(uuid, new ItemStack(material));
    }

    @Override
    public MailBoxResponse removePresent(Player player, Material material) {
        return removePresent(player, new ItemStack(material));
    }

    @Override
    public boolean isBox(OfflinePlayer player) {
        return isBox(player.getUniqueId());
    }

}
package day.lone.mail.api.impl;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface MailBox {

    MailBoxResponse addPresent(UUID uuid, ItemStack itemStack);

    MailBoxResponse addPresent(UUID uuid, Material material);

    MailBoxResponse addPresent(Player player, ItemStack itemStack);

    MailBoxResponse addPresent(Player player, Material material);

    MailBoxResponse pickUpPresent(UUID uuid, ItemStack itemStack);

    MailBoxResponse pickUpPresent(UUID uuid, Material material);

    MailBoxResponse pickUpPresent(Player player, ItemStack itemStack);

    MailBoxResponse pickUpPresent(Player player, Material material);

    MailBoxResponse removePresent(UUID uuid, ItemStack itemStack);

    MailBoxResponse removePresent(UUID uuid, Material material);

    MailBoxResponse removePresent(Player player, ItemStack itemStack);

    MailBoxResponse removePresent(Player player, Material material);

    boolean isBox(UUID uuid);

    boolean isBox(OfflinePlayer player);

}
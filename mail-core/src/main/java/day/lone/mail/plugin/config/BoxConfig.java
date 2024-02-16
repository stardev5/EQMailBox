package day.lone.mail.plugin.config;

import day.lone.mail.plugin.EQMailBox;
import day.lone.mail.plugin.data.MailBox;
import day.yone.utils.ByteUtils;
import day.yone.utils.config.FileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BoxConfig extends FileConfiguration {

    public BoxConfig() {
        super(EQMailBox.getInstance(), "boxes.yml");
        createFile();

        addDefault("boxes", new HashSet<>());
        copyDefaults(true);
        saveData();
    }

    public void createBox(UUID uuid) {
        set("boxes." + uuid, ByteUtils.encodeToItemArray(new ItemStack[0]));
    }

    public void createBox(Player player) {
        createBox(player.getUniqueId());
    }

    public void updateBox(MailBox mailBox) {
        set("boxes." + mailBox.getPlayer().getUniqueId(), ByteUtils.encodeToItemArray(mailBox.getItems().toArray(new ItemStack[0])));
    }

    public void removeBox(UUID uuid) {
        set("boxes." + uuid, null);
    }

    public void removeBox(OfflinePlayer player) {
        removeBox(player.getUniqueId());
    }

    public MailBox getBox(UUID uuid) {
        return new MailBox(
                Bukkit.getOfflinePlayer(uuid),
                new ArrayList<>(Arrays.asList(ByteUtils.decodeToItemArray(getString("boxes." + uuid))))
        );
    }

    public MailBox getBox(OfflinePlayer player) {
        return getBox(player.getUniqueId());
    }

    public boolean isBox(UUID uuid) {
        return contains("boxes." + uuid);
    }

    public boolean isBox(OfflinePlayer player) {
        return isBox(player.getUniqueId());
    }

    public List<OfflinePlayer> getBoxes() {
        List<OfflinePlayer> players = new ArrayList<>();
        for (String str : getConfigurationSection("boxes").getKeys(false)) {
            players.add(Bukkit.getOfflinePlayer(UUID.fromString(str)));
        }
        return players;
    }

}
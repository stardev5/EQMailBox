package day.lone.mail.plugin.service;

import day.lone.mail.plugin.config.BoxConfig;
import day.lone.mail.plugin.data.MailBox;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class BoxService {

    public static BoxService getInstance() {
        return new BoxService();
    }

    public void createBox(UUID uuid) {
        new BoxConfig().createBox(uuid);
    }

    public void createBox(Player player) {
        new BoxConfig().createBox(player);
    }

    public void updateBox(MailBox mailBox) {
        new BoxConfig().updateBox(mailBox);
    }

    public void removeBox(UUID uuid) {
        new BoxConfig().removeBox(uuid);
    }

    public void removeBox(OfflinePlayer player) {
        new BoxConfig().removeBox(player);
    }

    public MailBox getBox(UUID uuid) {
        return new BoxConfig().getBox(uuid);
    }

    public MailBox getBox(OfflinePlayer player) {
        return new BoxConfig().getBox(player);
    }

    public boolean isBox(UUID uuid) {
        return new BoxConfig().isBox(uuid);
    }

    public boolean isBox(OfflinePlayer player) {
        return new BoxConfig().isBox(player);
    }

    public List<OfflinePlayer> getBoxes() {
        return new BoxConfig().getBoxes();
    }

}
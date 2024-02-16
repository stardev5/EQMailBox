package day.lone.mail.api.impl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class MailBoxResponse {

    private final OfflinePlayer player;
    private final ResponseType type;
    private final String message;

    public MailBoxResponse(UUID uuid, ResponseType type, String message) {
        this.player = Bukkit.getOfflinePlayer(uuid);
        this.type = type;
        this.message = message;
    }

    public MailBoxResponse(OfflinePlayer player, ResponseType type, String message) {
        this(player.getUniqueId(), type, message);
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public ResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public boolean success() {
        return this.type == ResponseType.SUCCESS;
    }

    public boolean failure() {
        return this.type == ResponseType.FAILURE;
    }

    public enum ResponseType {
        SUCCESS(1),
        FAILURE(2);

        private final int id;

        ResponseType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
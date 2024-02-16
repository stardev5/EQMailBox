package day.lone.mail.plugin.listener;

import day.lone.mail.api.MailBoxAPI;
import day.lone.mail.plugin.service.BoxService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMailBoxListener implements Listener {

    public CustomMailBoxListener(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!MailBoxAPI.getInstance().isBox(event.getPlayer())) {
            BoxService.getInstance().createBox(event.getPlayer());
        }
    }

}
package day.lone.mail.api;

import day.lone.mail.api.impl.MailBox;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Objects;

public interface MailBoxAPI {

    static MailBox getInstance() {
        RegisteredServiceProvider<MailBox> service = Bukkit.getServer().getServicesManager().getRegistration(MailBox.class);
        return Objects.requireNonNull(service).getProvider();
    }

}
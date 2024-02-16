package day.lone.mail.plugin;

import co.aikar.commands.BukkitCommandManager;
import day.lone.mail.api.impl.MailBox;
import day.lone.mail.plugin.command.BoxCommand;
import day.lone.mail.plugin.config.BoxConfig;
import day.lone.mail.plugin.listener.CustomMailBoxListener;
import day.lone.mail.plugin.service.BoxRegistration;
import day.yone.utils.inventory.CustomInventoryListener;
import day.yone.utils.translate.TranslateEnum;
import day.yone.utils.translate.Translator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class EQMailBox extends JavaPlugin {

    private static EQMailBox instance;

    @Override
    public void onEnable() {
        instance = this;

        new BoxConfig();

        new CustomMailBoxListener(this);
        new CustomInventoryListener(this);

        Translator.setTranslateEnum(this, TranslateEnum.KOREAN);

        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(new BoxCommand());

        Bukkit.getServicesManager().register(MailBox.class, new BoxRegistration(), this, ServicePriority.Normal);
    }

    public static EQMailBox getInstance() {
        return instance;
    }

}

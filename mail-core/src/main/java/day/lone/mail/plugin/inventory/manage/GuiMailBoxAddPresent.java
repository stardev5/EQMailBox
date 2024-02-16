package day.lone.mail.plugin.inventory.manage;

import day.lone.mail.api.MailBoxAPI;
import day.lone.mail.plugin.EQMailBox;
import day.lone.mail.plugin.data.MailBox;
import day.lone.mail.plugin.service.BoxService;
import day.yone.utils.SchedulerUtils;
import day.yone.utils.color.ColorUtils;
import day.yone.utils.inventory.CustomInventoryHolder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiMailBoxAddPresent extends CustomInventoryHolder {

    private final OfflinePlayer player;

    public GuiMailBoxAddPresent(OfflinePlayer player) {
        super(2, "우편함 관리 | 선물 전송");
        this.player = player;
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            MailBox mailBox = BoxService.getInstance().getBox(this.player);
            List<ItemStack> contents = new ArrayList<>(mailBox.getItems());

            for (int i = 0; i < 18; i++) {
                ItemStack is = event.getInventory().getItem(i);
                if (is != null && !is.getType().equals(Material.AIR)) {
                    contents.add(is);
                }
            }

            if (!contents.isEmpty()) {
                BoxService.getInstance().updateBox(mailBox.setPresents(contents));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
                player.sendMessage(ColorUtils.getColor("&a" + this.player.getName() + "님에게 선물을 전송했습니다!"));
            }
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
            new SchedulerUtils(EQMailBox.getInstance()).later(() -> new GuiMailBoxManage().openInventory(player));
        }
    }

}
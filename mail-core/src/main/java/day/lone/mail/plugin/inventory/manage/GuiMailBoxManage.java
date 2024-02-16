package day.lone.mail.plugin.inventory.manage;

import day.lone.mail.plugin.service.BoxService;
import day.yone.utils.ItemUtils;
import day.yone.utils.inventory.CustomInventoryHolder;
import day.yone.utils.paginated.PaginatedComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiMailBoxManage extends CustomInventoryHolder {

    private int page;
    private List<Player> players;
    private PaginatedComponent<Player> component;

    private final List<Integer> airSlots = List.of(
            45,
            46,
            47,
            48,
            49,
            50,
            51,
            52,
            53
    );

    public GuiMailBoxManage() {
        super(6, true, "우편함 관리");
        this.page = 1;
        this.players = new ArrayList<>(Bukkit.getOnlinePlayers());
        this.component = new PaginatedComponent<>(this.players, 45);
    }

    @Override
    protected void prevInit(Inventory inventory) {
        this.airSlots.forEach(slot ->
                inventory.setItem(slot, ItemUtils.setType(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("&f").getItemStack())
        );
        inventory.setItem(45, getPreviousPage());
        inventory.setItem(53, getNextPage());
    }

    @Override
    protected void init(Inventory inventory) {
        for (OfflinePlayer player : this.component.getPanes(this.page)) {
            inventory.addItem(getMailBox(player));
        }
    }

    @Override
    protected void init(Inventory inventory, int page) {
        this.page = page;
        this.players = new ArrayList<>(Bukkit.getOnlinePlayers());
        this.component = new PaginatedComponent<>(this.players, 45);

        for (int i = 0; i < 45; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }

        for (OfflinePlayer player : this.component.getPanes(this.page)) {
            inventory.addItem(getMailBox(player));
        }
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) return;

        Player player = (Player) event.getWhoClicked();
        if (itemStack.isSimilar(getPreviousPage())) {
            if ((this.page - 1) < 1) return;
            init(getInventory(), this.page - 1);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
            return;
        }

        if (itemStack.isSimilar(getNextPage())) {
            init(getInventory(), this.page + 1);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
            return;
        }

        for (OfflinePlayer p : this.component.getPanes(this.page)) {
            if (itemStack.isSimilar(getMailBox(p))) {
                if (event.isShiftClick()) {
                    if (event.isLeftClick()) {
                        new GuiMailBoxAddPresent(p).openInventory(player);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                    }
                } else {
                    if (event.isLeftClick()) {
                        new GuiMailBoxView(p, player).openInventory(player);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
                    }
                }
            }
        }
    }

    private ItemStack getPreviousPage() {
        return ItemUtils.setType(Material.RED_STAINED_GLASS_PANE)
                .setDisplayName("&c이전 페이지 이동").getItemStack();
    }

    private ItemStack getNextPage() {
        return ItemUtils.setType(Material.LIME_STAINED_GLASS_PANE)
                .setDisplayName("&a다음 페이지 이동").getItemStack();
    }

    private ItemStack getMailBox(OfflinePlayer player) {
        return ItemUtils.setType(Material.PLAYER_HEAD).setOwner(player)
                .setDisplayName("&7" + player.getName() + "님의 우편함")
                .setLore(List.of(
                        "&e[선물 확인] &f좌클릭",
                        "&c[선물 전송] &f쉬프트 + 좌클릭",
                        "",
                        "&a[ 클릭 시 해당 유저의 우편함을 확인합니다. ]"
                )).getItemStack();
    }

}
package day.lone.mail.plugin.inventory.manage;

import day.lone.mail.api.MailBoxAPI;
import day.lone.mail.api.impl.MailBoxResponse;
import day.lone.mail.plugin.EQMailBox;
import day.lone.mail.plugin.service.BoxService;
import day.yone.utils.ItemUtils;
import day.yone.utils.SchedulerUtils;
import day.yone.utils.color.ColorUtils;
import day.yone.utils.inventory.CustomInventoryHolder;
import day.yone.utils.inventory.InventoryUtils;
import day.yone.utils.paginated.PaginatedComponent;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiMailBoxView extends CustomInventoryHolder {

    private final OfflinePlayer player;
    private final OfflinePlayer viewer;

    private int page;
    private List<ItemStack> items;
    private PaginatedComponent<ItemStack> component;

    private final List<Integer> airSlots = List.of(
            46,
            47,
            48,
            49,
            50,
            51,
            52
    );

    public GuiMailBoxView(OfflinePlayer player, OfflinePlayer viewer) {
        super(6, true, player.getName() + "님의 우편함");
        this.player = player;
        this.viewer = viewer;

        this.page = 1;
        this.items = BoxService.getInstance().getBox(player).getItems();
        this.component = new PaginatedComponent<>(this.items, 45);
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
        for (ItemStack itemStack : this.component.getPanes(this.page)) {
            inventory.addItem(getPresent(itemStack));
        }
    }

    @Override
    protected void init(Inventory inventory, int page) {
        this.page = page;
        this.items = BoxService.getInstance().getBox(this.player).getItems();
        this.component = new PaginatedComponent<>(this.items, 45);

        for (int i = 0; i < 45; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }

        for (ItemStack itemStack : this.component.getPanes(this.page)) {
            inventory.addItem(getPresent(itemStack));
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

        if (equalsPlayer(this.player, this.viewer)) {
            ItemStack is = this.component.getPanes(this.page).get(event.getSlot());
            if (itemStack.isSimilar(getPresent(is))) {
                if (InventoryUtils.hasAvaliableSlot(player)) {
                    MailBoxResponse response = MailBoxAPI.getInstance().pickUpPresent(player, is);
                    if (response.success()) {
                        init(getInventory(), this.page);
                        player.sendMessage(ColorUtils.getColor("&a" + response.getMessage()));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
                    } else {
                        player.sendMessage(ColorUtils.getColor("&c" + response.getMessage()));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
                    }
                } else {
                    player.sendMessage(ColorUtils.getColor("&c당신의 인벤토리가 꽉 찼습니다."));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
                }
            }
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
            player.sendMessage(ColorUtils.getColor("&c해당 우편함은 당신의 소유가 아닙니다."));
        }
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player p) {
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
            new SchedulerUtils(EQMailBox.getInstance()).later(() -> new GuiMailBoxManage().openInventory(p));
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

    private ItemStack getPresent(ItemStack itemStack) {
        return ItemUtils.setItemStack(itemStack.clone())
                .setLore(List.of(equalsPlayer(this.player, this.viewer) ? "&a[ 클릭 시 해당 아이템을 지급 받습니다. ]" :
                        "&c[ 해당 우편함이 당신의 소유가 아닙니다. ]")).getItemStack();
    }

    private boolean equalsPlayer(OfflinePlayer player, OfflinePlayer viewer) {
        if (player != null && viewer != null) {
            if (player.getName() != null && viewer.getName() != null) {
                return player.getName().equals(viewer.getName());
            }
        }
        return false;
    }

}
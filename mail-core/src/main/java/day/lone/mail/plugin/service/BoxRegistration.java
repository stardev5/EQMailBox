package day.lone.mail.plugin.service;

import day.lone.mail.plugin.EQMailBox;
import day.lone.mail.api.impl.AbstractMailBox;
import day.lone.mail.api.impl.MailBoxResponse;
import day.yone.utils.translate.TranslateEnum;
import day.yone.utils.translate.Translator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BoxRegistration extends AbstractMailBox {

    @Override
    public MailBoxResponse addPresent(UUID uuid, ItemStack itemStack) {
        BoxService service = BoxService.getInstance();
        if (!service.isBox(uuid)) {
            service.createBox(uuid);
        }
        service.updateBox(service.getBox(uuid).addPresent(itemStack));
        String itemName = Translator.getItemLanguageName(EQMailBox.getInstance(), TranslateEnum.KOREAN, itemStack);
        return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.SUCCESS, itemName + "(을)를 선물했습니다!");
    }

    @Override
    public MailBoxResponse addPresent(Player player, ItemStack itemStack) {
        return addPresent(player.getUniqueId(), itemStack);
    }

    @Override
    public MailBoxResponse pickUpPresent(UUID uuid, ItemStack itemStack) {
        BoxService service = BoxService.getInstance();
        if (!service.isBox(uuid)) {
            return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.FAILURE, "해당 유저는 우편함이 존재하지 않습니다.");
        }

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.FAILURE, "해당 유저는 오프라인 상태입니다.");
        }

        player.getInventory().addItem(itemStack.clone());
        service.updateBox(service.getBox(uuid).removePresent(itemStack));
        String itemName = Translator.getItemLanguageName(EQMailBox.getInstance(), TranslateEnum.KOREAN, itemStack);
        return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.SUCCESS, itemName + "(을)를 지급 받았습니다!");
    }

    @Override
    public MailBoxResponse pickUpPresent(Player player, ItemStack itemStack) {
        return pickUpPresent(player.getUniqueId(), itemStack);
    }

    @Override
    public MailBoxResponse removePresent(UUID uuid, ItemStack itemStack) {
        BoxService service = BoxService.getInstance();
        if (!service.isBox(uuid)) {
            return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.FAILURE, "해당 유저는 우편함이 존재하지 않습니다.");
        }

        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.FAILURE, "해당 유저는 오프라인 상태입니다.");
        }

        service.updateBox(service.getBox(uuid).removePresent(itemStack));
        String itemName = Translator.getItemLanguageName(EQMailBox.getInstance(), TranslateEnum.KOREAN, itemStack);
        return new MailBoxResponse(uuid, MailBoxResponse.ResponseType.SUCCESS, itemName + "(을)를 제거했습니다.");
    }

    @Override
    public MailBoxResponse removePresent(Player player, ItemStack itemStack) {
        return removePresent(player.getUniqueId(), itemStack);
    }

    @Override
    public boolean isBox(UUID uuid) {
        return BoxService.getInstance().isBox(uuid);
    }

}
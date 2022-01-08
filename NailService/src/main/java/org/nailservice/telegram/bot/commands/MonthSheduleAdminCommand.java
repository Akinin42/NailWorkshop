package org.nailservice.telegram.bot.commands;

import java.time.LocalDate;
import java.util.List;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.nailservice.telegram.bot.service.TelegramSheduleFormatter;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonthSheduleAdminCommand implements Command {

    private final SheduleService sheduleService;
    private final SendBotMessageService sendBotMessageService;
    private TelegramSheduleFormatter sheduleFormatter;

    @Override
    public void execute(Update update) {
        List<Shedule> monthShedule = sheduleService.createMonthShedule(LocalDate.now());
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                createMonthSheduleText(monthShedule));
    }

    private String createMonthSheduleText(List<Shedule> monthShedule) {
        StringBuilder sheduleText = new StringBuilder();
        for (Shedule shedule : monthShedule) {
            sheduleText.append(sheduleFormatter.createSheduleTextForAdmin(shedule)).append("\n");
        }
        return sheduleText.toString();
    }
}

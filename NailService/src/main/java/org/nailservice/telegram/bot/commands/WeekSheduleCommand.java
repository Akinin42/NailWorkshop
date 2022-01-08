package org.nailservice.telegram.bot.commands;

import java.time.LocalDate;
import java.util.List;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.TelegramSheduleFormatter;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeekSheduleCommand implements Command {

    private final SheduleService sheduleService;
    private final SendBotMessageService sendBotMessageService;
    private TelegramSheduleFormatter sheduleFormatter;

    @Override
    public void execute(Update update) {
        List<Shedule> weekShedule = sheduleService.createWeekShedule(LocalDate.now());
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                createWeekSheduleText(weekShedule));
    }

    private String createWeekSheduleText(List<Shedule> weekShedule) {
        StringBuilder weekSheduleText = new StringBuilder();
        for (Shedule shedule : weekShedule) {
            weekSheduleText.append(sheduleFormatter.createSheduleTextForCustomer(shedule)).append("\n");
        }
        return weekSheduleText.toString();
    }
}

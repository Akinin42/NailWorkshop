package org.nailservice.telegram.bot.commands;

import java.time.LocalDate;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.TelegramSheduleFormatter;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DaySheduleCommand implements Command {

    private final SheduleService sheduleService;
    private final SendBotMessageService sendBotMessageService;
    private TelegramSheduleFormatter sheduleFormatter;

    @Override
    public void execute(Update update) {
        Shedule shedule = sheduleService.createShedule(LocalDate.now());
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                sheduleFormatter.createSheduleTextForCustomer(shedule));
    }
}

package org.nailservice.telegram.bot;

import org.nailservice.service.CustomerService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.commands.CustomerCommand;
import org.nailservice.telegram.bot.commands.DaySheduleAdminCommand;
import org.nailservice.telegram.bot.commands.DaySheduleCommand;
import org.nailservice.telegram.bot.commands.WeekSheduleAdminCommand;
import org.nailservice.telegram.bot.commands.WeekSheduleCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingCommandBot {

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public TelegramBot(CustomerService customerService, SheduleService sheduleService,
            TelegramSheduleFormatter sheduleFormatter) {
        register(new CustomerCommand("customers", "Get all customers", customerService));
        register(new DaySheduleCommand("today", "Get today shedule", sheduleService, sheduleFormatter));
        register(new DaySheduleAdminCommand("mytoday", "Get today shedule for nail master", sheduleService,
                sheduleFormatter));
        register(new WeekSheduleCommand("week", "Get week shedule", sheduleService, sheduleFormatter));
        register(new WeekSheduleAdminCommand("myweek", "Get week shedule for nail master", sheduleService,
                sheduleFormatter));
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("Привет");
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}

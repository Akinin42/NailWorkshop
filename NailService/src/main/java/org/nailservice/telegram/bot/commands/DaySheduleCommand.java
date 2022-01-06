package org.nailservice.telegram.bot.commands;

import java.time.LocalDate;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.TelegramSheduleFormatter;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class DaySheduleCommand extends Command {

    private final SheduleService sheduleService;
    private TelegramSheduleFormatter sheduleFormatter;

    public DaySheduleCommand(String identifier, String description, SheduleService sheduleService,
            TelegramSheduleFormatter sheduleFormatter) {
        super(identifier, description);
        this.sheduleService = sheduleService;
        this.sheduleFormatter = sheduleFormatter;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = (user.getUserName() != null) ? user.getUserName()
                : String.format("%s %s", user.getLastName(), user.getFirstName());
        Shedule shedule = sheduleService.createShedule(LocalDate.now());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                sheduleFormatter.createSheduleTextForCustomer(shedule));
    }
}

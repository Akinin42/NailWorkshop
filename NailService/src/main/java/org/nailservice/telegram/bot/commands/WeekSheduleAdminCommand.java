package org.nailservice.telegram.bot.commands;

import java.time.LocalDate;
import java.util.List;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.TelegramSheduleFormatter;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class WeekSheduleAdminCommand extends Command {

    private final SheduleService sheduleService;
    private TelegramSheduleFormatter sheduleFormatter;

    public WeekSheduleAdminCommand(String identifier, String description, SheduleService sheduleService,
            TelegramSheduleFormatter sheduleFormatter) {
        super(identifier, description);
        this.sheduleService = sheduleService;
        this.sheduleFormatter = sheduleFormatter;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = (user.getUserName() != null) ? user.getUserName()
                : String.format("%s %s", user.getLastName(), user.getFirstName());
        List<Shedule> weekShedule = sheduleService.createWeekShedule(LocalDate.now());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, createWeekSheduleText(weekShedule));
    }

    private String createWeekSheduleText(List<Shedule> weekShedule) {
        StringBuilder weekSheduleText = new StringBuilder();
        for (Shedule shedule : weekShedule) {
            weekSheduleText.append(sheduleFormatter.createSheduleTextForAdmin(shedule)).append("\n");
        }
        return weekSheduleText.toString();
    }
}

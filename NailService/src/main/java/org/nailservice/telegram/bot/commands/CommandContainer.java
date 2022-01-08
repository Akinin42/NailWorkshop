package org.nailservice.telegram.bot.commands;

import static org.nailservice.telegram.bot.commands.CommandName.CUSTOMERS;
import static org.nailservice.telegram.bot.commands.CommandName.MY_TODAY;
import static org.nailservice.telegram.bot.commands.CommandName.MY_WEEK;
import static org.nailservice.telegram.bot.commands.CommandName.TODAY;
import static org.nailservice.telegram.bot.commands.CommandName.WEEK;

import org.nailservice.service.CustomerService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.TelegramSheduleFormatter;
import org.nailservice.telegram.bot.service.SendBotMessageService;

import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, CustomerService customerService, SheduleService sheduleService) {
        TelegramSheduleFormatter sheduleFormatter = new TelegramSheduleFormatter();
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CUSTOMERS.getCommandName(), new CustomerCommand(customerService, sendBotMessageService))
                .put(MY_WEEK.getCommandName(), new WeekSheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(WEEK.getCommandName(), new WeekSheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(TODAY.getCommandName(), new DaySheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(MY_TODAY.getCommandName(), new DaySheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
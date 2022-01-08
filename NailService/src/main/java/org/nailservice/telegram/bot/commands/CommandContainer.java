package org.nailservice.telegram.bot.commands;

import static org.nailservice.telegram.bot.commands.CommandName.ADMIN_MENU;
import static org.nailservice.telegram.bot.commands.CommandName.ADMIN_MONTH;
import static org.nailservice.telegram.bot.commands.CommandName.ADMIN_TODAY;
import static org.nailservice.telegram.bot.commands.CommandName.ADMIN_WEEK;
import static org.nailservice.telegram.bot.commands.CommandName.CUSTOMERS;
import static org.nailservice.telegram.bot.commands.CommandName.MENU;
import static org.nailservice.telegram.bot.commands.CommandName.MONTH;
import static org.nailservice.telegram.bot.commands.CommandName.PRICE;
import static org.nailservice.telegram.bot.commands.CommandName.TODAY;
import static org.nailservice.telegram.bot.commands.CommandName.WEEK;

import org.nailservice.service.CustomerService;
import org.nailservice.service.ProcedureService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.nailservice.telegram.bot.service.TelegramSheduleFormatter;

import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, CustomerService customerService,
            SheduleService sheduleService, ProcedureService procedureService) {
        TelegramSheduleFormatter sheduleFormatter = new TelegramSheduleFormatter();
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CUSTOMERS.getCommandName(), new CustomerCommand(customerService, sendBotMessageService))
                .put(TODAY.getCommandName(),
                        new DaySheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(WEEK.getCommandName(),
                        new WeekSheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(MONTH.getCommandName(),
                        new MonthSheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(ADMIN_TODAY.getCommandName(),
                        new DaySheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(ADMIN_WEEK.getCommandName(),
                        new WeekSheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(ADMIN_MONTH.getCommandName(),
                        new MonthSheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(MENU.getCommandName(), new MenuCommand(sendBotMessageService))
                .put(ADMIN_MENU.getCommandName(), new MenuAdminCommand(sendBotMessageService))
                .put(PRICE.getCommandName(), new PriceCommand(procedureService, sendBotMessageService)).build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}

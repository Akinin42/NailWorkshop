package org.nailservice.telegram.bot.commands;

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
                .put(CommandName.START.getCommandName(), new MenuCommand(sendBotMessageService))
                .put(CommandName.CUSTOMERS.getCommandName(), new CustomerCommand(customerService, sendBotMessageService))
                .put(CommandName.TODAY.getCommandName(),
                        new DaySheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(CommandName.WEEK.getCommandName(),
                        new WeekSheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(CommandName.MONTH.getCommandName(),
                        new MonthSheduleCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(CommandName.ADMIN_TODAY.getCommandName(),
                        new DaySheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(CommandName.ADMIN_WEEK.getCommandName(),
                        new WeekSheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(CommandName.ADMIN_MONTH.getCommandName(),
                        new MonthSheduleAdminCommand(sheduleService, sendBotMessageService, sheduleFormatter))
                .put(CommandName.MENU.getCommandName(), new MenuCommand(sendBotMessageService))
                .put(CommandName.ADMIN_MENU.getCommandName(), new MenuAdminCommand(sendBotMessageService))
                .put(CommandName.PRICE.getCommandName(), new PriceCommand(procedureService, sendBotMessageService)).build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}

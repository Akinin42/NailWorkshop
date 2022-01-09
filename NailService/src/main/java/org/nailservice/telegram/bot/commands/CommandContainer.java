package org.nailservice.telegram.bot.commands;

import org.nailservice.service.CustomerService;
import org.nailservice.service.ProcedureService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.nailservice.telegram.bot.service.TelegramSheduleCreator;

import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer(SendBotMessageService sendBotMessageService, CustomerService customerService,
            SheduleService sheduleService, ProcedureService procedureService) {
        TelegramSheduleCreator sheduleCreator = new TelegramSheduleCreator();
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new MenuCommand(sendBotMessageService))
                .put(CommandName.CUSTOMERS.getCommandName(),
                        new CustomerCommand(customerService, sendBotMessageService))                
                .put(CommandName.DAY.getCommandName(),
                        new DayCommand(sheduleService, sendBotMessageService, sheduleCreator))
                .put(CommandName.ADMIN_DAY.getCommandName(),
                        new DayAdminCommand(sheduleService, sendBotMessageService, sheduleCreator))
                .put(CommandName.MENU.getCommandName(), new MenuCommand(sendBotMessageService))
                .put(CommandName.ADMIN_MENU.getCommandName(), new MenuAdminCommand(sendBotMessageService))
                .put(CommandName.PRICE.getCommandName(), new PriceCommand(procedureService, sendBotMessageService))
                .put(CommandName.CALENDAR.getCommandName(), new CalendarCommand(sendBotMessageService))
                .put(CommandName.ADMIN_CALENDAR.getCommandName(), new CalendarAdminCommand(sendBotMessageService))
                .build();
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, commandMap.get(CommandName.START.getCommandName()));
    }
}

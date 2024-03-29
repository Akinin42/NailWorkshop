package org.nailservice.telegram.bot;

import org.nailservice.service.CustomerService;
import org.nailservice.service.ProcedureService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.commands.CommandContainer;
import org.nailservice.telegram.bot.commands.CommandName;
import org.nailservice.telegram.bot.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NailWorkshopBot extends TelegramLongPollingBot {

    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final CommandContainer commandContainer;

    public NailWorkshopBot(CustomerService customerService, SheduleService sheduleService,
            ProcedureService procedureService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), customerService,
                sheduleService, procedureService);
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
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                if (update.getMessage().getText().equalsIgnoreCase("это Настя")) {
                    commandContainer.retrieveCommand(CommandName.ADMIN_MENU.getCommandName()).execute(update);
                } else {
                    commandContainer.retrieveCommand(CommandName.MENU.getCommandName()).execute(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();
            String commandIdentifier = message.split(" ")[0].toLowerCase();
            commandContainer.retrieveCommand(commandIdentifier).execute(addMessageToUpdate(update));
        }
    }

    private Update addMessageToUpdate(Update update) {
        Message message = update.getCallbackQuery().getMessage();
        message.setText(update.getCallbackQuery().getData());
        update.setMessage(message);
        return update;
    }
}

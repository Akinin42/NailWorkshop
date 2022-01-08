package org.nailservice.telegram.bot;

import static org.nailservice.telegram.bot.commands.CommandName.ADMIN_MENU;
import static org.nailservice.telegram.bot.commands.CommandName.MENU;

import org.nailservice.service.CustomerService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.commands.CommandContainer;
import org.nailservice.telegram.bot.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NailWorkshopBot extends TelegramLongPollingBot {

    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final CommandContainer commandContainer;

    public NailWorkshopBot(CustomerService customerService, SheduleService sheduleService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), customerService,
                sheduleService);
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
                if(update.getMessage().getText().equalsIgnoreCase("это Настя")) {
                    commandContainer.retrieveCommand(ADMIN_MENU.getCommandName()).execute(update);
                } else {
                    commandContainer.retrieveCommand(MENU.getCommandName()).execute(update);
                }                
            }
        } else if (update.hasCallbackQuery()) {           
            String message = update.getCallbackQuery().getData();
            String commandIdentifier = message.split(" ")[0].toLowerCase();
            commandContainer.retrieveCommand(commandIdentifier).execute(addMessageToUpdate(update));
        }
    }
    
    private Update addMessageToUpdate(Update update) {
        update.setMessage(update.getCallbackQuery().getMessage());
        return update;
    }
}

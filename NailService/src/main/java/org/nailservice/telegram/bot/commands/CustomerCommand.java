package org.nailservice.telegram.bot.commands;

import java.util.List;

import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CustomerCommand extends Command {

    private final CustomerService customerService;

    public CustomerCommand(String identifier, String description, CustomerService customerService) {
        super(identifier, description);
        this.customerService = customerService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = (user.getUserName() != null) ? user.getUserName()
                : String.format("%s %s", user.getLastName(), user.getFirstName());
        List<Customer> customers = customerService.findAllCustomers();
        StringBuilder answerText = new StringBuilder();
        for (Customer customer : customers) {
            answerText.append(customer.getName()).append(" - ").append(customer.getPhone()).append("\n");
        }
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, answerText.toString());
    }
}

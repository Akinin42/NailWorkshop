package org.nailservice.telegram.bot.commands;

import java.util.List;

import org.nailservice.entity.Customer;
import org.nailservice.service.CustomerService;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerCommand implements Command {

    private final CustomerService customerService;
    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), createCustomersText());
    }

    private String createCustomersText() {
        StringBuilder customersText = new StringBuilder();
        List<Customer> customers = customerService.findAllCustomers();
        for (Customer customer : customers) {
            customersText.append(customer.getName()).append(" - ").append(customer.getPhone()).append("\n");
        }
        return customersText.toString();
    }
}

package org.nailservice.telegram.bot.commands;

import java.util.List;

import org.nailservice.entity.Procedure;
import org.nailservice.service.ProcedureService;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PriceCommand implements Command {

    private final ProcedureService procedureService;
    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), createCustomersText());
    }

    private String createCustomersText() {
        StringBuilder priceText = new StringBuilder();
        List<Procedure> procedures = procedureService.findAllProcedures();
        for (Procedure procedure : procedures) {
            priceText.append(procedure.getName()).append(" - ").append(procedure.getCost()).append(" руб.").append("\n");
        }
        return priceText.toString();
    }
}

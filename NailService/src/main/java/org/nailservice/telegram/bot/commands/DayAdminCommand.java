package org.nailservice.telegram.bot.commands;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nailservice.entity.Shedule;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.nailservice.telegram.bot.service.TelegramSheduleCreator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DayAdminCommand implements Command {

    private final SheduleService sheduleService;
    private final SendBotMessageService sendBotMessageService;    
    private TelegramSheduleCreator sheduleCreator;

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(createKeyboard(update));
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите пожайлуста время: ");
        sendBotMessageService.sendMessage(sendMessage);
    }

    private InlineKeyboardMarkup createKeyboard(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rowList);
        LocalDate date = generateDate(update);
        rowList.add(createDateRow(date));
        addOrders(rowList, date);
        rowList.add(createCalendarRow(date));
        return inlineKeyboardMarkup;
    }

    private LocalDate generateDate(Update update) {
        String[] message = update.getMessage().getText().split(" ");
        LocalDate date = Instant.ofEpochSecond(update.getMessage().getDate()).atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (message.length > 1) {
            return LocalDate.parse(message[1]);
        } else {
            return date;
        }
    }

    private List<InlineKeyboardButton> createDateRow(LocalDate date) {
        return createButtonsRow(createButton(date.toString(), String.format("/adminday %s", date.toString())));
    }

    private List<List<InlineKeyboardButton>> addOrders(List<List<InlineKeyboardButton>> rowList, LocalDate date) {
        Shedule shedule = sheduleService.createShedule(date);
        for (Map.Entry<String, String> order: sheduleCreator.createSheduleTextForAdmin(shedule).entrySet()) {
            rowList.add(createButtonsRow(createButton(order.getKey(), order.getValue())));
        }
        return rowList;
    }
    
    private List<InlineKeyboardButton> createCalendarRow(LocalDate date) {
        String month = date.getMonth().toString();
        return createButtonsRow(createButton("Показать весь месяц", String.format("/admincalendar %s", month)));
    }

    private List<InlineKeyboardButton> createButtonsRow(InlineKeyboardButton button) {
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;

    }

    private InlineKeyboardButton createButton(String text, String callback) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        return button;
    }
}

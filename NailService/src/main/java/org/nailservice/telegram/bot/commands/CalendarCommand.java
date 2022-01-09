package org.nailservice.telegram.bot.commands;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalendarCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(createKeyboard(update));
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите пожайлуста день: ");
        sendBotMessageService.sendMessage(sendMessage);
    }

    private InlineKeyboardMarkup createKeyboard(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rowList);
        LocalDate date = generateDate(update);
        rowList.add(createMonthRow(date));
        rowList.add(createDaysNameRow());
        int day = 1;
        while (day <= date.getMonth().length(false)) {
            int mondayPosition = LocalDate.of(date.getYear(), date.getMonth(), day).getDayOfWeek().getValue();
            rowList.add(createDaysRow(day, mondayPosition, date.getMonth().length(false)));
            day += (8 - mondayPosition);
        }
        rowList.add(createSwitchMonthRow(date));
        return inlineKeyboardMarkup;
    }

    private LocalDate generateDate(Update update) {
        String[] message = update.getMessage().getText().split(" ");
        LocalDate date = Instant.ofEpochSecond(update.getMessage().getDate()).atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (message.length > 1) {
            return LocalDate.of(date.getYear(), Month.valueOf(message[1]), 1);
        } else {
            return date;
        }
    }

    private List<InlineKeyboardButton> createMonthRow(LocalDate date) {
        String monthAndYear = String.format("%s %d", date.getMonth().toString(), date.getYear());
        return createButtonsRow(createButton(monthAndYear, " "));
    }

    private List<InlineKeyboardButton> createDaysNameRow() {
        String[] daysName = { "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс" };
        List<InlineKeyboardButton> daysNameRow = new ArrayList<>();
        for (String name : daysName) {
            daysNameRow.add(createButton(name, " "));
        }
        return daysNameRow;
    }

    private List<InlineKeyboardButton> createDaysRow(int firstRowNumber, int position, int monthLength) {
        List<InlineKeyboardButton> daysRow = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            if (i < position || firstRowNumber + i - position > monthLength) {
                daysRow.add(createButton(" ", " "));
            } else {
                daysRow.add(createButton(Integer.toString(firstRowNumber + i - position), "/day"));
            }
        }
        return daysRow;
    }

    private List<InlineKeyboardButton> createSwitchMonthRow(LocalDate date) {
        List<InlineKeyboardButton> switchRow = new ArrayList<>();
        String lastMonth = date.getMonth().minus(1).toString();
        String nextMonth = date.getMonth().plus(1).toString();
        switchRow.add(createButton(lastMonth, String.format("/calendar %s", lastMonth)));
        switchRow.add(createButton(nextMonth, String.format("/calendar %s", nextMonth)));
        return switchRow;
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

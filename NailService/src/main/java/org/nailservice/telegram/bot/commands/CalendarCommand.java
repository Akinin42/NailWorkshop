package org.nailservice.telegram.bot.commands;

import java.time.LocalDate;
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
        sendMessage.setReplyMarkup(createKeyboard());
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите пожайлуста день: ");
        sendBotMessageService.sendMessage(sendMessage);
    }

    private InlineKeyboardMarkup createKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rowList);
        rowList.add(createMonthRow());
        rowList.add(createDaysNameRow());
        int day = 1;
        while (day <= LocalDate.now().getMonth().maxLength()) {
            int mondayPosition = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day).getDayOfWeek()
                    .getValue();
            rowList.add(createDaysRow(day, mondayPosition));
            day += (8 - mondayPosition);
        }
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createMonthRow() {
        String monthAndYear = String.format("%s %d", LocalDate.now().getMonth().toString(), LocalDate.now().getYear());
        return createButtonsRow(createButton(monthAndYear, "/calendar"));
    }

    private List<InlineKeyboardButton> createDaysNameRow() {
        String[] daysName = { "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс" };
        List<InlineKeyboardButton> daysNameRow = new ArrayList<>();
        for (String name : daysName) {
            daysNameRow.add(createButton(name, "/calendar"));
        }
        return daysNameRow;
    }

    private List<InlineKeyboardButton> createDaysRow(int firstRowNumber, int position) {
        List<InlineKeyboardButton> daysRow = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            if (i < position || firstRowNumber + i - position > LocalDate.now().getMonth().maxLength()) {
                daysRow.add(createButton(" ", "s"));
            } else {
                daysRow.add(createButton(String.format("%d", (firstRowNumber + i - position)), "/day"));
            }
        }
        return daysRow;
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

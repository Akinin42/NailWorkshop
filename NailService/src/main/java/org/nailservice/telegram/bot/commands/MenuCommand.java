package org.nailservice.telegram.bot.commands;

import java.util.ArrayList;
import java.util.List;

import org.nailservice.telegram.bot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MenuCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(createKeyboard());
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Добро пожаловать, я бот нэйл-мастера Анастасии Лопаренок, "
                + "все доступные услуги вы можете получить в меню ниже:");
        sendBotMessageService.sendMessage(sendMessage);
    }

    private InlineKeyboardMarkup createKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(createButtonsRow(createButton("Расписание на сегодня", "/day")));
        rowList.add(createButtonsRow(createButton("Расписание на месяц", "/calendar")));
        rowList.add(createButtonsRow(createButton("Прайс-лист", "/price")));
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
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

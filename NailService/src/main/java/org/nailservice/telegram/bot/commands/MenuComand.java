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
public class MenuComand implements Command {

    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        InlineKeyboardButton todayButton = new InlineKeyboardButton();
        todayButton.setText("Расписание на сегодня");
        todayButton.setCallbackData("/today");
        InlineKeyboardButton weekButton = new InlineKeyboardButton();
        weekButton.setText("Расписание на неделю");
        weekButton.setCallbackData("/week");
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(todayButton);
        keyboardButtonsRow.add(weekButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Добро пожаловать в меню бота Анастасии Лопаренок: "
                + "вы можете посмотреть все доступные команды введя /help");
        sendBotMessageService.sendMessage(sendMessage);
    }
}

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
public class MenuAdminComand implements Command {

    private final SendBotMessageService sendBotMessageService;

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        InlineKeyboardButton todayButton = new InlineKeyboardButton();
        todayButton.setText("Расписание на сегодня");
        todayButton.setCallbackData("/admintoday");
        InlineKeyboardButton weekButton = new InlineKeyboardButton();
        weekButton.setText("Расписание на неделю");
        weekButton.setCallbackData("/adminweek");
//        InlineKeyboardButton customersButton = new InlineKeyboardButton();
//        weekButton.setText("Клиенты");
//        weekButton.setCallbackData("/customers");
        List<InlineKeyboardButton> firstKeyboardButtonsRow = new ArrayList<>();
        firstKeyboardButtonsRow.add(todayButton);
        firstKeyboardButtonsRow.add(weekButton);
//        List<InlineKeyboardButton> secondKeyboardButtonsRow = new ArrayList<>();
//        secondKeyboardButtonsRow.add(customersButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(firstKeyboardButtonsRow);
//        rowList.add(secondKeyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Привет хозяйка ");
        sendBotMessageService.sendMessage(sendMessage);
    }
}

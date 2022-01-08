package org.nailservice.telegram.bot.service;

import org.nailservice.telegram.bot.NailWorkshopBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final NailWorkshopBot nailWorkshopBot;

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        try {
            nailWorkshopBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(String.format("error for message: %s", message));
        }
    }
}

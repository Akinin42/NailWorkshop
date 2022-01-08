package org.nailservice.telegram.bot.service;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}

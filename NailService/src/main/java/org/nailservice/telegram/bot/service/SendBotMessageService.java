package org.nailservice.telegram.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
    
    void sendMessage(SendMessage message);
}

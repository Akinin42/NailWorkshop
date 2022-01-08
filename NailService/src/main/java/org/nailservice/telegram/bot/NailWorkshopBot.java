package org.nailservice.telegram.bot;

import org.nailservice.service.CustomerService;
import org.nailservice.service.SheduleService;
import org.nailservice.telegram.bot.commands.CommandContainer;
import org.nailservice.telegram.bot.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NailWorkshopBot extends TelegramLongPollingBot {

    private static final String COMMAND_PREFIX = "/";

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final CommandContainer commandContainer;   

    public NailWorkshopBot(CustomerService customerService, SheduleService sheduleService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), customerService, sheduleService);
    }

//    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

//    public TelegramBot(CustomerService customerService, SheduleService sheduleService,
//            TelegramSheduleFormatter sheduleFormatter) {
//        register(new CustomerCommand("customers", "Get all customers", customerService));
//        register(new DaySheduleCommand("today", "Get today shedule", sheduleService, sheduleFormatter));
//        register(new DaySheduleAdminCommand("mytoday", "Get today shedule for nail master", sheduleService,
//                sheduleFormatter));
//        register(new WeekSheduleCommand("week", "Get week shedule", sheduleService, sheduleFormatter));
//        register(new WeekSheduleAdminCommand("myweek", "Get week shedule for nail master", sheduleService,
//                sheduleFormatter));
//    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
//                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

//    @Override
//    public void processNonCommandUpdate(Update update) {
//
//        if (update.hasMessage()) {
////            SendMessage sendMessage = new SendMessage();
////            sendMessage.enableMarkdown(true);
//            Message message = update.getMessage();
////            sendMessage.setChatId(message.getChatId().toString());
////            Long chatId = message.getChatId();
////            String text = update.getMessage().getText();
////            sendMessage.setReplyMarkup(replyKeyboardMarkup);
////            try {
////                sendMessage.setText(getMessage(text));
////                execute(sendMessage);
////            } catch (TelegramApiException e) {
////                e.printStackTrace();
////            }
//            if (message != null && message.hasText()) {
//                sendNotification(message, "Добро пожаловать к Анастасии Лопаренок:");
//            }
//        } else if (update.hasCallbackQuery()) {            
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setText(update.getCallbackQuery().getData());
//            sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());            
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

//    private String getMessage(String text) {
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        keyboard.clear();
//        keyboardFirstRow.clear();
//        keyboardFirstRow.add("Расписание на сегодня");
//        keyboardFirstRow.add("Расписание на неделю");
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        return "Добро пожаловать к Анастасии Лопаренок:";
//    }

//    private void sendNotification(Message message, String messageText) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
////        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
////        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
////        replyKeyboardMarkup.setSelective(true);
////        replyKeyboardMarkup.setResizeKeyboard(true);
////        replyKeyboardMarkup.setOneTimeKeyboard(true);
//
//        // Создаем список строк клавиатуры
////        List<KeyboardRow> keyboard = new ArrayList<>();
//        // Первая строчка клавиатуры
////        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        // Добавляем кнопки в первую строчку клавиатуры
////        keyboardFirstRow.add("Расписание на сегодня");
////        keyboardFirstRow.add("Расписание на неделю");
//        InlineKeyboardButton todayButton = new InlineKeyboardButton();
//        todayButton.setText("Расписание на сегодня");
//        todayButton.setCallbackData("/today");
//        InlineKeyboardButton weekButton = new InlineKeyboardButton();
//        weekButton.setText("Расписание на неделю");
//        weekButton.setCallbackData("/week");
//        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
//        keyboardButtonsRow.add(todayButton);
//        keyboardButtonsRow.add(weekButton);
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow);
////        replyKeyboardMarkup.setKeyboard(keyboard);
//        inlineKeyboardMarkup.setKeyboard(rowList);
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
//        sendMessage.setText(messageText);
//        try {
//            execute(sendMessage);
////            sendMessage(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
}

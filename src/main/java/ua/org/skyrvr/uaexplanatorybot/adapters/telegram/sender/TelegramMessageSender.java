package ua.org.skyrvr.uaexplanatorybot.adapters.telegram.sender;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ua.org.skyrvr.uaexplanatorybot.adapters.telegram.TelegramBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Telegram message sender implementation
 */
@Component
public class TelegramMessageSender {

    @Autowired
    private TelegramBot telegramBot;

    public void sendTextMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(text);
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendQuickReply(String recipientId, String question, Map<String, String> answerOptions) {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup replyKeyboard = new InlineKeyboardMarkup();
        // add answers to the keyboard
        answerOptions.entrySet().iterator().forEachRemaining(e -> {
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton().setText(e.getValue()).setCallbackData(e.getKey());
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(keyboardButton);
            replyKeyboard.getKeyboard().add(row);
        });
        sendMessage.setReplyMarkup(replyKeyboard)
                .setChatId(recipientId)
                .setText(question);
        // send the reply
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


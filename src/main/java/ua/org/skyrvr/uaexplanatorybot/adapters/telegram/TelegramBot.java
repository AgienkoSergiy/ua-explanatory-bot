package ua.org.skyrvr.uaexplanatorybot.adapters.telegram;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ua.org.skyrvr.uaexplanatorybot.process.ProcessingCore;

import javax.annotation.PostConstruct;

/**
 * This is a telegram bot adapter that receives updates from telegram. Upon receiving updates, the component forwards
 * messages to the Processing Core.
 */
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.name}")
    private String botName;

    @Autowired
    private ProcessingCore processingCore;

    /**
     * Init the telegram context
     */
    static {
        ApiContextInitializer.init();
    }

    /**
     * Register this component to be used for processing Telegram updates.
     *
     * @throws TelegramApiRequestException
     */
    @PostConstruct
    public void instantiateBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(this);
    }

    /**
     * This method parses telegram messages.
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        String messageText = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        processingCore.processMessage(messageText, chatId);
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

}


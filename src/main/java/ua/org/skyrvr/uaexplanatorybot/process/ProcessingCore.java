package ua.org.skyrvr.uaexplanatorybot.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.org.skyrvr.uaexplanatorybot.adapters.telegram.sender.TelegramMessageSender;
import ua.org.skyrvr.uaexplanatorybot.connectors.DictConnector;

@Component
public class ProcessingCore {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DictConnector dictConnector;

    @Autowired
    private TelegramMessageSender messageSender;


    public void processMessage(String messageText, String chatId) {
        logger.info("Message has been received: " + messageText);
        String dictResponse = dictConnector.getWordExplanation(messageText);

        messageSender.sendTextMessage(chatId, dictResponse);
    }

}
